package com.tf.backend.controller;

import com.tf.backend.repository.mongo.PocDao;
import com.tf.backend.model.domain.POC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("poc")
public class PocController {
    @Autowired
    PocDao pocDao;

    @GetMapping("get")
    public POC findPoc(String cveID) {
        return pocDao.getPocByCve(cveID);
    }

    @GetMapping("get/pocNum")
    public Long getPocTotal() {
        return pocDao.getPocNum();
    }
}
