package com.security.service;

import com.security.entity.Role;
import com.security.model.RoleDto;

import java.util.List;


public interface RoleService extends BaseService<Role> {

    Role toEntity(RoleDto dto);

    RoleDto toDto(Role entity);

    List<Role> findAll();
}
