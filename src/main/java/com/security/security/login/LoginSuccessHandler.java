package com.security.security.login;

import com.alibaba.fastjson.JSON;
import com.security.entity.User;
import com.security.util.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录成功接口
 */
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        User currentUser = (User) authentication.getPrincipal();
        log.info("Login success: " + currentUser.getUsername());

        // 返回当前用户信息
        response.setContentType("application/json;charset=utf-8");
        currentUser.setPassword(null);
        response.getWriter().write(JSON.toJSONString(ResponseObject.success(currentUser)));
    }
}
