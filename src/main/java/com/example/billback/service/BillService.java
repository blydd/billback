package com.example.billback.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.billback.vo.BillVo;
import com.example.billback.dto.BillDto;
import com.example.billback.entity.Bill;

import java.util.List;

public interface BillService extends IService<Bill> {
    List<BillVo> getBills(BillDto billDto);
    Bill saveBillWithTags(BillDto bill);
    void updateBillWithTags(BillDto bill);
    void deleteBillWithTags(Long id);

    BillVo getOneById(Long id);
}