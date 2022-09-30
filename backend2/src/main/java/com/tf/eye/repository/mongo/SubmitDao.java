package com.tf.eye.repository.mongo;

import com.alibaba.fastjson.JSONObject;
import com.tf.eye.model.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class SubmitDao {
    @Autowired
    MongoTemplate mongoTemplate;

    // 将提交信息持久化到数据库
    public void insert(SubmitVuln vuln) {
        mongoTemplate.save(vuln);
    }

    // 获取漏洞提交总数
    public Long getSubmitNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        Long total = mongoTemplate.count(query, SubmitVuln.class);
        System.out.println("漏洞提交总数：" + total);
        return total;
    }

    public List<SubmitVuln> getAllSubmit() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        return mongoTemplate.find(query, SubmitVuln.class);
    }

    //查询集合里的所有文档 + 分页
    public List<SubmitVuln> getAllSubmit(Integer size, Integer page) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        query.skip((page - 1) * size).limit(size);
        return mongoTemplate.find(query, SubmitVuln.class);
    }

    public SubmitVuln getVulnByTFID(String tfid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tfid").is(tfid));
        return mongoTemplate.findOne(query, SubmitVuln.class);
    }

    public void deleteByTFID(String tfid) {
        Query query = new Query();
        query.addCriteria(Criteria.where("tfid").is(tfid));
        mongoTemplate.findAndRemove(query, SubmitVuln.class);
    }

    //多条件查询
    public String findByMulti(String tfid, String vendor, String attacker, String type, String title, Integer size, Integer page) {
        Query query = new Query();
        JSONObject json = new JSONObject();
        Pattern pattern=Pattern.compile(".*?" + title + ".*", Pattern.CASE_INSENSITIVE);
        System.out.println(pattern);
        if (!tfid.equals("")) {
            query.addCriteria(Criteria.where("tfid").is(tfid));
        }
        if (!attacker.equals("")) {
            query.addCriteria(Criteria.where("attacker").is(attacker));
        }
        if (!type.equals("")) {
            query.addCriteria(Criteria.where("type").is(type));
        }
        if (!title.equals("")) {
            query.addCriteria(Criteria.where("description").regex(pattern));
        }
        if (!vendor.equals("")) {
            query.addCriteria(Criteria.where("vendor").is(vendor));
        }
        System.out.println(query);
        Long total = mongoTemplate.count(query, SubmitVuln.class);
        query.skip((page - 1) * size).limit(size);
        json.put("total", total);
        json.put("result", mongoTemplate.find(query,SubmitVuln.class));
        return json.toJSONString();
    }

    public String updateVulnByID(JSONObject param) {
        System.out.println(param);
        String tfid = param.get("tfid").toString();
        SubmitVuln vuln = mongoTemplate.findAndRemove(Query.query(Criteria.where("tfid").is(tfid)), SubmitVuln.class);
        if (param.get("decription") != null) {
            vuln.setDescription(param.get("description").toString());
        }
        if (param.get("title") != null) {
            vuln.setTitle(param.get("title").toString());
        }
        if (param.get("type") != null) {
            vuln.setType(param.get("type").toString());
        }
        if (param.get("product") != null) {
            vuln.setProduct(param.get("product").toString());
        }
        if (param.get("attacker") != null) {
            vuln.setAttacker(param.get("attacker").toString());
        }
        if (param.get("vendor") != null) {
            vuln.setVendor(param.get("vendor").toString());
        }
        if (param.get("address") != null) {
            vuln.setAddress(param.get("address").toString());
        }
        System.out.println(vuln);
        mongoTemplate.save(vuln, "submit_iot");
        return "success";
    }
}
