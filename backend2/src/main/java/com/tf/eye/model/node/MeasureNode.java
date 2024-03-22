package com.tf.eye.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;


@Node("Measure")
@Data
public class MeasureNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property("patchUrl")
    private String patchUrl;
    @Property("suggestion")
    private String suggestion;

    @Relationship(type = "hasMeasure", direction = OUTGOING)
    private VulnerabilityNode vulnerabilityNode;

}
