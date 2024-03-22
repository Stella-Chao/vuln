package com.tf.eye.repository.graph;

import com.tf.eye.model.node.WeaknessNode;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author zhanghe
 * @date 2022/3/10 23:04
 */
public interface WeaknessRepository extends Neo4jRepository<WeaknessNode, Long> {
    @Query("MATCH (n:Weakness {cweId: {0}}) return n")
    WeaknessNode findTypeNodeBycweId(String cweId);

}
