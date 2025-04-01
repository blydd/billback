package com.example.billback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.billback.vo.BillVo;
import com.example.billback.dto.BillDto;
import com.example.billback.entity.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {
    List<BillVo> getBills(@Param("billDto") BillDto billDto);
}