package com.init.model;


import lombok.Data;

import java.util.*;

import java.io.Serializable;

@Data
public class ResourceDto implements Serializable {

    private long id;
    private String url;
}
