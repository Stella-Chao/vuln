package com.tf.eye.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("Product")
@Data
public class ProductNode {
    @Id @GeneratedValue
    private Long id;
    @Property("name")
    private String name;
    @Property("type")
    private String type;
    @Property("vendor")
    private String vendor;
    @Property("version")
    private String version;

    public ProductNode() {}
    public ProductNode(String name, String vendor) {
        this.name = name;
        this.vendor = vendor;
    }

    public ProductNode(String name, String type, String vendor) {
        this.name = name;
        this.type = type;
        this.vendor = vendor;
    }


}
