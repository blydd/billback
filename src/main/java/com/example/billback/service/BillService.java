package com.example.billback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.billback.common.Result;
import com.example.billback.dto.BillDto;
import com.example.billback.entity.Bill;
import com.example.billback.vo.BillVo;

import java.util.List;

public interface BillService extends IService<Bill> {
    Result<List<BillVo>> getBills(BillDto billDto);
}