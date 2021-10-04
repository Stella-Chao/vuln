package com.zhang.backend2.dao.mongo;

import com.zhang.backend2.model.domain.TFiot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TFiotDao {
    @Autowired
    MongoTemplate mongoTemplate;

    //给集合中添加一条文档
    public void save(TFiot vuln) {
        mongoTemplate.save(vuln);
    }

    //查询集合里的所有文档
    public List<TFiot> getAllVuln() {
        return mongoTemplate.findAll(TFiot.class);
    }

    //通过CVE-ID查询
    public TFiot getVulnByCVE(String cveID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("CVE-ID").is(cveID));
        return mongoTemplate.findOne(query,TFiot.class);
    }
}
