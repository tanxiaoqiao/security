package com.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: Kris
 * @Date: 2019-07-30  08:58
 */
@Table
@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<RoleResourceRel> roleResourceRels;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<UserRoleRel> userRoleRels;

}
