package com.example.billback.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单表实体类
 */
@Data
public class Bill {
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
    private LocalDateTime billDate;
    
    /**
     * 备注
     */
    private String desc;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 收支类型:支出,入账,不计入
     */
    private String inoutType;
}
