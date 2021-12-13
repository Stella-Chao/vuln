package com.tf.backend.controller;

import com.alibaba.fastjson.JSONObject;
import com.tf.backend.model.domain.User;
import com.tf.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhanghe
 * @date 2021/12/12 17:24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    @ResponseBody
    public ResponseEntity<Void> signup(@RequestBody String params) {
        System.out.println(params);
        Map<String, Object> data = JSONObject.parseObject(params);
        System.out.println("注册中...");
        System.out.println(data);
        String username = data.get("username").toString();
        String password = data.get("password").toString();
        String email = "";
        String company = "";
        String profession = "";
        if (data.containsKey("email")) {
            email = data.get("email").toString();
        }
        if (data.containsKey("company")) {
            company = data.get("company").toString();
        }
        if (data.containsKey("profession")) {
            profession = data.get("profession").toString();
        }
        String phone = data.get("phone").toString();
        if (userService.getUserByName(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setRole("User");
            user.setCompany(company);
            user.setPhone(phone);
            user.setProfession(profession);
            userService.add(user);
        } else {
            System.out.println("用户名已存在！");
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    @ResponseBody
    public String signin(@RequestBody JSONObject data) {
        System.out.println("params:" + data);
        String username = data.get("username").toString();
        String password = data.get("password").toString();
        User user = userService.getUserByName(username);
        JSONObject res = new JSONObject();
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            res.put("code", HttpStatus.OK);
            res.put("roles",user.getRole());
            System.out.println(res.toJSONString());
            return res.toJSONString();
        } else {
            res.put("code", HttpStatus.NO_CONTENT);
            return res.toJSONString();
        }
    }

    @GetMapping("/test")
    public String test() {
        return "test...";
    }
}
