package com.init.entity;

import lombok.Data;

import javax.persistence.*;

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
}
