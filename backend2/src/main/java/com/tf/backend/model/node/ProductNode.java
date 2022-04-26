package com.tf.backend.model.node;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Product")
@Data
public class ProductNode {
    @Id @GeneratedValue
    private Long id;

    private String name;

    private String type;

    private String vendor;

    public ProductNode() {

    }
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
