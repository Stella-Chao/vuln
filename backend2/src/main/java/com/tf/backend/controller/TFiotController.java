package com.tf.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.tf.backend.dao.mongo.SubmitDao;
import com.tf.backend.dao.mongo.TFiotDao;
import com.tf.backend.model.domain.SubmitVuln;
import com.tf.backend.model.domain.TFiot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("search2")
    public String search(String cveID, String severity, String attack, String type, String description, Integer size, Integer page) {
        System.out.println("CVE-ID" + cveID);
        System.out.println("severity" + severity);
        System.out.println("attack" + attack);
        System.out.println("desciption" + description);
        System.out.println("pageSize" + page);
        System.out.println("pageNum" + page);
        return iotDao.findByMulti(cveID, severity, attack, type, description, size, page);
    }


    @PostMapping("submit")
    public String submitVuln(@RequestBody JSONObject param) {
        System.out.println(param);
        SubmitVuln vuln = new SubmitVuln();
        vuln.setTitle(param.get("title").toString());
        vuln.setDate(new Date());
        vuln.setAddress(param.get("address").toString());
        vuln.setAttacker(param.get("attacker").toString());
        vuln.setVentor(param.get("vendor").toString());
        vuln.setProduct(param.get("product").toString());
        vuln.setType(param.get("type").toString());
        vuln.setDescription(param.get("description").toString());
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

    @GetMapping("list/{size}/{page}")
    public List<TFiot> list(@PathVariable("size") Integer size, @PathVariable("page") Integer page) {
        return iotDao.getAllVuln(size, page);
    }

    @GetMapping("get/count")
    public Long getTotal() {
        return iotDao.getVulnNum();
    }

    @GetMapping("severity")
    public String getPieData() {
        JSONObject json = new JSONObject();
        json.put("超危", iotDao.getCriticalNum());
        json.put("高危", iotDao.getHighNum());
        json.put("中危", iotDao.getMediumNum());
        json.put("低危", iotDao.getLowNum());
        return json.toJSONString();
    }
}
