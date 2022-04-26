package com.tf.backend.repository.graph;

import com.tf.backend.model.node.CVSSNode;
import org.neo4j.driver.internal.shaded.reactor.core.publisher.Mono;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @author zhanghe
 * @date 2022/3/10 23:04
 */
public interface CVSSRepository extends Neo4jRepository<CVSSNode, Long> {
    Mono<CVSSNode> findCVSSNodeById(Long id);
}
