package com.example.billback.config;

import com.example.billback.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.billback.common.Constant.USER_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    
    private final JwtUtils jwtUtils;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取请求头中的token
        String token = request.getHeader("Authorization");
        
        // 检查token是否存在
        if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            System.err.println("Token不存在");
            return false;
        }
        
        // 提取token
        token = token.substring(7);
        
        try {
            // 验证token
            if (!jwtUtils.validateToken(token)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                System.err.println("token无效");
                return false;
            }
            
            // 从token中提取用户ID并设置到请求属性中
            Integer userId = jwtUtils.extractUserId(token);
            request.setAttribute(USER_ID, userId);
            
            return true;
        } catch (Exception e) {
            System.err.println("Token验证失败");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            e.printStackTrace();
            return false;
        }
    }
}