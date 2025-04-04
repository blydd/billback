package com.example.billback.controller;

import com.example.billback.common.Result;
import com.example.billback.dto.WxLoginRequest;
import com.example.billback.service.UserService;
import com.example.billback.vo.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;
    
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody WxLoginRequest request) {
        LoginResponse response = userService.wxLogin(request);
        return Result.success(response);
    }

}