package com.tf.eye.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;


@Node("Code")
@Data
public class CodeNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property("url")
    private String url;
    @Property("code")
    private String code;
    @Property("verify")
    private String verify;
    @Property("type")
    private String type;
    @Property("author")
    private String author;

    @Relationship(type = "hasProven", direction = OUTGOING)
    private VulnerabilityNode vulnerabilityNode;

}
