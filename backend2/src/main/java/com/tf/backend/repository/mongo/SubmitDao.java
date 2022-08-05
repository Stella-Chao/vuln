package com.tf.backend.repository.mongo;

import com.tf.backend.model.domain.SubmitVuln;
import com.tf.backend.model.domain.TFiot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class SubmitDao {
    @Autowired
    MongoTemplate mongoTemplate;

    // 将提交信息持久化到数据库
    public void insert(SubmitVuln vuln) {
        mongoTemplate.save(vuln);
    }

    // 获取漏洞提交总数
    public Long getSubmitNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        Long total = mongoTemplate.count(query, SubmitVuln.class);
        System.out.println("漏洞提交总数：" + total);
        return total;
    }
}
