package com.example.billback.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.billback.dto.WxLoginRequest;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    private final RestTemplate restTemplate;
    private final JwtUtils jwtUtils;
    
    @Value("${wechat.appid}")
    private String appId;
    
    @Value("${wechat.secret}")
    private String appSecret;
    
    @Override
    public LoginResponse wxLogin(WxLoginRequest request) {
        // 获取微信登录凭证
        String code = request.getCode();
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId +
                "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        JSONObject jsonObject = JSONUtil.parseObj(responseEntity.getBody());
        
        // 检查微信返回是否有错误
        if (jsonObject.containsKey("errcode") && jsonObject.getInt("errcode") != 0) {
            log.error("微信登录失败: {}", jsonObject.getStr("errmsg"));
            throw new RuntimeException("微信登录失败: " + jsonObject.getStr("errmsg"));
        }
        
        // 获取openid和unionid
        String openId = jsonObject.getStr("openid");
        String unionId = jsonObject.getStr("unionid");
        String sessionKey = jsonObject.getStr("session_key");
        
        // 查询用户是否存在
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getOpenId, openId));
        
        WxLoginRequest.UserInfo userInfo = request.getUserInfo();
        
        if (user == null) {
            // 创建新用户
            user = new User();
            user.setOpenId(openId);
            user.setUnionId(unionId);
            user.setNickName(userInfo.getNickName());
            user.setAvatarUrl(userInfo.getAvatarUrl());
            user.setGender(userInfo.getGender());
            user.setCountry(userInfo.getCountry());
            user.setProvince(userInfo.getProvince());
            user.setCity(userInfo.getCity());
            user.setLanguage(userInfo.getLanguage());
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            
            userMapper.insert(user);

            //初始化标签数据
            userMapper.insertInitTags(user.getId());
        } else {
            // 更新用户信息
            user.setNickName(userInfo.getNickName());
            user.setAvatarUrl(userInfo.getAvatarUrl());
            user.setGender(userInfo.getGender());
            user.setCountry(userInfo.getCountry());
            user.setProvince(userInfo.getProvince());
            user.setCity(userInfo.getCity());
            user.setLanguage(userInfo.getLanguage());
            user.setUpdatedAt(LocalDateTime.now());
            
            userMapper.updateById(user);
        }
        
        // 生成JWT令牌
        String token = jwtUtils.generateToken(user.getId(), openId);
        
        // 返回登录响应
        return LoginResponse.builder()
                .userId(user.getId())
                .token(token)
                .userInfo(LoginResponse.UserInfoDTO.builder()
                        .nickName(user.getNickName())
                        .avatarUrl(user.getAvatarUrl())
                        .build())
                .build();
    }
}