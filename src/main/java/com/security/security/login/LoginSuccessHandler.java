package com.security.security.login;

import com.alibaba.fastjson.JSON;
import com.security.util.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
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
        com.security.entity.User user = new com.security.entity.User();
        BeanUtils.copyProperties(currentUser,user);
        user.setPassword(null);
        log.info("Login success: " + currentUser.getUsername());

        // 返回当前用户信息
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(ResponseObject.success(user)));
    }
}
