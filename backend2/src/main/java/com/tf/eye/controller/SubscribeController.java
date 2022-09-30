package com.tf.eye.controller;

import com.alibaba.fastjson.JSONObject;
import com.tf.eye.model.domain.Device;
import com.tf.eye.model.domain.SubmitVuln;
import com.tf.eye.model.domain.Subscribe;
import com.tf.eye.repository.mongo.SubscribeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("subscribe")
public class SubscribeController {

    @Autowired
    SubscribeDao subscribeDao;


    @PostMapping("submit")
    public String submitVuln(@RequestBody JSONObject param) {
        System.out.println(param);
        Subscribe sub = new Subscribe();
        String email = (String)param.get("email");
        String type = String.valueOf(param.get("type"));
        sub.setEmail(email);
        sub.setType(type);
        try {
            subscribeDao.save(sub);
            return "success";
        } catch (Exception e) {}
        return "failed";
    }

}
