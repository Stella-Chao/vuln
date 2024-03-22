package com.tf.eye.repository.graph;

import com.tf.eye.model.node.VendorNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author zh
 * @date 2022/11/12 17:08
 */
public interface VendorRepository extends Neo4jRepository<VendorNode, Long> {

}
