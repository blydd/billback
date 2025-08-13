package com.example.billback.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.billback.common.Result;
import com.example.billback.dto.BillDto;
import com.example.billback.entity.Bill;
import com.example.billback.entity.BillTag;
import com.example.billback.entity.Tag;
import com.example.billback.vo.BillVo;
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
    public Result<List<BillVo>> getBills(BillDto billDto) {
        if (Objects.isNull(billDto.getUserId())){
            return Result.error(100,"请先登录");
        }
        List<BillVo> bills = billMapper.getBills(billDto);
        //查询标签
        bills.forEach(bill -> {
            List<BillTag> billTags = billTagsMapper.selectList(Wrappers.<BillTag>lambdaQuery().eq(BillTag::getBillId, bill.getId()));
            if (CollUtil.isNotEmpty(billTags)){
                List<Tag> tags = tagMapper.selectList(Wrappers.<Tag>lambdaQuery()
                        .in(Tag::getId, billTags.stream().map(BillTag::getTagId).collect(Collectors.toList()))
                        .eq(Objects.nonNull(billDto.getAccountType()),Tag::getTagType, billDto.getAccountType())
                );
                bill.setTags(tags);
            }
        });
        return Result.success(bills);
    }
}