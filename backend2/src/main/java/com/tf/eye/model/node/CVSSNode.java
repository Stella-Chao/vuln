package com.tf.eye.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("CVSS")
@Data
public class CVSSNode {

    @Id @GeneratedValue
    private Long id;
    @Property("level")
    private String level;
    @Property("score")
    private Double score;
    @Property("attack")
    private String attack;
}


