package com.init.service.impl;

import com.init.entity.Resource;
import com.init.repository.ResourceRepository;
import com.init.service.ResourceService;
import com.init.util.JpaUtils;
import com.init.model.ResourceDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    ResourceRepository resourceRep;

    @Override
    public void save(Resource entity) {
        resourceRep.save(entity);
    }

    @Override
    public void delete(Resource entity) {
        resourceRep.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        resourceRep.deleteById(id);
    }

    @Override
    public Optional<Resource> findById(Long id) {
        return resourceRep.findById(id);
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public Page<Resource> findPage() {
        Page<Resource> entityPage = resourceRep.findAll(JpaUtils.getPageRequest());
        return entityPage;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public Page<Resource> findPage(Specification<Resource> specification) {
        Page<Resource> entityPage = resourceRep.findAll(specification, JpaUtils.getPageRequest());
        return entityPage;
    }

    @Override
    public Resource toEntity(ResourceDto dto) {
        Resource entity = new Resource();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public ResourceDto toDto(Resource entity) {
        ResourceDto dto = new ResourceDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
