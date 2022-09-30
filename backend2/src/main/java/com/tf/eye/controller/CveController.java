package com.tf.eye.controller;

import com.alibaba.fastjson.JSONObject;
import com.tf.eye.model.domain.CVE2;
import com.tf.eye.repository.mongo.CveDao;
import com.tf.eye.repository.mongo.SubmitDao;
import com.tf.eye.repository.mongo.TFiotDao;
import com.tf.eye.model.domain.SubmitVuln;
import com.tf.eye.model.domain.TFiot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("cve")
public class CveController {
    @Autowired
    CveDao cveDao;

    @Autowired
    SubmitDao subDao;

    @GetMapping("cve")
    public CVE2 findByCVE(String cveID) {
        return cveDao.getVulnByCVE(cveID);
    }

    @GetMapping("search")
    public List<CVE2> search(String cveID, String severity, String description) {
        System.out.println(severity + "...");
        // 选中CVE-ID
        if (!cveID.equals("")) {
            List<CVE2> ans = new ArrayList<>();
            ans.add(cveDao.getVulnByCVE(cveID));
            return ans;
            // 未选中CVE-ID
        } else {
            if (description.equals("")) {
                if (severity.equals("高危")) {
                    return cveDao.getHighVuln();
                } else if(severity.equals("中危")) {
                    return cveDao.getMediumVuln();
                } else {
                    return cveDao.getLowVuln();
                }
            } else {
                System.out.println(description);
//                return cveDao.findByMulti(severity,description);
                return cveDao.findByDescription(description);
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
        return cveDao.findByMulti(cveID, severity, attack, type, description, size, page);
    }


    @PostMapping("submit")
    public String submitVuln(@RequestBody JSONObject param) {
        System.out.println(param);
        SubmitVuln vuln = new SubmitVuln();
        if (param.get("title") == null || param.get("description") == null || param.get("type") == null) {
            return "failed";
        }
        vuln.setTitle(param.get("title").toString());
        vuln.setDate(new Date());
        vuln.setAddress(param.get("address").toString());
        vuln.setAttacker(param.get("attacker").toString());
        vuln.setVendor(param.get("vendor").toString());
        vuln.setProduct(param.get("product").toString());
        vuln.setType(param.get("type").toString());
        vuln.setDescription(param.get("description").toString());
        try {
            subDao.insert(vuln);
            return "success";
        } catch (Exception e) {
            System.out.println(e);
            return "failed";
        }
    }

    @GetMapping("get/submitNum")
    public Long getSubmitTotal() {
        return subDao.getSubmitNum();
    }

    @GetMapping("test")
    public CVE2 test() {
        Map<String,String> map = new HashMap<>();
        return cveDao.getVulnByCVE("CVE-2021-41392");
    }

    @GetMapping("list/{size}/{page}")
    public List<CVE2> list(@PathVariable("size") Integer size, @PathVariable("page") Integer page) {
        return cveDao.getAllVuln(size, page);
    }

    @GetMapping("get/count")
    public Long getTotal() {
        return cveDao.getVulnNum();
    }

    @GetMapping("severity")
    public String getPieData() {
        JSONObject json = new JSONObject();
        json.put("超危", cveDao.getCriticalNum());
        json.put("高危", cveDao.getHighNum());
        json.put("中危", cveDao.getMediumNum());
        json.put("低危", cveDao.getLowNum());
        return json.toJSONString();
    }

    @GetMapping("highnum")
    public Long getHighNum() {
        Long total = 0L;
        total += cveDao.getHighNum();
        total += cveDao.getCriticalNum();
        System.out.println("高危漏洞数量：" + total);
        return total;
    }

    @PostMapping("update")
    public String updateVuln(@RequestBody JSONObject param) {
        return cveDao.updateVulnByID(param);
    }

    @GetMapping("get/month")
    public String getRecentMonth() {
        return cveDao.getMonthly();
    }

    @GetMapping("get/vendor")
    public String getTopVendor() {
        JSONObject body = new JSONObject();
        body.put("海康威视", cveDao.getVendorNum("hikvision"));
        body.put("大华", cveDao.getVendorNum("Dahua"));
        body.put("思科", cveDao.getVendorNum("cisco"));
        body.put("海康威视", cveDao.getVendorNum("hikvision"));
        body.put("海康威视", cveDao.getVendorNum("hikvision"));
        body.put("海康威视", cveDao.getVendorNum("hikvision"));
        body.put("海康威视", cveDao.getVendorNum("hikvision"));
        body.put("海康威视", cveDao.getVendorNum("hikvision"));
        body.put("海康威视", cveDao.getVendorNum("hikvision"));
        body.put("海康威视", cveDao.getVendorNum("hikvision"));
        return body.toJSONString();
    }
}
