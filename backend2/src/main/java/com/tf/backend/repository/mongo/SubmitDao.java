package com.tf.backend.repository.mongo;

import com.tf.backend.model.domain.SubmitVuln;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SubmitDao {
    @Autowired
    MongoTemplate mongoTemplate;

    // 将提交信息持久化到数据库
    public void insert(SubmitVuln vuln) {
        mongoTemplate.save(vuln);
    }
}
