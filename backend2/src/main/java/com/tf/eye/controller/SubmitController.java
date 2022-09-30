package com.tf.eye.controller;

import com.alibaba.fastjson.JSONObject;
import com.tf.eye.model.domain.Device;
import com.tf.eye.model.domain.SubmitVuln;
import com.tf.eye.model.domain.Subscribe;
import com.tf.eye.model.domain.TFiot;
import com.tf.eye.repository.mongo.SubmitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/submit")
public class SubmitController {
    @Autowired
    SubmitDao submitDao;

    @PostMapping("add")
    public String submitVuln(@RequestBody JSONObject param) throws ParseException {
        System.out.println(param);
        SubmitVuln vuln = new SubmitVuln();
        if (param.get("tfid") == null || param.get("title") == null || param.get("description") == null || param.get("type") == null) {
            return "failed";
        }
        vuln.setTfid(param.get("tfid").toString());
        vuln.setTitle(param.get("title").toString());
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String today = f.format(date);
        vuln.setDate(f.parse(today));
        vuln.setAddress(param.get("address").toString());
        vuln.setAttacker(param.get("attacker").toString());
        vuln.setVendor(param.get("vendor").toString());
        vuln.setProduct(param.get("product").toString());
        vuln.setType(param.get("type").toString());
        vuln.setDescription(param.get("description").toString());
        try {
            submitDao.insert(vuln);
            return "success";
        } catch (Exception e) {
            System.out.println(e);
            return "failed";
        }
    }

    @GetMapping("list/{size}/{page}")
    public List<SubmitVuln> list(@PathVariable("size") Integer size, @PathVariable("page") Integer page) {
        return submitDao.getAllSubmit(size, page);
    }

    @GetMapping("get/count")
    public Long getTotal() {
        return submitDao.getSubmitNum();
    }


    @PostMapping("/delete")
    public String delete(@RequestBody JSONObject param) {
        System.out.println(param);
        String tfid = param.get("tfid").toString();
        submitDao.deleteByTFID(tfid);
        return "删除成功！";
    }

    @GetMapping("vuln")
    public SubmitVuln search(String tfid) {
        return submitDao.getVulnByTFID(tfid);
    }

    @GetMapping("search2")
    public String search(String tfid, String vendor, String attacker, String type, String title, Integer size, Integer page) {
        System.out.println("TF-ID" + tfid);
        System.out.println("vendor" + vendor);
        System.out.println("attacker" + attacker);
        System.out.println("title" + title);
        System.out.println("pageSize" + page);
        System.out.println("pageNum" + page);
        return submitDao.findByMulti(tfid, vendor, attacker, type, title, size, page);
    }

    @PostMapping("update")
    public String updateVuln(@RequestBody JSONObject param) {
        return submitDao.updateVulnByID(param);
    }

}
