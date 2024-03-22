package com.tf.eye.repository.mongo;

import com.tf.eye.model.domain.CAPEC;
import com.tf.eye.model.domain.POC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zh
 * @date 2023/3/2 14:24
 */
@Repository
public class CAPECDao {
    @Autowired
    MongoTemplate mongoTemplate;


    public List<CAPEC> getAllCapec() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        return mongoTemplate.find(query, CAPEC.class);
    }
}
