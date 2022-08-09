package com.tf.eye.controller;

import com.alibaba.fastjson.JSONObject;
import com.tf.eye.model.domain.Device;
import com.tf.eye.repository.mongo.DeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("device")
public class DeviceController {

    @Autowired
    DeviceDao deviceDao;

    @GetMapping("list/{size}/{page}")
    public List<Device> list(@PathVariable("size") Integer size, @PathVariable("page") Integer page) {
        return deviceDao.getAllDevice(size, page);
    }

    @GetMapping("get/count")
    public Long getTotal() {
        return deviceDao.getDeviceNum();
    }


    /**
     * 删除设备
     * @param param
     * @return
     */
    @PostMapping("/delete")
    public String delete(@RequestBody JSONObject param) {
        System.out.println(param);
        String product = param.get("product").toString();
        deviceDao.deleteByProduct(product);
        return "删除成功！";
    }

    /**
     * 设备信息修改
     * @param param
     * @return
     */
    @PostMapping("/modify")
    @ResponseBody
    public String modify(@RequestBody JSONObject param) {
        System.out.println(param);
        String product = param.get("product").toString();
        Device device = deviceDao.getDeviceByProduct(product);
        device.setBrand(param.get("brand").toString());
        device.setVerify(param.get("verify").toString());
        device.setType1(param.get("type1").toString());
        device.setType2(param.get("type2").toString());
        device.setVulns(param.get("vulns").toString());
        device.setIotBrandRel(param.get("iot_brand_rel").toString());
        device.setRange(param.get("range").toString());
        device.setRange(param.get("range").toString());
        device.setBrandCN(param.get("brand_cn").toString());
        device.setBrandAlias(param.get("brand_alias").toString());
        deviceDao.update(device);
        return "修改成功!";
    }

}
