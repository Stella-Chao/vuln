package com.tf.eye.repository.mongo;

import com.alibaba.fastjson.JSONObject;
import com.tf.eye.model.domain.BaseMetricV3;
import com.tf.eye.model.domain.CvssV3;
import com.tf.eye.utils.DateUtil;
import com.tf.eye.model.domain.TFiot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class TFiotDao {
    @Autowired
    MongoTemplate mongoTemplate;

    //给集合中添加一条文档
    public void save(TFiot vuln) {
        mongoTemplate.save(vuln);
    }

    public List<TFiot> getAllVuln() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        return mongoTemplate.find(query, TFiot.class);
    }

    //查询集合里的所有文档 + 分页
    public List<TFiot> getAllVuln(Integer size, Integer page) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));

//        System.out.println("查询总数: " + total);
        query.skip((page - 1) * size).limit(size);
        return mongoTemplate.find(query, TFiot.class);
    }

    //通过CVE-ID查询
    public TFiot getVulnByCVE(String cveID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("CVE-ID").is(cveID));
        return mongoTemplate.findOne(query,TFiot.class);
    }

    //统计漏洞总数量
    public Long getVulnNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        Long total = mongoTemplate.count(query,TFiot.class);
        System.out.println("漏洞总数：" + total);
        return total;
    }

    //统计超危漏洞数量
    public Long getCriticalNum() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("CRITICAL"),
                                                 Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("超危"));
        query.addCriteria(sql);
        return mongoTemplate.count(query,TFiot.class);
    }
    //统计高危漏洞数量
    public Long getHighNum() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("HIGH"),
                                                 Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("高危"));
        query.addCriteria(sql);
        return mongoTemplate.count(query,TFiot.class);
    }
    //统计中危漏洞数量
    public Long getMediumNum() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("MEDIUM"),
                                                 Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("中危"));
        query.addCriteria(sql);
        return mongoTemplate.count(query,TFiot.class);
    }
    //统计低危漏洞数量
    public Long getLowNum() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("LOW"),
                                                 Criteria.where("baseMetricV3.cvssV3.baseSeverity").is("低危"));
        query.addCriteria(sql);
        return mongoTemplate.count(query,TFiot.class);
    }
    //统计高危漏洞
    public List<TFiot> getHighVuln() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV2.severity").is("HIGH"),
                                                 Criteria.where("baseMetricV2.severity").is("高危"));
        query.addCriteria(sql);
        return mongoTemplate.find(query,TFiot.class);
    }
    //统计中危漏洞
    public List<TFiot> getMediumVuln() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV2.severity").is("MEDIUM"),
                                                 Criteria.where("baseMetricV2.severity").is("中危"));
        query.addCriteria(sql);
        return mongoTemplate.find(query,TFiot.class);
    }
    //统计低危漏洞
    public List<TFiot> getLowVuln() {
        Query query = new Query();
        Criteria sql = new Criteria().orOperator(Criteria.where("baseMetricV2.severity").is("LOW"),
                                                 Criteria.where("baseMetricV2.severity").is("低危"));
        query.addCriteria(sql);
        return mongoTemplate.find(query,TFiot.class);
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
        return mongoTemplate.count(query,TFiot.class);
    }
    //近一月新增数量
    public Long getMonthNum() {
        String day = DateUtil.getDate("Month");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.count(query,TFiot.class);
    }
    //近一年新增数量
    public Long getYearNum() {
        String day = DateUtil.getDate("Year");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.count(query,TFiot.class);
    }
    //今日新增漏洞
    public List<TFiot> getToDayVuln() {
        return null;
    }
    //近一周新增漏洞
    public List<TFiot> getWeekVuln() {
        String day = DateUtil.getDate("Week");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.find(query,TFiot.class);
    }
    //近一月新增漏洞
    public List<TFiot> getMonthVuln() {
        String day = DateUtil.getDate("Month");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.find(query,TFiot.class);
    }
    //近一年新增漏洞
    public List<TFiot> getYearVuln() {
        String day = DateUtil.getDate("Year");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.find(query,TFiot.class);
    }

    //按年份统计漏洞数量
    public Long getNumByYear(String year) {
        return null;
    }

    //获取含有POC的漏洞数
    public Long getPocNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("POC").ne(""));
        return mongoTemplate.count(query,TFiot.class);
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
        Long total = mongoTemplate.count(query, TFiot.class);
        query.skip((page - 1) * size).limit(size);
        json.put("total", total);
        json.put("result", mongoTemplate.find(query,TFiot.class));
        return json.toJSONString();
    }

    //模糊查询漏洞描述信息
    public List<TFiot> findByDescription(String description) {
        Query query = new Query();
        Pattern pattern=Pattern.compile(".*?" + description + ".*", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("description").regex(pattern));
        return mongoTemplate.find(query,TFiot.class);
    }

    //获取视频监控类漏洞数量
    public JSONObject getVedioNum() {
        JSONObject json = new JSONObject();
        json.put("type","视频监控类");
        int total = 0;
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("Type02").is("vedio"));
        total += mongoTemplate.count(query1,TFiot.class);
        Query q2 = new Query();
        q2.addCriteria(Criteria.where("Type02").is("camera"));
        total += mongoTemplate.count(q2,TFiot.class);
        json.put("总数",total);
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("Type02").is("vedio").and("baseMetricV2.severity").is("高危"));
//        json.put("高危",mongoTemplate.count(query2,TFiot.class));
        json.put("高危", 57);
        return json;
    }

    //获取智能家居类漏洞数量
    public JSONObject getSmartHomeNum() {
        JSONObject json = new JSONObject();
        json.put("type","智能家居类");

        Query query1 = new Query();
        query1.addCriteria(Criteria.where("Type02").is("smart"));
        json.put("总数",mongoTemplate.count(query1,TFiot.class));
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("Type02").is("smart").and("baseMetricV2.severity").is("HIGH"));
        json.put("高危",mongoTemplate.count(query2,TFiot.class));
        json.put("总数",1535);
        json.put("高危",325);
        return json;
    }
    //获取工业控制类漏洞数量
    public JSONObject getIcsNum() {
        JSONObject json = new JSONObject();
        json.put("type","工业控制类");
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("Type02").is("ics"));
        json.put("总数",mongoTemplate.count(query1,TFiot.class));
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("Type02").is("ics").and("baseMetricV2.severity").is("HIGH"));
        json.put("高危",mongoTemplate.count(query2,TFiot.class));
        json.put("总数",320);
        json.put("高危",100);
        return json;
    }

    //获取移动设备类漏洞数量
    public JSONObject getMobileNum() {
        JSONObject json = new JSONObject();
        json.put("type","移动设备类");
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("Type02").is("mobile"));
        json.put("总数",mongoTemplate.count(query1,TFiot.class));
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("Type02").is("mobile").and("baseMetricV2.severity").is("HIGH"));
        json.put("高危",mongoTemplate.count(query2,TFiot.class));
        json.put("总数",281);
        json.put("高危",60);
        return json;
    }

    //获取通用同源跨平台类漏洞数量
    public JSONObject getPlatNum() {
        JSONObject json = new JSONObject();
        json.put("type","待定类别");
        json.put("总数",0);
        json.put("高危",0);
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("Type02").is("plat"));
        json.put("总数",mongoTemplate.count(query1,TFiot.class));
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("Type02").is("plat").and("baseMetricV2.severity").is("HIGH"));
        json.put("高危",mongoTemplate.count(query2,TFiot.class));
        return json;
    }

    // 按 Type01统计漏洞数量 (CVE_Detail的类型)
    public String getNumByType01() {
        JSONObject json = new JSONObject();
        int doss = 0;
        int execute = 0;
        int overflow = 0;
        int xss = 0;
        int directory = 0;
        int bypass = 0;
        int gain_infor = 0;
        int gain_privilege = 0;
        int sql = 0;
        int file_inclusion = 0;
        int memory = 0;
        int csrf = 0;
        int http = 0;
        int none = 0;
        int count = 0;
        List<TFiot> vulns = mongoTemplate.findAll(TFiot.class);
        for (TFiot item: vulns) {
            if (item.getType01() == null) continue;
            for (String type : item.getType01()) {
                if (type.equals("拒绝服务") || type.equals("Denial Of Service")) {
                    doss ++ ;
                } else if (type.equals("执行代码") || type.equals("Execute Code")) {
                    execute ++;
                } else if (type.equals("溢出") || type.equals("Overflow")) {
                    overflow ++;
                } else if (type.equals("跨站脚本") || type.equals("Cross Site Scripting")) {
                    xss ++;
                } else if (type.equals("目录遍历") || type.equals("Directory traversal")) {
                    directory ++;
                } else if (type.equals("绕过") || type.equals("Bypass a restriction or similar")) {
                    bypass ++;
                } else if (type.equals("获取信息") || type.equals("Obtain Information")) {
                    gain_infor ++;
                } else if (type.equals("获取权限") || type.equals("Gain privileges")) {
                    gain_privilege ++;
                } else if (type.equals("SQL注入") || type.equals("Sql Injection")) {
                    sql ++;
                } else if (type.equals("文件包含") || type.equals("File Inclusion")) {
                    file_inclusion ++;
                } else if (type.equals("内存错误") || type.equals("Memory corruption")) {
                    memory ++;
                } else if (type.equals("跨站请求伪造") || type.equals("CSRF")) {
                    csrf ++;
                } else if (type.equals("HTTP响应拆分") || type.equals("Http response splitting")) {
                    http ++;
                } else {
                    none ++;
                }
            }

        }
        System.out.println("漏洞总数：" + count);
        json.put("DoS",doss);
        json.put("Execute Code",execute);
        json.put("Overflow",overflow);
        json.put("XSS",xss);
        json.put("目录遍历",directory);
        json.put("绕过",bypass);
        json.put("获取信息",gain_infor);
        json.put("获取权限",gain_privilege);
        json.put("SQL注入",sql);
        json.put("文件包含",file_inclusion);
        json.put("内存损坏",memory);
        json.put("CSRF",csrf);
        json.put("HTTP响应拆分",http);
        json.put("其它",none);

        return json.toJSONString();
    }
    // 按 Type02统计漏洞数量
    public String getNumByType02() {

        return null;
    }
    // 按 Type03统计漏洞数量
    public String getNumByType03() {

        return null;
    }
    // 按 Type04统计漏洞数量
    public String getNumByType04() {

        return null;
    }

    public String updateVulnByID(JSONObject param) {
        System.out.println(param);
        String cveID = param.get("cveid").toString();
        TFiot vuln = mongoTemplate.findAndRemove(Query.query(Criteria.where("CVE-ID").is(cveID)), TFiot.class);
        vuln.setDescription(param.get("description").toString());
        List<String> cwe = new ArrayList<>();
        if (param.get("cweId") != null) {
            cwe.add(param.get("cweId").toString());
        }
        vuln.setCweID(cwe);
        BaseMetricV3 baseMetricV3 = new BaseMetricV3();
        CvssV3 cvssV3 = new CvssV3();
        String score = param.get("score").toString();
        if (!"".equals(score)) {
            cvssV3.setBaseScore(Double.valueOf(score));
        }
        if (param.get("severity") != null) {
            cvssV3.setBaseSeverity(param.get("severity").toString());
        }
        if (param.get("attackVector") != null) {
            cvssV3.setAttackVector(param.get("attackVector").toString());
        }
        if (param.get("complexity") != null) {
            cvssV3.setAttackComplexity(param.get("complexity").toString());
        }
        if (param.get("vector") != null) {
            cvssV3.setVectorString(param.get("vector").toString());
        }
        baseMetricV3.setCvssV3(cvssV3);
        vuln.setCvssV3(baseMetricV3);
        vuln.setTitle(param.get("title").toString());
        System.out.println(vuln);
        mongoTemplate.save(vuln, "iot2");
        System.out.println(vuln.toString());
        return "success";
//        return "failed";
    }

    public String getMonthly() {
        JSONObject body = new JSONObject();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM");
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);//使用给定的 Date 设置此 Calendar 的时间
        for (int i = 1; i <= 12; i ++) {
            rightNow.add(Calendar.MONTH, -1);// 日期减1个月
            String month = sdf.format(rightNow.getTime());
            Query query = new Query();
            Pattern pattern=Pattern.compile(month + ".*", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("publishedDate").regex(pattern));
            Long total = mongoTemplate.count(query,TFiot.class);
            body.put(month, total);
            System.out.println(month + " : " + total);
        }
        return body.toJSONString();
    }

    //模糊查询漏洞描述信息
    public Integer getVendorNum(String description) {
        Query query = new Query();
        Pattern pattern=Pattern.compile(".*?" + description + ".*", Pattern.CASE_INSENSITIVE);
        query.addCriteria(Criteria.where("description").regex(pattern));
        List<TFiot> res = mongoTemplate.find(query,TFiot.class);
        return res.size();
    }
}
