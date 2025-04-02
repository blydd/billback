package com.example.billback.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收支类型枚举
 */
@Getter
@AllArgsConstructor
public enum InoutTypeEnum {
    
    /**
     * 支出
     */
    EXPENSE(1, "支出"),
    
    /**
     * 入账
     */
    INCOME(2, "入账"),
    
    /**
     * 不计入收支
     */
    NONE(3, "不计入收支");
    
    /**
     * 类型编码
     */
    private final Integer code;
    
    /**
     * 类型描述
     */
    private final String desc;
    
    /**
     * 根据编码获取枚举
     */
    public static InoutTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (InoutTypeEnum type : InoutTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 