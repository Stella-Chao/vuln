package com.tf.eye.model.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "capec")
public class CAPEC implements Serializable {
    private static final long serialVersionUID = -3733667693422327769L;

    @Field("capecId")
    String capecId;

    @Field("name")
    String name;

    @Field("likelihood")
    String likelihood;

    @Field("severity")
    String severity;

    @Field("description")
    String description;

    @Field("cwe")
    List<String> cwe;

}
