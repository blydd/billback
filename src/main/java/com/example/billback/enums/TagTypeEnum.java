package com.example.billback.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 标签类型枚举
 */
@Getter
@AllArgsConstructor
public enum TagTypeEnum {
    
    /**
     * 支付方式
     */
    PAYMENT_METHOD(1, "支付方式"),
    
    /**
     * 账单类型
     */
    BILL_TYPE(2, "账单类型");
    
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
    public static TagTypeEnum getByCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (TagTypeEnum type : TagTypeEnum.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
} 