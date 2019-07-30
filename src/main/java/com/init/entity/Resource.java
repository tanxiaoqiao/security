package com.init.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: Kris
 * @Date: 2019-07-30  09:03
 */
@Table
@Entity
@Data
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String url;
}
