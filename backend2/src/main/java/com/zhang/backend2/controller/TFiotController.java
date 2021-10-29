package com.zhang.backend2.controller;

import com.zhang.backend2.dao.mongo.SubmitDao;
import com.zhang.backend2.dao.mongo.TFiotDao;
import com.zhang.backend2.model.domain.SubmitVuln;
import com.zhang.backend2.model.domain.TFiot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("tf")
public class TFiotController {
    @Autowired
    TFiotDao iotDao;

    @Autowired
    SubmitDao subDao;

    @GetMapping("cve")
    public TFiot findByCVE(String cveID) {
        return iotDao.getVulnByCVE(cveID);
    }

    @GetMapping("search")
    public List<TFiot> search(String cveID, String severity, String description) {
        System.out.println(severity + "...");
        // 选中CVE-ID
        if (!cveID.equals("")) {
            List<TFiot> ans = new ArrayList<>();
            ans.add(iotDao.getVulnByCVE(cveID));
            return ans;
            // 未选中CVE-ID
        } else {
            if (description.equals("")) {
                if (severity.equals("高危")) {
                    return iotDao.getHighVuln();
                } else if(severity.equals("中危")) {
                    return iotDao.getMediumVuln();
                } else {
                    return iotDao.getLowVuln();
                }
            } else {
                System.out.println(description);
//                return iotDao.findByMulti(severity,description);
                return iotDao.findByDescription(description);
            }
        }
    }

    @PostMapping("submit")
    public String submitVuln(@RequestBody SubmitVuln vuln) {
        try {
            subDao.insert(vuln);
            return "success";
        } catch (Exception e) {
            System.out.println(e);
            return "failure";
        }
    }

    @GetMapping("test")
    public TFiot test() {
        Map<String,String> map = new HashMap<>();
        return iotDao.getVulnByCVE("CVE-2021-41392");
    }
}
