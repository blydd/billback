package com.example.billback.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 账单 标签中间表 一对多
 */
@Data
@TableName("bill_tags")
@Accessors(chain = true)
public class BillTags {
    private Long billId;
    private Long tagId;
}