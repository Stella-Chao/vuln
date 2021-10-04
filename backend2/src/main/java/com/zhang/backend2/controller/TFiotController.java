package com.zhang.backend2.controller;

import com.zhang.backend2.dao.mongo.TFiotDao;
import com.zhang.backend2.model.domain.TFiot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tf")
public class TFiotController {
    @Autowired
    TFiotDao iotDao;

    @GetMapping("cve")
    public TFiot findByCVE(String cveID) {
        return iotDao.getVulnByCVE(cveID);
    }

    @GetMapping("test")
    public TFiot test() {
        return iotDao.getVulnByCVE("CVE-2021-41392");
    }
}
