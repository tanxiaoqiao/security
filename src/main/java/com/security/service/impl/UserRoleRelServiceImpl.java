package com.security.service.impl;

import com.security.entity.UserRoleRel;
import com.security.repository.UserRoleRelRepository;
import com.security.service.UserRoleRelService;
import com.security.util.JpaUtils;
import com.security.model.UserRoleRelDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserRoleRelServiceImpl implements UserRoleRelService {

    @Autowired
    UserRoleRelRepository userRoleRelRep;

    @Override
    public void save(UserRoleRel entity) {
        userRoleRelRep.save(entity);
    }

    @Override
    public void delete(UserRoleRel entity) {
        userRoleRelRep.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        userRoleRelRep.deleteById(id);
    }

    @Override
    public Optional<UserRoleRel> findById(Long id) {
        return userRoleRelRep.findById(id);
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public Page<UserRoleRel> findPage() {
        Page<UserRoleRel> entityPage = userRoleRelRep.findAll(JpaUtils.getPageRequest());
        return entityPage;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public Page<UserRoleRel> findPage(Specification<UserRoleRel> specification) {
        Page<UserRoleRel> entityPage = userRoleRelRep.findAll(specification, JpaUtils.getPageRequest());
        return entityPage;
    }

    @Override
    public UserRoleRel toEntity(UserRoleRelDto dto) {
        UserRoleRel entity = new UserRoleRel();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public UserRoleRelDto toDto(UserRoleRel entity) {
        UserRoleRelDto dto = new UserRoleRelDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }




}
