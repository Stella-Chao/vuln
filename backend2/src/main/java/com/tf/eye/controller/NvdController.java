package com.tf.eye.controller;

import com.alibaba.fastjson.JSONObject;
import com.tf.eye.model.domain.Nvd;
import com.tf.eye.model.domain.SubmitVuln;
import com.tf.eye.model.domain.TFiot;
import com.tf.eye.model.domain.cve.NVD;
import com.tf.eye.repository.mongo.NvdDao;
import com.tf.eye.service.NvdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("nvd")
public class NvdController {
    @Autowired
    NvdDao nvdDao;

    @GetMapping("cve")
    public NVD findByCVE(String cveID) {
        return nvdDao.getVulnByCVE(cveID);
    }

    @GetMapping("search")
    public List<NVD> search(String cveID, String severity, String description) {
        System.out.println(severity + "...");
        // 选中CVE-ID
        if (!cveID.equals("")) {
            List<NVD> ans = new ArrayList<>();
            ans.add(nvdDao.getVulnByCVE(cveID));
            return ans;
            // 未选中CVE-ID
        } else {
            if (description.equals("")) {
                if (severity.equals("高危")) {
                    return nvdDao.getHighVuln();
                } else if(severity.equals("中危")) {
                    return nvdDao.getMediumVuln();
                } else {
                    return nvdDao.getLowVuln();
                }
            } else {
                System.out.println(description);
//                return iotDao.findByMulti(severity,description);
                return nvdDao.findByDescription(description);
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
        return nvdDao.findByMulti(cveID, severity, attack, type, description, size, page);
    }

    @GetMapping("test")
    public NVD test() {
        Map<String,String> map = new HashMap<>();
        return nvdDao.getVulnByCVE("CVE-2021-41392");
    }

    @GetMapping("list/{size}/{page}")
    public List<NVD> list(@PathVariable("size") Integer size, @PathVariable("page") Integer page) {
        return nvdDao.getAllVuln(size, page);
    }

    @GetMapping("get/count")
    public Long getTotal() {
        return nvdDao.getVulnNum();
    }

    @GetMapping("severity")
    public String getPieData() {
        JSONObject json = new JSONObject();
        json.put("超危", nvdDao.getCriticalNum());
        json.put("高危", nvdDao.getHighNum());
        json.put("中危", nvdDao.getMediumNum());
        json.put("低危", nvdDao.getLowNum());
        return json.toJSONString();
    }

    @GetMapping("highnum")
    public Long getHighNum() {
        Long total = 0L;
        total += nvdDao.getHighNum();
        total += nvdDao.getCriticalNum();
        System.out.println("高危漏洞数量：" + total);
        return total;
    }

}
