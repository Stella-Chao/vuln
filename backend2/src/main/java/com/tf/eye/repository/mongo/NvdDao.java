package com.tf.eye.repository.mongo;

import com.alibaba.fastjson.JSONObject;
import com.tf.eye.model.domain.cve.NVD;
import com.tf.eye.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zh
 * @date 2022/8/25 16:42
 */
@Repository
public class NvdDao {
    @Autowired
    MongoTemplate mongoTemplate;

    //给集合中添加一条文档
    public void save(NVD vuln) {
        mongoTemplate.save(vuln);
    }

    public List<NVD> getAllVuln() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        return mongoTemplate.find(query, NVD.class);
    }

    //查询集合里的所有文档 + 分页
    public List<NVD> getAllVuln(Integer size, Integer page) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        query.skip((page - 1) * size).limit(size);
        return mongoTemplate.find(query, NVD.class);
    }

    //通过CVE-ID查询
    public NVD getVulnByCVE(String cveID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("cve.cveDataMeta.ID").is(cveID));
        NVD vuln = mongoTemplate.findOne(query,NVD.class);
        System.out.println(vuln);
        return vuln;
    }

    //统计漏洞总数量
    public Long getVulnNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        Long total = mongoTemplate.count(query,NVD.class);
        System.out.println("漏洞总数：" + total);
        return total;
    }

    //统计超危漏洞数量
    public Long getCriticalNum() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("CRITICAL"),
                Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("超危"));
        query.addCriteria(sql);
        return mongoTemplate.count(query,NVD.class);
    }
    //统计高危漏洞数量
    public Long getHighNum() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("HIGH"),
                Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("高危"));
        query.addCriteria(sql);
        return mongoTemplate.count(query,NVD.class);
    }
    //统计中危漏洞数量
    public Long getMediumNum() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("MEDIUM"),
                Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("中危"));
        query.addCriteria(sql);
        return mongoTemplate.count(query,NVD.class);
    }
    //统计低危漏洞数量
    public Long getLowNum() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("LOW"),
                Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("低危"));
        query.addCriteria(sql);
        return mongoTemplate.count(query,NVD.class);
    }
    //统计高危漏洞
    public List<NVD> getHighVuln() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV2.severity").is("HIGH"),
                Criteria.where("baseMetricV2.severity").is("高危"));
        query.addCriteria(sql);
        return mongoTemplate.find(query,NVD.class);
    }
    //统计中危漏洞
    public List<NVD> getMediumVuln() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV2.severity").is("MEDIUM"),
                Criteria.where("baseMetricV2.severity").is("中危"));
        query.addCriteria(sql);
        return mongoTemplate.find(query,NVD.class);
    }
    //统计低危漏洞
    public List<NVD> getLowVuln() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV2.severity").is("LOW"),
                Criteria.where("baseMetricV2.severity").is("低危"));
        query.addCriteria(sql);
        return mongoTemplate.find(query,NVD.class);
    }

    //今日新增数量
    public Long getTodayNum() {
        return 0L;
    }

    //近一周新增数量
    public Long getWeekNum() {
        String day = DateUtil.getDate("Week");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.count(query,NVD.class);
    }
    //近一月新增数量
    public Long getMonthNum() {
        String day = DateUtil.getDate("Month");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.count(query,NVD.class);
    }
    //近一年新增数量
    public Long getYearNum() {
        String day = DateUtil.getDate("Year");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.count(query,NVD.class);
    }
    //今日新增漏洞
    public List<NVD> getToDayVuln() {
        return null;
    }
    //近一周新增漏洞
    public List<NVD> getWeekVuln() {
        String day = DateUtil.getDate("Week");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.find(query,NVD.class);
    }
    //近一月新增漏洞
    public List<NVD> getMonthVuln() {
        String day = DateUtil.getDate("Month");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.find(query,NVD.class);
    }
    //近一年新增漏洞
    public List<NVD> getYearVuln() {
        String day = DateUtil.getDate("Year");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.find(query,NVD.class);
    }

    //按年份统计漏洞数量
    public Long getNumByYear(String year) {
        return null;
    }

    //获取含有POC的漏洞数
    public Long getPocNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("POC").ne(""));
        return mongoTemplate.count(query,NVD.class);
    }

    //多条件查询
    public String findByMulti(String cveID, String severity, String attack, String type, String description, Integer size, Integer page) {
        Query query = new Query();
        JSONObject json = new JSONObject();
        Pattern pattern=Pattern.compile(".*?" + description + ".*", Pattern.CASE_INSENSITIVE);
        System.out.println(pattern);
        if (!cveID.equals("")) {
            query.addCriteria(Criteria.where("CVE-ID").is(cveID));
        }
        if (!severity.equals("undefined")) {
            query.addCriteria(Criteria.where("baseMetricV3.cvssV3.baseSeverity").is(severity));
        }
        if (!attack.equals("undefined")) {
            query.addCriteria(Criteria.where("baseMetricV3.cvssV3.attackVector").is(attack));
        }
        if (!type.equals("undefined")) {
            query.addCriteria(Criteria.where("Type01").is(type));
        }
        if (!description.equals("")) {
            query.addCriteria(Criteria.where("description").regex(pattern));
        }
        System.out.println(query);
        Long total = mongoTemplate.count(query, NVD.class);
        query.skip((page - 1) * size).limit(size);
        json.put("total", total);
        json.put("result", mongoTemplate.find(query,NVD.class));
        return json.toJSONString();
    }

    //模糊查询漏洞描述信息
    public List<NVD> findByDescription(String description) {
        Query query = new Query();
        Pattern pattern=Pattern.compile(".*?" + description + ".*", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("description").regex(pattern));
        return mongoTemplate.find(query,NVD.class);
    }
}
