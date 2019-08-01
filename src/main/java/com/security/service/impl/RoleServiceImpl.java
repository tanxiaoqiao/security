package com.security.service.impl;

import com.security.entity.Role;
import com.security.repository.RoleRepository;
import com.security.service.RoleService;
import com.security.util.JpaUtils;
import com.security.model.RoleDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRep;

    @Override
    public void save(Role entity) {
        roleRep.save(entity);
    }

    @Override
    public void delete(Role entity) {
        roleRep.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        roleRep.deleteById(id);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRep.findById(id);
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public Page<Role> findPage() {
        Page<Role> entityPage = roleRep.findAll(JpaUtils.getPageRequest());
        return entityPage;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public Page<Role> findPage(Specification<Role> specification) {
        Page<Role> entityPage = roleRep.findAll(specification, JpaUtils.getPageRequest());
        return entityPage;
    }

    @Override
    public Role toEntity(RoleDto dto) {
        Role entity = new Role();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public RoleDto toDto(Role entity) {
        RoleDto dto = new RoleDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public List<Role> findAll() {
        return roleRep.findAll();
    }
}
