package com.example.billback.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 账单表实体类
 */
@Data
public class BillDto {
    /**
     * 账单ID
     */
    private Integer id;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 金额
     */
    private BigDecimal amount;
    
    /**
     * 账单时间
     */
    private String billDate;
    
    /**
     * 备注
     */
    private String desc;
    
    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 修改时间
     */
    private String updateTime;
    
    /**
     * 收支类型:支出,入账,不计入
     */
    private String inoutType;

    /**
     * 账户类型:储蓄账户,信用账户
     */
    private String accountType;

    /**
     * 标签id
     */
    private List<Integer> tagIdList;
    /**
     * 标签类型:支付方式(微信支付宝等),账单类型(衣食住行等)
     */
    private String tagType;
}
