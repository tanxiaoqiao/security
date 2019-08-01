package com.security.service;

import com.security.entity.User;
import com.security.model.UserDto;


public interface UserService extends BaseService<User> {

    User toEntity(UserDto dto);

    UserDto toDto(User entity);

    User findByName(String name);
}
