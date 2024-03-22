package com.tf.eye.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("Weakness")
@Data
public class WeaknessNode {
    @Id @GeneratedValue
    private Long id;

    @Property("cweId")
    private String cweId;
    @Property("name")
    private String name;
    @Property("description")
    private String description;
    public WeaknessNode(){}
    public WeaknessNode(String cweId, String typeName) {
        this.cweId = cweId;
        this.name = typeName;
    }
    public WeaknessNode(String cweId) {
        this.cweId = cweId;
    }

}
