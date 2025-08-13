package com.example.billback.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.billback.common.Result;
import com.example.billback.entity.User;
import com.example.billback.mapper.UserMapper;
import com.example.billback.service.UserService;
import com.example.billback.utils.JwtUtils;

import com.example.billback.vo.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    

    @Override
    public Result<LoginResponse> wxLogin(User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error( 100,"用户名或密码不能为空");
        }
        // 查询用户是否存在
        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername()));
        if (Objects.isNull(existUser)){
            return Result.error( 100,"用户不存在");
        }
        if (!StrUtil.equals(user.getPassword(),existUser.getPassword())){
            return Result.error( 100,"密码不正确");
        }

        // 生成JWT令牌
        String token = jwtUtils.generateToken(existUser.getId());
        
        // 返回登录响应
        return Result.success(LoginResponse.builder()
                .userId(existUser.getId())
                .token(token)
                .username(user.getUsername())
                .build());
    }

    @Override
    public Result<User> register(User user) {
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error( 100,"用户名或密码不能为空");
        }
        if (StrUtil.isBlank(user.getSex())){
            return Result.error( 100,"性别不能为空");
        }
        if (!StrUtil.equalsAny(user.getSex(),"男","女")){
            return Result.error( 100,"性别只能是男或女");
        }
        //用户名不能重复
        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUsername, user.getUsername()));
        if (Objects.nonNull(existUser)){
            return Result.error( 100,"用户名已存在");
        }
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
        return Result.success(user);
    }
}