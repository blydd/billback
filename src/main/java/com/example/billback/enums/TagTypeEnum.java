package com.example.billback.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.billback.common.Constant.TAGTYPE_BILL;
import static com.example.billback.common.Constant.TAGTYPE_PAY;

/**
 * 标签类型枚举
 */
@Getter
@AllArgsConstructor
public enum TagTypeEnum {
    
    /**
     * 支付方式
     */
    PAYMENT_METHOD(TAGTYPE_PAY),
    
    /**
     * 账单类型
     */
    BILL_TYPE(TAGTYPE_BILL);

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
        for (TagTypeEnum t : TagTypeEnum.values()) {
            if (t.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
} 