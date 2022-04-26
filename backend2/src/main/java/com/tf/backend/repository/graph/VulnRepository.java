package com.tf.backend.repository.graph;

import com.tf.backend.model.node.VulnNode;
import org.neo4j.driver.internal.shaded.reactor.core.publisher.Mono;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhanghe
 * @date 2022/3/10 23:01
 */

public interface VulnRepository extends Neo4jRepository<VulnNode, Long> {

    @Query(value = "match (n) return n order by n.cveid desc s skip 0 limit 20")
    List<VulnNode> getAllByCveId();

    @Query("MATCH (n:Vuln {cveid: {0}} ) return n")
    VulnNode findVulnNodeByCveId(String cveid);
}
