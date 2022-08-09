package com.tf.eye.repository.graph;

import com.tf.eye.model.node.ProductNode;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author zhanghe
 * @date 2022/3/10 23:04
 */
public interface ProductRepository extends Neo4jRepository<ProductNode, Long> {
    @Query("MATCH (n:Product {name: {0}}) return n")
    ProductNode findProductNodeByName(String name);
}
