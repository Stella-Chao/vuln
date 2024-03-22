package com.tf.eye.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;


@Node("Vendor")
@Data
public class VendorNode {
    @Id
    @GeneratedValue
    private Long id;
    @Property("name")
    private String name;
    @Property("alias")
    private String alias;
    @Property("cnName")
    private String cnName;
    @Property("iotRelevance")
    private String iotRelevance;
    @Relationship(type = "hasProduct", direction = OUTGOING)
    private Set<ProductNode> products = new HashSet<>();

    public VendorNode(String name) {
        this.name = name;
    }
}
