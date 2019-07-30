package com.security.model;


import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceDto implements Serializable {

    private long id;
    private String url;
}
