package com.zhang.backend2.dao.mongo;

import com.zhang.backend2.model.domain.CveDo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CveRepository extends MongoRepository<CveDo, Integer> {

    /* 通过CVE-ID查询漏洞 */
    CveDo getByName(String name);

    /* 通过描述信息查询漏洞 */
    CveDo getByDescriptionLike(String description);
}
