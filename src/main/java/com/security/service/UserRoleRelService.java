package com.security.service;

import com.security.entity.UserRoleRel;
import com.security.model.UserRoleRelDto;


public interface UserRoleRelService extends BaseService<UserRoleRel> {

    UserRoleRel toEntity(UserRoleRelDto dto);

    UserRoleRelDto toDto(UserRoleRel entity);
}
