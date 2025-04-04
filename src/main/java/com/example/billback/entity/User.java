package com.example.billback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("users")
public class User {
    
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    private String openId;
    
    private String unionId;
    
    private String nickName;
    
    private String avatarUrl;
    
    private Integer gender;
    
    private String country;
    
    private String province;
    
    private String city;
    
    private String language;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}