package com.security.model;


import com.security.entity.Role;
import com.security.entity.User;
import lombok.Data;


import java.io.Serializable;

@Data
public class UserRoleRelDto implements Serializable {

    private long id;
    private User user;
    private Role role;
}
