package com.tf.eye.repository.mongo;

import com.tf.eye.model.domain.POC;
import com.tf.eye.model.domain.TFiot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    // 获取 Poc 总数
    public Long getPocNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        Long total = mongoTemplate.count(query, POC.class);
        System.out.println("Poc 总数：" + total);
        return total;
    }

    public List<POC> getAllPoc() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        return mongoTemplate.find(query, POC.class);
    }
}
