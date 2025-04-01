# 已知问题和需要改进的地方

本文档记录了当前系统中已知的问题和需要改进的地方，供开发人员参考。

## 账单管理

### 1. 更新账单标签问题

在 `BillServiceImpl` 的 `updateBillWithTags` 方法中，目前代码会删除账单相关的所有标签，但没有添加新的标签。这会导致每次更新账单都会丢失所有标签关联。

**问题代码位置**:
```java
@Override
@Transactional(rollbackFor = Exception.class)
public void updateBillWithTags(BillDto bill) {
    // 更新账单基本信息
    int result = billMapper.updateById(bill);
    logger.info("账单基本信息更新结果：{}", result);
    
    if (result <= 0) {
        logger.error("账单基本信息更新失败，ID：{}", bill.getId());
        throw new RuntimeException("更新账单失败");
    }

    //先查询再删除全部标签
    List<BillTags> billTags = billTagsMapper.selectList(Wrappers.<BillTags>lambdaQuery().eq(BillTags::getBillId, bill.getId()));
    billTagsMapper.delete(Wrappers.<BillTags>lambdaQuery().eq(BillTags::getBillId, bill.getId()));
    tagMapper.deleteBatchIds(billTags.stream().map(BillTags::getTagId).collect(Collectors.toList()));
    
    // 缺少添加新标签的代码
}
```

**建议修复**:
```java
@Override
@Transactional(rollbackFor = Exception.class)
public void updateBillWithTags(BillDto bill) {
    // 更新账单基本信息
    int result = billMapper.updateById(bill);
    logger.info("账单基本信息更新结果：{}", result);
    
    if (result <= 0) {
        logger.error("账单基本信息更新失败，ID：{}", bill.getId());
        throw new RuntimeException("更新账单失败");
    }

    // 删除原有的账单-标签关联
    billTagsMapper.delete(Wrappers.<BillTags>lambdaQuery().eq(BillTags::getBillId, bill.getId()));
    
    // 添加新的标签关联
    if (!CollectionUtils.isEmpty(bill.getTagIds())) {
        for (Long tagId : bill.getTagIds()) {
            billTagsMapper.insert(new BillTags().setBillId(bill.getId()).setTagId(tagId));
        }
    }
}
```

### 2. 更新账单删除标签问题

当前 `updateBillWithTags` 方法不仅删除了账单和标签的关联，还删除了标签本身，这可能并不是期望的行为。标签通常是可以复用的，删除账单与标签的关联不应该同时删除标签本身。

**问题代码位置**:
```java
//先查询再删除全部标签
List<BillTags> billTags = billTagsMapper.selectList(Wrappers.<BillTags>lambdaQuery().eq(BillTags::getBillId, bill.getId()));
billTagsMapper.delete(Wrappers.<BillTags>lambdaQuery().eq(BillTags::getBillId, bill.getId()));
tagMapper.deleteBatchIds(billTags.stream().map(BillTags::getTagId).collect(Collectors.toList()));
```

**建议修复**:
删除 `tagMapper.deleteBatchIds` 这一行代码，只保留删除关联关系的操作。

## 前端注意事项

由于存在上述问题，在调用更新账单接口时，前端开发人员需要注意：

1. 每次更新账单时都需要传递完整的标签ID列表，否则标签关联会丢失
2. 注意不要使用已被删除的标签ID，因为当前代码会删除这些标签

这些问题将在后续版本中修复。 