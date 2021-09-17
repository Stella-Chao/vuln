package com.zhang.backend2.service.impl;

import com.zhang.backend2.dao.mongo.CveRepository;
import com.zhang.backend2.model.domain.CveDo;
import com.zhang.backend2.service.CveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class CveServiceImpl implements CveService {
    @Autowired
    private CveRepository cveRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveCve(CveDo cveDo) {
        cveRepository.save(cveDo);
    }

    @Override
    public void removeCveByCveName(String name) {
        Query query = new Query(new Criteria("name").is(name));
        mongoTemplate.remove(query, CveDo.class);
    }

    /* 通过CVE-ID 来更新描述信息 */
    @Override
    public void updateCve(CveDo cveDo) {
        Query query = new Query(new Criteria("name").is(cveDo.getName()));
        Update update = new Update().set("description", cveDo.getDescription());
        mongoTemplate.updateMulti(query, update, CveDo.class);
    }

    /* 通过CVE-ID查询*/
    @Override
    public CveDo getByCveName(String name) {
        return cveRepository.getByName(name);
    }

    /* 通过相似描述查询 */
    @Override
    public CveDo getByCveDescriptionLike(String description) {
        return cveRepository.getByDescriptionLike(description);
    }
}
