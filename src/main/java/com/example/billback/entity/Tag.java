package com.example.billback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("tags")
public class Tag {
    /**
     * 主键ID，采用数据库自增方式
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 标签名称
     */
    private String name;
    
    /**
     * 收支类型:1-支出,2-入账,3-不计入收支
     */
    private Integer inoutType;
    
    /**
     * 标签类型:1-支付方式,2-账单类型,3-归属人
     */
    private Integer tagType;
    
    /**
     * 账户类型.标签类型为支付方式时才有:1-储蓄账户,2-信用账户
     */
    private Integer accountType;
    
    /**
     * 标签图标
     */
    private String icon;
    
    /**
     * 用户ID，关联用户表的主键
     */
    private Long userId;

    /**
     * 信用额度1
     */
    private BigDecimal creditLimit1;

    /**
     * 信用可用额度1
     */
    private BigDecimal creditLimitAvailable1;

    /**
     * 信用卡账单日1
     */
    private Integer creditBillDay1;

    /**
     * 信用卡还款日1
     */
    private Integer creditPayDay1;

    /**
     * 信用额度2
     */
    private BigDecimal creditLimit2;

    /**
     * 信用可用额度2
     */
    private BigDecimal creditLimitAvailable2;

    /**
     * 信用卡账单日2
     */
    private Integer creditBillDay2;

    /**
     * 信用卡还款日2
     */
    private Integer creditPayDay2;
}