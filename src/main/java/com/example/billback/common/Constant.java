package com.example.billback.common;

/**
 * @author: bgt
 * @Date: 2025/4/3 16:16
 * @Desc:
 */

import java.util.Arrays;
import java.util.List;

/**
 * 常量类
 */
public class Constant {

    public static final String USER_ID = "userId";

    //收支类型
    public static final String INOUTTYPE_IN = "入账";
    public static final String INOUTTYPE_OUT = "支出";
    public static final String INOUTTYPE_NO = "不计入";
    //标签类型
    public static final String TAGTYPE_PAY = "支付方式";
    public static final String TAGTYPE_BILL = "账单类型";

    //增删改接口
    public static final List<String> updateMethods = Arrays.asList("save", "update", "delete");
}
