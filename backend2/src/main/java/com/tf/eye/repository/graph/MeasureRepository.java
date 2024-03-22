package com.tf.eye.repository.graph;

import com.tf.eye.model.node.MeasureNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author zh
 * @date 2022/11/12 17:07
 */
public interface MeasureRepository extends Neo4jRepository<MeasureNode, Long> {

}
