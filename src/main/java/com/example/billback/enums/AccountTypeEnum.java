package com.example.billback.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户类型枚举
 */
@Getter
@AllArgsConstructor
public enum AccountTypeEnum {
    
    /**
     * 储蓄账户
     */
    SAVINGS(1, "储蓄账户"),
    
    /**
     * 信用账户
     */
    CREDIT(2, "信用账户");
    
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
    public static AccountTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (AccountTypeEnum type : AccountTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 