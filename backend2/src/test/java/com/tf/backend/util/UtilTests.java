package com.tf.backend.util;

import com.tf.backend.utils.Neo4jUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author zhanghe
 * @date 2022/3/14 13:31
 */

@SpringBootTest
public class UtilTests {

    @Autowired
    Neo4jUtil neo4jUtil;

    @Test
    void neo4j() {
//        neo4jUtil.clearNeo4j();

//        neo4jUtil.addTypeNode();


        neo4jUtil.mongo2Neo4j();

//        neo4jUtil.addProductNode();

//        neo4jUtil.test();



    }

}
