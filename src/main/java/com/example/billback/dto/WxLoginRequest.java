package com.example.billback.dto;

import lombok.Data;

/**
 * 微信登录请求DTO
 * 用于封装微信登录时需要的信息
 */
@Data
public class WxLoginRequest {
    /**
     * 微信登录码
     * 用户授权后获得的code，用于获取用户信息
     */
    private String code;

    /**
     * 用户信息
     * 包含用户详细的个人信息，如昵称、头像等
     */
    private UserInfo userInfo;

    /**
     * 用户信息内部类
     * 详细描述用户的相关信息，如昵称、头像、性别等
     */
    @Data
    public static class UserInfo {
        /**
         * 用户昵称
         * 用户在微信中的昵称
         */
        private String nickName;

        /**
         * 用户头像URL
         * 用户在微信中的头像地址
         */
        private String avatarUrl;

        /**
         * 用户性别
         * 用户在微信中设置的性别，1表示男性，2表示女性
         */
        private Integer gender;

        /**
         * 用户所在国家
         * 用户在微信中设置的所在国家
         */
        private String country;

        /**
         * 用户所在省份
         * 用户在微信中设置的所在省份
         */
        private String province;

        /**
         * 用户所在城市
         * 用户在微信中设置的所在城市
         */
        private String city;

        /**
         * 用户语言
         * 用户在微信中设置的语言
         */
        private String language;
    }
}
