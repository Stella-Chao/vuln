package com.tf.eye.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("AttackPattern")
@Data
public class CapecNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property("capecId")
    String capecId;
    @Property("name")
    String name;
    @Property("likelihood")
    String likelihood;
    @Property("severity")
    String severity;
    @Property("description")
    String description;
    @Relationship(type = "hasExploited", direction = OUTGOING)
    private List<WeaknessNode> cwes;


}
