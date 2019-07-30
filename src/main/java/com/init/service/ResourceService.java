package com.init.service;

import com.init.entity.Resource;
import com.init.model.ResourceDto;


public interface ResourceService extends BaseService<Resource> {

    Resource toEntity(ResourceDto dto);

    ResourceDto toDto(Resource entity);
}
