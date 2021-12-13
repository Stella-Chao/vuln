package com.tf.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.tf.backend.dao.mongo.TFiotDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("dashboard")
public class DashBoardController {

    @Autowired
    TFiotDao iotDao;

    @GetMapping("/data01")
    /* 返回大屏顶部显示的数据 */
    public String getScreenData01() {
        JSONObject dashboard = new JSONObject();
        dashboard.put("漏洞总数量",iotDao.getVulnNum());
        dashboard.put("超危漏洞",iotDao.getCriticalNum());
        dashboard.put("高危漏洞",iotDao.getHighNum());
        dashboard.put("中危漏洞",iotDao.getMediumNum());
        dashboard.put("低危漏洞",iotDao.getLowNum());
        dashboard.put("POC数量",iotDao.getPocNum());
        dashboard.put("当日新增",iotDao.getTodayNum());
        dashboard.put("近一周新增数量",iotDao.getWeekNum());
        dashboard.put("近一月新增数量",iotDao.getMonthNum());
        dashboard.put("近一年新增数量",iotDao.getYearNum());
        System.out.println(dashboard);
        return dashboard.toJSONString();
    }

    @GetMapping("/data02")
    /* 返回大屏左侧类别数据 */
    public String getScreenData02() {
        JSONObject dashboard = new JSONObject();
        dashboard.put("CVE类别",iotDao.getNumByType01());
        System.out.println(dashboard);
        return dashboard.toJSONString();
    }

    @GetMapping("/data03")
    /* 返回大屏中部的滚动数据 */
    public String getScreenData03() {
        JSONObject dashboard = new JSONObject();
//        dashboard.put("近一周新增数量",iotDao.getWeekVuln());
        dashboard.put("近一月新增数量",iotDao.getMonthVuln());
//        dashboard.put("近一年新增数量",iotDao.getYearVuln());
        return dashboard.toJSONString();
    }

    @GetMapping("/data04")
    /* 返回大屏底部展示的数据 */
    public String getScreenData04() {
        List<JSONObject> ans = new ArrayList<>();
        ans.add(iotDao.getVedioNum());
        ans.add(iotDao.getIcsNum());
        ans.add(iotDao.getMobileNum());
        ans.add(iotDao.getSmartHomeNum());
        ans.add(iotDao.getPlatNum());
        return ans.toString();
    }
}
