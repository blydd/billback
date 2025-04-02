package com.example.billback.dto;

import com.example.billback.entity.Bill;
import lombok.Data;

import java.util.List;

/**
 * @author: bgt
 * @Date: 2025/4/1 15:52
 * @Desc:
 */
@Data
public class BillDto extends Bill {

    private String month;
    /**
     * 账户类型:1储蓄账户,2信用账户
     */
    private Integer accountType;

    /**
     * 标签类型:1-支付方式(微信支付宝等),2-账单类型(衣食住行等)
     */
    private Integer tagType;



    private List<Long> tagIds;
}
