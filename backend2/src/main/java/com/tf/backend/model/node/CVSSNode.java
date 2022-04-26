package com.tf.backend.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("CVSS")
@Data
public class CVSSNode {

    @Id @GeneratedValue
    private Long id;

    private String level;

    private Double score;

    private String attack;

}
