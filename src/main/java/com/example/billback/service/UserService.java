package com.example.billback.service;


import com.example.billback.dto.WxLoginRequest;
import com.example.billback.vo.LoginResponse;

public interface UserService {
    LoginResponse wxLogin(WxLoginRequest request);
}