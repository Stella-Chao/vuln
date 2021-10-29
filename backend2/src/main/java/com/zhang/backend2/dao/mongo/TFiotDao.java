package com.zhang.backend2.dao.mongo;

import com.alibaba.fastjson.JSONObject;
import com.zhang.backend2.model.domain.TFiot;
import com.zhang.backend2.util.dateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
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

    //查询集合里的所有文档
    public List<TFiot> getAllVuln() {
        return mongoTemplate.findAll(TFiot.class);
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
        return mongoTemplate.count(query,TFiot.class);
    }

    /* 先只按 cvssV2 来统计*/

    //统计超危漏洞数量
    public Long getCriticalNum() {
        return null;
    }
    //统计高危漏洞数量
    public Long getHighNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("baseMetricV2.severity").is("高危"));
        return mongoTemplate.count(query,TFiot.class);
    }
    //统计中危漏洞数量
    public Long getMediumNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("baseMetricV2.severity").is("中危"));
        return mongoTemplate.count(query,TFiot.class);
    }
    //统计低危漏洞数量
    public Long getLowNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("baseMetricV2.severity").is("低危"));
        return mongoTemplate.count(query,TFiot.class);
    }
    //统计高危漏洞
    public List<TFiot> getHighVuln() {
        Query query = new Query();
        query.addCriteria(Criteria.where("baseMetricV2.severity").is("高危"));
        return mongoTemplate.find(query,TFiot.class);
    }
    //统计中危漏洞
    public List<TFiot> getMediumVuln() {
        Query query = new Query();
        query.addCriteria(Criteria.where("baseMetricV2.severity").is("中危"));
        return mongoTemplate.find(query,TFiot.class);
    }
    //统计低危漏洞
    public List<TFiot> getLowVuln() {
        Query query = new Query();
        query.addCriteria(Criteria.where("baseMetricV2.severity").is("低危"));
        return mongoTemplate.find(query,TFiot.class);
    }

    //今日新增数量
    public Long getTodayNum() {
        return 0L;
    }
    //近一周新增数量
    public Long getWeekNum() {
        String day = dateUtil.getDate("Week");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.count(query,TFiot.class);
    }
    //近一月新增数量
    public Long getMonthNum() {
        String day = dateUtil.getDate("Month");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.count(query,TFiot.class);
    }
    //近一年新增数量
    public Long getYearNum() {
        String day = dateUtil.getDate("Year");
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
        String day = dateUtil.getDate("Week");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.find(query,TFiot.class);
    }
    //近一月新增漏洞
    public List<TFiot> getMonthVuln() {
        String day = dateUtil.getDate("Month");
        Query query = new Query();
        query.addCriteria(Criteria.where("publishedDate").gt(day));
        return mongoTemplate.find(query,TFiot.class);
    }
    //近一年新增漏洞
    public List<TFiot> getYearVuln() {
        String day = dateUtil.getDate("Year");
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
    public List<TFiot> findByMulti(String severity, String description) {
        Query query = new Query();
        Pattern pattern=Pattern.compile(".*?" + description + ".*", Pattern.CASE_INSENSITIVE);
        System.out.println(pattern);
        query.addCriteria(Criteria.where("baseMetricV2.severity").is(severity));
        query.addCriteria(Criteria.where("description").regex(pattern));
        return mongoTemplate.find(query,TFiot.class);
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
        Query query1 = new Query();
        query1.addCriteria(Criteria.where("Type02").is("vedio"));
        json.put("总数",mongoTemplate.count(query1,TFiot.class));
        Query query2 = new Query();
        query2.addCriteria(Criteria.where("Type02").is("vedio").and("baseMetricV2.severity").is("高危"));
        json.put("高危",mongoTemplate.count(query2,TFiot.class));
        return json;
    }

    //获取智能家居类漏洞数量
    public JSONObject getSmartHomeNum() {
        JSONObject json = new JSONObject();
        json.put("type","智能家居类");
        json.put("总数",0);
        json.put("高危",0);
//        Query query1 = new Query();
//        query1.addCriteria(Criteria.where("Type02").is("smart"));
//        json.put("总数",mongoTemplate.count(query1,TFiot.class));
//        Query query2 = new Query();
//        query2.addCriteria(Criteria.where("Type02").is("smart").and("baseMetricV2.severity").is("HIGH"));
//        json.put("高危",mongoTemplate.count(query2,TFiot.class));
        return json;
    }
    //获取工业控制类漏洞数量
    public JSONObject getIcsNum() {
        JSONObject json = new JSONObject();
        json.put("type","工业控制类");
        json.put("总数",0);
        json.put("高危",0);
//        Query query1 = new Query();
//        query1.addCriteria(Criteria.where("Type02").is("ics"));
//        json.put("总数",mongoTemplate.count(query1,TFiot.class));
//        Query query2 = new Query();
//        query2.addCriteria(Criteria.where("Type02").is("ics").and("baseMetricV2.severity").is("HIGH"));
//        json.put("高危",mongoTemplate.count(query2,TFiot.class));
        return json;
    }

    //获取移动设备类漏洞数量
    public JSONObject getMobileNum() {
        JSONObject json = new JSONObject();
        json.put("type","移动设备类");
        json.put("总数",0);
        json.put("高危",0);
//        Query query1 = new Query();
//        query1.addCriteria(Criteria.where("Type02").is("mobile"));
//        json.put("总数",mongoTemplate.count(query1,TFiot.class));
//        Query query2 = new Query();
//        query2.addCriteria(Criteria.where("Type02").is("mobile").and("baseMetricV2.severity").is("HIGH"));
//        json.put("高危",mongoTemplate.count(query2,TFiot.class));
        return json;
    }

    //获取通用同源跨平台类漏洞数量
    public JSONObject getPlatNum() {
        JSONObject json = new JSONObject();
        json.put("type","待定类别");
        json.put("总数",0);
        json.put("高危",0);
//        Query query1 = new Query();
//        query1.addCriteria(Criteria.where("Type02").is("plat"));
//        json.put("总数",mongoTemplate.count(query1,TFiot.class));
//        Query query2 = new Query();
//        query2.addCriteria(Criteria.where("Type02").is("plat").and("baseMetricV2.severity").is("HIGH"));
//        json.put("高危",mongoTemplate.count(query2,TFiot.class));
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
            for (String type : item.getType01()) {
                if (type.equals("拒绝服务")) {
                    doss ++ ;
                } else if (type.equals("执行代码")) {
                    execute ++;
                } else if (type.equals("溢出")) {
                    overflow ++;
                } else if (type.equals("跨站脚本")) {
                    xss ++;
                } else if (type.equals("目录遍历")) {
                    directory ++;
                } else if (type.equals("绕过")) {
                    bypass ++;
                } else if (type.equals("获取信息")) {
                    gain_infor ++;
                } else if (type.equals("获取权限")) {
                    gain_privilege ++;
                } else if (type.equals("SQL注入")) {
                    sql ++;
                } else if (type.equals("文件包含")) {
                    file_inclusion ++;
                } else if (type.equals("内存错误")) {
                    memory ++;
                } else if (type.equals("跨站请求伪造")) {
                    csrf ++;
                } else if (type.equals("HTTP响应拆分")) {
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

}
