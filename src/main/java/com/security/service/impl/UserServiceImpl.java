package com.security.service.impl;

import com.security.entity.User;
import com.security.repository.UserRepository;
import com.security.service.UserService;
import com.security.util.JpaUtils;
import com.security.model.UserDto;
import com.security.util.PasswordUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRep;

    @Override
    public void save(User entity) {
        entity.setPassword(PasswordUtils.sha256Encode(entity.getPassword()));
        userRep.save(entity);
    }

    @Override
    public void delete(User entity) {
        userRep.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        userRep.deleteById(id);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRep.findById(id);
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public Page<User> findPage() {
        Page<User> entityPage = userRep.findAll(JpaUtils.getPageRequest());
        return entityPage;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @Override
    public Page<User> findPage(Specification<User> specification) {
        Page<User> entityPage = userRep.findAll(specification, JpaUtils.getPageRequest());
        return entityPage;
    }

    @Override
    public User toEntity(UserDto dto) {
        User entity = new User();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public UserDto toDto(User entity) {
        UserDto dto = new UserDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public User findByName(String name) {
        return userRep.findByUsername( name);
    }

    @Override
    public User findByNameAndPassword(String name, String password) {
        String pwd = PasswordUtils.sha256Encode(password);
        return userRep.findByUsernameAndPassword(name,pwd);
    }
}
