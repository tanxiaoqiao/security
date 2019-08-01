package com.security.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Kris
 * @Date: 2019-07-30  10:20
 */
@Table
@Entity
@Data
public class UserRoleRel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Role role;
}
