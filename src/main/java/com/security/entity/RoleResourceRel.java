package com.security.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Kris
 * @Date: 2019-07-30  09:39
 */
@Table
@Entity
@Data
public class RoleResourceRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private  Role role;

    @ManyToOne
    private  Resource resource;

}
