package com.init.service;

import com.init.entity.Role;
import com.init.model.RoleDto;


public interface RoleService extends BaseService<Role> {

    Role toEntity(RoleDto dto);

    RoleDto toDto(Role entity);
}
