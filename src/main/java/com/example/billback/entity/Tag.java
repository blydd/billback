package com.example.billback.entity;

import lombok.Data;

/**
 * 标签表实体类
 */
@Data
public class Tag {
    /**
     * 标签ID
     */
    private Integer id;
    
    /**
     * 标签名称
     */
    private String name;
    
    /**
     * 收支类型:支出,入账,不计入
     */
    private String inoutType;
    
    /**
     * 用户id
     */
    private Integer userId;
    
    /**
     * 标签类型:支付方式(微信支付宝等),账单类型(衣食住行等)
     */
    private String tagType;
    /**
     * 账户类型:储蓄账户,信用账户
     */
    private String accountType;
}
