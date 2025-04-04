package com.example.billback.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.billback.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    void insertInitTags(@Param("userId") Integer id);
}