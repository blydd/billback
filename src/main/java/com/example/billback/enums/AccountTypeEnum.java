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
    SAVINGS("储蓄账户"),
    
    /**
     * 信用账户
     */
    CREDIT("信用账户");
    

    /**
     * 类型描述
     */
    private final String type;

    /**
     * 判断类型是否正确
     */
    public static boolean check(String type) {
        if (type == null) {
            return false;
        }
        for (AccountTypeEnum t : AccountTypeEnum.values()) {
            if (t.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
} 