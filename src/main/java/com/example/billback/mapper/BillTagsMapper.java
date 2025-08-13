package com.example.billback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.billback.entity.BillTag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BillTagsMapper extends BaseMapper<BillTag> {
}