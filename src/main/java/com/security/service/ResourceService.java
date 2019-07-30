package com.security.service;

import com.security.entity.Resource;
import com.security.model.ResourceDto;


public interface ResourceService extends BaseService<Resource> {

    Resource toEntity(ResourceDto dto);

    ResourceDto toDto(Resource entity);
}
