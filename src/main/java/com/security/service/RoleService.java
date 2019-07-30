package com.security.service;

import com.security.entity.Role;
import com.security.model.RoleDto;


public interface RoleService extends BaseService<Role> {

    Role toEntity(RoleDto dto);

    RoleDto toDto(Role entity);
}
