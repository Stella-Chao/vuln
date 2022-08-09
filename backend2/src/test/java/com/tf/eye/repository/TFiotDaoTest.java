package com.tf.eye.repository;

import com.tf.eye.repository.mongo.TFiotDao;
import com.tf.eye.model.domain.TFiot;

public class TFiotDaoTest {

    public static void main(String[] args) {
        TFiotDao dao = new TFiotDao();
        TFiot vuln = dao.getVulnByCVE("CVE-2021-41392");
        System.out.println(vuln);
    }
}
