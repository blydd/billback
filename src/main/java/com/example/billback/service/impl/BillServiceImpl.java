package com.example.billback.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.billback.vo.BillVo;
import com.example.billback.dto.BillDto;
import com.example.billback.entity.Bill;
import com.example.billback.entity.BillTags;
import com.example.billback.entity.Tag;
import com.example.billback.mapper.BillMapper;
import com.example.billback.mapper.BillTagsMapper;
import com.example.billback.mapper.TagMapper;
import com.example.billback.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

    private static final Logger logger = LoggerFactory.getLogger(BillServiceImpl.class);

    @Autowired
    private BillMapper billMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BillTagsMapper billTagsMapper;


    /**
     * 获取账单列表
     * @param billDto
     * @return
     */
    @Override
    public List<BillVo> getBills(BillDto billDto) {
        List<BillVo> bills = billMapper.getBills(billDto);
        //查询标签
        bills.forEach(bill -> {
            List<BillTags> billTags = billTagsMapper.selectList(Wrappers.<BillTags>lambdaQuery().eq(BillTags::getBillId, bill.getId()));
            if (CollUtil.isNotEmpty(billTags)){
                List<Tag> tags = tagMapper.selectList(Wrappers.<Tag>lambdaQuery()
                        .in(Tag::getId, billTags.stream().map(BillTags::getTagId).collect(Collectors.toList()))
                        .eq(Objects.nonNull(billDto.getTagType()),Tag::getTagType, billDto.getTagType())
                );
                bill.setTags(tags);
            }
        });
        return bills;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Bill saveBillWithTags(BillDto bill) {

        // 确保账单有创建时间
        if (bill.getCreateTime() == null) {
            bill.setCreateTime(LocalDateTime.now());
        }
        
        // 保存账单基本信息
        int result = billMapper.insert(bill);
        logger.info("账单基本信息保存结果：{}，生成的ID：{}", result, bill.getId());
        
        if (result <= 0) {
            logger.error("账单基本信息保存失败");
            throw new RuntimeException("保存账单失败");
        }

        //查询关联的标签,看是否属于帅哥
        List<Tag> tags = tagMapper.selectBatchIds(bill.getTagIds());
        boolean ifMan = CollUtil.isNotEmpty(tags.stream().filter(t -> t.getTagType() == 3 && t.getName().equals("帅哥")).collect(Collectors.toList()));
        //保存账单标签中间表
        if (!CollectionUtils.isEmpty(bill.getTagIds())) {
            for (Long tagId : bill.getTagIds()) {
                //更新信用卡可用额度
                Tag tag = tagMapper.selectById(tagId);
                if (Objects.equals(tag.getAccountType(),2)) {
                    BigDecimal amount = bill.getAmount();
                    Integer inoutType = bill.getInoutType();
                    if (inoutType == 1) {
                        //支出
                        if (ifMan){
                            tag.setCreditLimitAvailable1(tag.getCreditLimitAvailable1().subtract(amount));
                        }else {
                            tag.setCreditLimitAvailable2(tag.getCreditLimitAvailable2().subtract(amount));
                        }

                    } else if (inoutType == 3 && StrUtil.equals(tag.getName(), "还信用卡")){
                        if (ifMan){
                            tag.setCreditLimitAvailable1(tag.getCreditLimitAvailable1().add(amount));
                        }else {
                            tag.setCreditLimitAvailable2(tag.getCreditLimitAvailable2().add(amount));
                        }

                    }
                    if (ifMan){
                        if (tag.getCreditLimitAvailable1().compareTo(BigDecimal.ZERO) < 0) {
                            tag.setCreditLimitAvailable1(BigDecimal.ZERO);
                        }
                        //如果可用额度大于额度,则设为额度
                        if (tag.getCreditLimitAvailable1().compareTo(tag.getCreditLimit1()) > 0) {
                            tag.setCreditLimitAvailable1(tag.getCreditLimit1());
                        }
                    }else {
                        if (tag.getCreditLimitAvailable2().compareTo(BigDecimal.ZERO) < 0) {
                            tag.setCreditLimitAvailable2(BigDecimal.ZERO);
                        }
                        //如果可用额度大于额度,则设为额度
                        if (tag.getCreditLimitAvailable2().compareTo(tag.getCreditLimit2()) > 0) {
                            tag.setCreditLimitAvailable2(tag.getCreditLimit2());
                        }
                    }

                    tagMapper.updateById(tag);
                }


                billTagsMapper.insert(new BillTags().setBillId(bill.getId()).setTagId(tagId));
            }
        }

        return bill;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBillWithTags(BillDto bill) {
        Bill oldBill = billMapper.selectById(bill.getId());
        // 更新账单基本信息
        int result = billMapper.updateById(bill);
        logger.info("账单基本信息更新结果：{}", result);
        
        if (result <= 0) {
            logger.error("账单基本信息更新失败，ID：{}", bill.getId());
            throw new RuntimeException("更新账单失败");
        }

        //先查询再删除全部标签关系
        List<BillTags> billTags = billTagsMapper.selectList(Wrappers.<BillTags>lambdaQuery().eq(BillTags::getBillId, bill.getId()));
        //查询关联的标签,看是否属于帅哥
        List<Tag> tags = tagMapper.selectBatchIds(billTags.stream().map(BillTags::getTagId).collect(Collectors.toList()));
        boolean ifMan = CollUtil.isNotEmpty(tags.stream().filter(t -> t.getTagType() == 3 && t.getName().equals("帅哥")).collect(Collectors.toList()));

        if (CollUtil.isNotEmpty(billTags)){
            for (BillTags billTag : billTags) {
                //更新信用卡可用额度:原来标签先恢复额度
                Tag tag = tagMapper.selectById(billTag.getTagId());
                if (Objects.equals(tag.getAccountType(),2)) {
                    BigDecimal amount = oldBill.getAmount();
                    Integer inoutType = oldBill.getInoutType();
                    if (inoutType == 1) {
                        //支出
                        if (ifMan){
                            tag.setCreditLimitAvailable1(tag.getCreditLimitAvailable1().add(amount));
                        }else {
                            tag.setCreditLimitAvailable2(tag.getCreditLimitAvailable2().add(amount));
                        }
                    } else if (inoutType == 3 && StrUtil.equals(tag.getName(), "还信用卡")){
                        if (ifMan){
                            tag.setCreditLimitAvailable1(tag.getCreditLimitAvailable1().subtract(amount));
                        }else {
                            tag.setCreditLimitAvailable2(tag.getCreditLimitAvailable2().subtract(amount));
                        }

                    }
                    if (ifMan){
                        if (tag.getCreditLimitAvailable1().compareTo(BigDecimal.ZERO) < 0) {
                            tag.setCreditLimitAvailable1(BigDecimal.ZERO);
                        }
                        //如果可用额度大于额度,则设为额度
                        if (tag.getCreditLimitAvailable1().compareTo(tag.getCreditLimit1()) > 0) {
                            tag.setCreditLimitAvailable1(tag.getCreditLimit1());
                        }
                    }else {
                        if (tag.getCreditLimitAvailable2().compareTo(BigDecimal.ZERO) < 0) {
                            tag.setCreditLimitAvailable2(BigDecimal.ZERO);
                        }
                        //如果可用额度大于额度,则设为额度
                        if (tag.getCreditLimitAvailable2().compareTo(tag.getCreditLimit2()) > 0) {
                            tag.setCreditLimitAvailable2(tag.getCreditLimit2());
                        }
                    }
                    tagMapper.updateById(tag);
                }
            }

            billTagsMapper.delete(Wrappers.<BillTags>lambdaQuery().eq(BillTags::getBillId, bill.getId()));
        }


        //保存标签
        if (!CollectionUtils.isEmpty(bill.getTagIds())) {
            //查询关联的标签,看是否属于帅哥
            List<Tag> newtags = tagMapper.selectBatchIds(bill.getTagIds());
            boolean ifMan2 = CollUtil.isNotEmpty(newtags.stream().filter(t -> t.getTagType() == 3 && t.getName().equals("帅哥")).collect(Collectors.toList()));

            for (Long tagId : bill.getTagIds()) {
                //更新信用卡可用额度:原来标签先恢复额度
                Tag tag = tagMapper.selectById(tagId);
                if (Objects.equals(tag.getAccountType(),2)) {
                    BigDecimal amount = oldBill.getAmount();
                    Integer inoutType = oldBill.getInoutType();
                    if (inoutType == 1) {
                        //支出
                        if (ifMan2){
                            tag.setCreditLimitAvailable1(tag.getCreditLimitAvailable1().subtract(amount));
                        }else {
                            tag.setCreditLimitAvailable2(tag.getCreditLimitAvailable2().subtract(amount));
                        }
                    } else if (inoutType == 3 && StrUtil.equals(tag.getName(), "还信用卡")){
                        if (ifMan2){
                            tag.setCreditLimitAvailable1(tag.getCreditLimitAvailable1().add(amount));
                        }else {
                            tag.setCreditLimitAvailable2(tag.getCreditLimitAvailable2().add(amount));
                        }
                    }
                    if (ifMan2){
                        if (tag.getCreditLimitAvailable1().compareTo(BigDecimal.ZERO) < 0) {
                            tag.setCreditLimitAvailable1(BigDecimal.ZERO);
                        }
                        //如果可用额度大于额度,则设为额度
                        if (tag.getCreditLimitAvailable1().compareTo(tag.getCreditLimit1()) > 0) {
                            tag.setCreditLimitAvailable1(tag.getCreditLimit1());
                        }
                    }else {
                        if (tag.getCreditLimitAvailable2().compareTo(BigDecimal.ZERO) < 0) {
                            tag.setCreditLimitAvailable2(BigDecimal.ZERO);
                        }
                        //如果可用额度大于额度,则设为额度
                        if (tag.getCreditLimitAvailable2().compareTo(tag.getCreditLimit2()) > 0) {
                            tag.setCreditLimitAvailable2(tag.getCreditLimit2());
                        }
                    }

                    tagMapper.updateById(tag);
                }

                billTagsMapper.insert(new BillTags().setBillId(bill.getId()).setTagId(tagId));
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBillWithTags(Long id) {
        logger.info("开始删除账单，ID：{}", id);

        // 删除账单基本信息
        int result = billMapper.deleteById(id);
        logger.info("账单基本信息删除结果：{}", result);
        
        if (result <= 0) {
            logger.error("账单基本信息删除失败，ID：{}", id);
            throw new RuntimeException("删除账单失败");
        }
    }

    @Override
    public BillVo getOneById(Long id) {
        Bill bill = this.getById(id);
        BillVo billVo = BeanUtil.copyProperties(bill, BillVo.class);
        List<BillTags> billTags = billTagsMapper.selectList(Wrappers.<BillTags>lambdaQuery().eq(BillTags::getBillId, bill.getId()));
        if (CollUtil.isNotEmpty(billTags)){
            List<Tag> tags = tagMapper.selectBatchIds(billTags.stream().map(BillTags::getTagId).collect(Collectors.toList()));
            billVo.setTags(tags);
        }
        return billVo;
    }
}