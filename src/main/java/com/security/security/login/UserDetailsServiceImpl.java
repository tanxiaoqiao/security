package com.security.security.login;

import com.security.constant.ErrorEnum;
import com.security.entity.Role;
import com.security.entity.User;
import com.security.entity.UserRoleRel;
import com.security.exception.BusinessException;
import com.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Kris  自定义身份验证
 * @Date: 2019-08-01  10:02
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByName(username);
        if (user == null) {
            throw new BusinessException(ErrorEnum.NOT_FOUND);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (UserRoleRel role : user.getUserRoleRels()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 加密
        String encodedPassword = passwordEncoder.encode(user.getPassword().trim());
        user.setPassword(encodedPassword);
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }
}

