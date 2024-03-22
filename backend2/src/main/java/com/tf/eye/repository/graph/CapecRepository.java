package com.tf.eye.repository.graph;

import com.tf.eye.model.node.CapecNode;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author zh
 * @date 2022/11/12 17:02
 */
public interface CapecRepository extends Neo4jRepository<CapecNode, Long> {

    @Query("MATCH (n:Capec {capecId: {0}} ) return n")
    CapecNode findCapecNodeByCapecId(String capecId);
}
