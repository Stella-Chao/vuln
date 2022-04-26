package com.tf.backend.model.node;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node("Vuln")
@Data
public class VulnNode {

    @Id @GeneratedValue
    private Long id;
    @Property("cveid")
    private String cveId;
    @Property("title")
    private String title;
    @Property("publishedDate")
    private String publishedDate;
    @Property("lastModified")
    private String lastModified;
    @Relationship(type = "RELATED", direction = OUTGOING)
    private Set<VulnNode> vulns = new HashSet<>();
    @Relationship(type = "TYPEIS", direction = OUTGOING)
    private Set<TypeNode> types = new HashSet<>();
    @Relationship(type = "FOUND", direction = OUTGOING)
    private Set<ProductNode> products = new HashSet<>();
    @Relationship(type = "CVSSIS", direction = OUTGOING)
    private Set<CVSSNode> cvss = new HashSet<>();

}
