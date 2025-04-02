package com.example.billback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@TableName("tags")
public class Tag {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    /**
     * 收支类型:1-支出,2-入账,3-不计入收支
     */
    private Integer inoutType;
    /**
     * 标签类型:1-支付方式,2-账单类型
     */
    private Integer tagType;
    /**
     * 账户类型.标签类型为支付方式时才有:1-储蓄账户,2-信用账户
     */
    private Integer accountType;
    private String icon;
    private Long userId;
}