package com.example.billback.entity;

import lombok.Data;

/**
 * 账单标签关联表实体类
 */
@Data
public class BillTag {
    /**
     * 账单ID
     */
    private Integer billId;
    
    /**
     * 标签ID
     */
    private Integer tagId;
}
