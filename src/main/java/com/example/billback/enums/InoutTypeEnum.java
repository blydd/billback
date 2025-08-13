package com.example.billback.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.example.billback.common.Constant.*;

/**
 * 收支类型枚举
 */
@Getter
@AllArgsConstructor
public enum InoutTypeEnum {
    
    /**
     * 支出
     */
    OUT(INOUTTYPE_OUT),
    
    /**
     * 入账
     */
    IN(INOUTTYPE_IN),
    
    /**
     * 不计入收支
     */
    NO(INOUTTYPE_NO);
    

    
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
        for (InoutTypeEnum t : InoutTypeEnum.values()) {
            if (t.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
} 