package com.tf.eye.controller;

import com.alibaba.fastjson.JSONObject;
import com.tf.eye.model.domain.Device;
import com.tf.eye.model.domain.TFiot;
import com.tf.eye.repository.mongo.DeviceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("search")
    public String search(String brand, String product, Integer size, Integer page) {
        return deviceDao.findByMulti(brand, product, size, page);
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
        if (param.get("verify") != null) {
            device.setVerify(param.get("verify").toString());
        }
        if (param.get("type1") != null) {
            device.setType1(param.get("type1").toString());
        }
        if (param.get("type2") != null) {
            device.setType2(param.get("type2").toString());
        }
        if (param.get("vulns") != null) {
            device.setVulns(param.get("vulns").toString());
        }
        if (param.get("iotBrandRel") != null) {
            device.setIotBrandRel(param.get("iotBrandRel").toString());
        }
        if (param.get("range") != null) {
            device.setRange(param.get("range").toString());
        }
        if (param.get("brandCN") != null) {
            device.setBrandCN(param.get("brandCN").toString());
        }
        if (param.get("brandAlias") != null) {
            device.setBrandAlias(param.get("brandAlias").toString());
        }
        deviceDao.update(device);
        return "修改成功!";
    }

}
