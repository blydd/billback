package com.example.billback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.billback.entity.Bill;
import com.example.billback.entity.BillTags;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BillTagsMapper extends BaseMapper<BillTags> {
}