package com.tf.eye.repository.mongo;

import com.tf.eye.model.domain.Device;
import com.tf.eye.model.domain.Subscribe;
import com.tf.eye.model.domain.TFiot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubscribeDao {

    @Autowired
    MongoTemplate mongoTemplate;

    public void save(Subscribe subscribe) {
        mongoTemplate.save(subscribe);
    }

    public List<Subscribe> getAllSubscribe() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        return mongoTemplate.find(query, Subscribe.class);
    }

    public Long getSubscribeNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        return mongoTemplate.count(query, Subscribe.class);
    }
}
