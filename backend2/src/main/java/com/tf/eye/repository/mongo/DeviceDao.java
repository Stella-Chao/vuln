package com.tf.eye.repository.mongo;

import com.alibaba.fastjson.JSONObject;
import com.tf.eye.model.domain.Device;
import com.tf.eye.model.domain.TFiot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

@Repository
public class DeviceDao {
    @Autowired
    MongoTemplate mongoTemplate;

    //给集合中添加一条文档
    public void save(Device device) {
        mongoTemplate.save(device);
    }

    public List<Device> getAllDevice() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        return mongoTemplate.find(query, Device.class);
    }

    //查询集合里的所有文档 + 分页
    public List<Device> getAllDevice(Integer size, Integer page) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));

//        System.out.println("查询总数: " + total);
        query.skip((page - 1) * size).limit(size);
        return mongoTemplate.find(query, Device.class);
    }

    //统计指定厂商数量
    public Long getBrandNumByName(String brand) {
        Query query = new Query();
        query.addCriteria(Criteria.where("brand").is(brand));
        return mongoTemplate.count(query, TFiot.class);
    }

    //统计指定设备数量
    public Long getDeviceNumByName(String product) {
        Query query = new Query();
        query.addCriteria(Criteria.where("product").is(product));
        return mongoTemplate.count(query, TFiot.class);
    }

    //通过设备名查询
    public Device getDeviceByProduct(String product) {
        Query query = new Query();
        query.addCriteria(Criteria.where("product").is(product));
        return mongoTemplate.findOne(query, Device.class);
    }

    //通过厂商名查询
    public List<Device> getDeviceByBrand(String brand) {
        Query query = new Query();
        query.addCriteria(Criteria.where("brand").is(brand));
        return mongoTemplate.find(query, Device.class);
    }

    //统计设备总数量
    public Long getDeviceNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        Long total = mongoTemplate.count(query,Device.class);
        System.out.println("漏洞总数：" + total);
        return total;
    }



    //更新设备信息
    public Device update(Device device) {
        Query query = new Query();
        query.addCriteria(Criteria.where("product").is(device.getProduct()));
        Update update = new Update();
        update.set("product", device.getProduct());
        update.set("brand", device.getBrand());
        update.set("type1", device.getType1());
        update.set("type2", device.getType2());
        update.set("verify", device.getVerify());
        update.set("vulns", device.getVulns());
        update.set("iot_brand_rel", device.getIotBrandRel());
        update.set("version", device.getVersion());
        update.set("range", device.getRange());
        update.set("brand_cn", device.getBrandCN());
        update.set("brand_alias", device.getBrandAlias());
        System.out.println(device);
        return mongoTemplate.findAndModify(query, update, Device.class);
    }

    /**
     * 通过设备名删除设备
     * @param product
     */
    public void deleteByProduct(String product) {
        Query query = new Query();
        query.addCriteria(Criteria.where("product").is(product));
        mongoTemplate.findAndRemove(query, Device.class);
    }

    //多条件查询
    public String findByMulti(String brand, String product, Integer size, Integer page) {
        Query query = new Query();
        JSONObject json = new JSONObject();
        System.out.println(size + " " + page);
        if (!brand.equals("")) {
            query.addCriteria(Criteria.where("brand").is(brand));
        }
        if (!product.equals("")) {
            query.addCriteria(Criteria.where("product").is(product));
        }
        System.out.println(query);
        Long total = mongoTemplate.count(query, Device.class);
        System.out.println(total);
        query.skip((page - 1) * size).limit(size);
        json.put("total", total);
        json.put("result", mongoTemplate.find(query,Device.class));
        return json.toJSONString();
    }
}


