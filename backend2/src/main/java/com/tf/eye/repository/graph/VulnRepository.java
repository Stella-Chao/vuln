package com.tf.eye.repository.graph;

import com.tf.eye.model.node.VulnerabilityNode;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @author zhanghe
 * @date 2022/3/10 23:01
 */

public interface VulnRepository extends Neo4jRepository<VulnerabilityNode, Long> {

    @Query(value = "match (n: Vuln) return n limit 10")
    List<VulnerabilityNode> getAllByCveId();

//    @Query(value = "MATCH (vuln:Vuln) RETURN vuln LIMIT 10")
//    List<VulnNode> getVulnList();

    @Query("MATCH (n:Vuln {cveid: {0}} ) return n")
    VulnerabilityNode findVulnNodeByCveId(String cveid);
}
