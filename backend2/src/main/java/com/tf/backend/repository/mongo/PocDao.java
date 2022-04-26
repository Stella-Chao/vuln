package com.tf.backend.repository.mongo;

import com.tf.backend.model.domain.POC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PocDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public POC getPocByCve(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cve").is(id));
        System.out.println("查询POC...");
        System.out.println(query);
        return mongoTemplate.findOne(query,POC.class);
    }
}
