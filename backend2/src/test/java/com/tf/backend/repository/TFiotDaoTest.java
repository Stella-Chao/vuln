package com.tf.backend.repository;

import com.tf.backend.repository.mongo.TFiotDao;
import com.tf.backend.model.domain.TFiot;

public class TFiotDaoTest {

    public static void main(String[] args) {
        TFiotDao dao = new TFiotDao();
        TFiot vuln = dao.getVulnByCVE("CVE-2021-41392");
        System.out.println(vuln);
    }
}
