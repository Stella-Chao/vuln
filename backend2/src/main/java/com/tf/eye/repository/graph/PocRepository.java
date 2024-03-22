package com.tf.eye.repository.graph;

import com.tf.eye.model.node.CodeNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author zh
 * @date 2022/11/12 17:06
 */
public interface PocRepository extends Neo4jRepository<CodeNode, Long> {

}
