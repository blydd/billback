package com.example.billback.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.example.billback.common.Constant;
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
        //判断是否调用的增删改接口
        boolean ifUpdate = false;
        if (CollUtil.containsAny(Constant.updateMethods, StrUtil.split(request.getRequestURI(),"/"))) {
            ifUpdate = true;
        }
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
        Integer userId = 1;
        try {
            //测试用户，且调的增删改接口，则提示登录
//            if (StrUtil.equals("user.test.token",token) && ifUpdate){
//                response.setStatus(HttpStatus.CONTINUE.value());
//                response.setContentType("application/json;charset=UTF-8"); // 设置响应类型
//                response.getWriter().write("{\"code\":100,\"msg\":\"请先登录\"}");
//                response.getWriter().flush(); // 刷新输出流
//                response.getWriter().close(); // 关闭输出流
//                response.flushBuffer(); // 清空响应缓冲区
//                System.err.println("测试用户尝试访问增删改接口，已提示登录"); // 添加日志记录
//                return false;
//            }
            if (StrUtil.equals("user.test.token",token)){
                //tokenw无效,默认测试用户
                request.setAttribute(USER_ID, userId);
                return true;
            }
            // 验证token
            if (!jwtUtils.validateToken(token)) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                System.err.println("token无效");
                return false;
            }
            
            // 从token中提取用户ID并设置到请求属性中
            userId = jwtUtils.extractUserId(token);
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