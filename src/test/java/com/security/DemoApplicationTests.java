package com.security;

import com.security.entity.Role;
import com.security.entity.User;
import com.security.entity.UserRoleRel;
import com.security.service.RoleService;
import com.security.service.UserRoleRelService;
import com.security.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Autowired
    UserRoleRelService userRoleRelService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        User user = userService.findById(1L).orElse(null);
        Role role = roleService.findById(1L).orElse(null);
        UserRoleRel userRoleRel = new UserRoleRel();
        userRoleRel.setRole(role);
        userRoleRel.setUser(user);
        userRoleRelService.save(userRoleRel);
    }

}
