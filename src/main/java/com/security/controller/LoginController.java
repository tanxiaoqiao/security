package com.security.controller;

import com.security.constant.ErrorEnum;
import com.security.entity.User;
import com.security.security.SessionManager;
import com.security.service.UserService;
import com.security.util.ResponseObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录控制
 */
@Api(tags = "登录模块")
@RestController
public class LoginController {


    @Autowired
    UserService userService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResponseObject login(
            HttpServletResponse response,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password) {

        User loginUser = userService.findByNameAndPassword(username, password);
        if (loginUser == null) {
            return ResponseObject.fail(ErrorEnum.PASSWORD_ERROR);
        }
        // 注册用户
        loginUser.setPassword(null);
        SessionManager.registerUser(loginUser);
        return ResponseObject.success(loginUser);
    }
}
