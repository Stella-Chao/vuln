package com.tf.eye.model.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Document(collection = "cwe")
public class CWE implements Serializable {
    private static final long serialVersionUID = -3785667693422327769L;

    @Field("cweId")
    private String cweId;

    @Field("name")
    private String name;

    @Field("description")
    private String description;


}
