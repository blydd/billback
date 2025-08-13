package com.example.billback.service;


import com.example.billback.common.Result;
import com.example.billback.entity.User;
import com.example.billback.vo.LoginResponse;

public interface UserService {

    Result<LoginResponse> wxLogin(User user);

    Result<User> register(User user);
}