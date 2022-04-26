package com.tf.backend.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Type")
@Data
public class TypeNode {
    @Id @GeneratedValue
    private Long id;

    private String cweId;

    private String typeName;

    public TypeNode(){

    }
    public TypeNode(String cweId, String typeName) {
        this.cweId = cweId;
        this.typeName = typeName;
    }
    public TypeNode(String cweId) {
        this.cweId = cweId;
    }

}
