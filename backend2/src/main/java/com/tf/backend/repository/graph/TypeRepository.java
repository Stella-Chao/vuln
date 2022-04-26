package com.tf.backend.repository.graph;

import com.tf.backend.model.node.TypeNode;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author zhanghe
 * @date 2022/3/10 23:04
 */
public interface TypeRepository extends Neo4jRepository<TypeNode, Long> {
    @Query("MATCH (n:Type {cweId: {0}}) return n")
    TypeNode findTypeNodeBycweId(String cweId);
}
