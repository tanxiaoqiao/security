package com.security.security.logout;

import com.alibaba.fastjson.JSON;
import com.security.constant.ErrorEnum;
import com.security.util.ResponseObject;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出成功处理
 *
 */
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        if (authentication == null) {
            response.getWriter().write(JSON.toJSONString(ResponseObject.fail(ErrorEnum.NO_LOGIN)));
            return;
        }

        response.getWriter().write(JSON.toJSONString(ResponseObject.success("OK")));
    }
}
