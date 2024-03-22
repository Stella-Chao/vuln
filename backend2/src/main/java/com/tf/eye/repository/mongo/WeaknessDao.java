package com.tf.eye.repository.mongo;

import com.tf.eye.model.domain.CWE;
import com.tf.eye.model.domain.POC;
import com.tf.eye.model.node.WeaknessNode;
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
public class WeaknessDao {
    @Autowired
    MongoTemplate mongoTemplate;

    public List<CWE> getAllWeakness() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        return mongoTemplate.find(query, CWE.class);
    }
}
