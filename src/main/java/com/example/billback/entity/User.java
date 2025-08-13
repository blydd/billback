package com.example.billback.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用户表实体类
 */
@Data
public class User {
    /**
     * 用户ID
     */
    private Integer id;
    
    /**
     * 用户名称
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    
    /**
     * 性别：男女
     */
    private String sex;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
