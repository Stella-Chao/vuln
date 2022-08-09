package com.tf.eye.model.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Reference implements Serializable {

    private static final long serialVersionUID = 9125756463479006771L;

    private String url;

    private String name;

    private String refsource;

    private List<String> tags;
}
