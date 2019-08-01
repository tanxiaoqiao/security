package com.security.repository;

import com.security.entity.UserRoleRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRelRepository extends JpaRepository<UserRoleRel, Long>, JpaSpecificationExecutor<UserRoleRel> {

}
