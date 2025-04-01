package com.example.billback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bills")
public class Bill extends BaseEntity {
    /**
     * 主键ID，采用数据库自增方式
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID，关联用户表的主键
     */
    private Long userId;

    /**
     * 账单金额
     */
    private BigDecimal amount;

    /**
     * 账单日期，使用@JsonFormat注解格式化为字符串
     * 以便在JSON序列化时按照指定格式输出日期时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime billDate;

    /**
     * 账单描述，用于记录账单的详细信息或备注
     */
    private String desc;

    /**
     * 账单类型，1代表支出，2代表收入
     */
    private String type;


} 