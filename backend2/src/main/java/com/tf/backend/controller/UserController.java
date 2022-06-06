package com.tf.backend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tf.backend.model.domain.TFiot;
import com.tf.backend.model.domain.User;
import com.tf.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 用户注册
     * @param params
     * @return
     */
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
            user.setRole("user");
            user.setCompany(company);
            user.setPhone(phone);
            user.setProfession(profession);
            System.out.println("test...");
            userService.add(user);
            System.out.println("test2...");
        } else {
            System.out.println("用户名已存在！");
        }
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    /**
     * 用户登录
     * @param param
     * @return
     */
    @PostMapping("/signin")
    @ResponseBody
    public String signin(@RequestBody JSONObject param) {
        System.out.println("params:" + param);
        String username = param.get("username").toString();
        String password = param.get("password").toString();
        User user = userService.getUserByName(username);
        System.out.println(user);
        JSONObject res = new JSONObject();
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            res.put("code", HttpStatus.OK);
            //返回值中添加 permissions 信息
            List<HashMap<Object,Object>> permissions = new ArrayList<>();
            HashMap<Object, Object> permission = new HashMap<>();
            permission.put("id", "queryForm");
            permission.put("operation", new String[]{"add", "edit"});
            permissions.add(permission);
            //返回值中添加 roles 信息
            List<HashMap<Object,Object>> roles = new ArrayList<>();
            HashMap<Object, Object> role = new HashMap<>();
            if ("admin".equals(user.getRole())) {
                role.put("id", "admin");
                role.put("operation", new String[]{"add","edit","delete"});
            }
            if ("user".equals(user.getRole())) {
                role.put("id", "user");
                role.put("operation", new String[]{"add","edit"});
            }
            roles.add(role);
            res.put("code", 0);
            res.put("message", user.getUsername() + " 欢迎回来!");
            JSONObject data = new JSONObject();
            data.put("permissions", permissions);
            data.put("roles", roles);
            user.setPassword("******");
            data.put("user", user);
            res.put("data", data);
            System.out.println(res.toJSONString());
            return res.toJSONString();
        } else {
            res.put("code", -1);
            res.put("message", "账号或密码错误");
            return res.toJSONString();
        }
    }

    /**
     * 修改密码
     * @param param
     * @return
     */
    @PostMapping("/password")
    @ResponseBody
    public String modifyPass(@RequestBody JSONObject param) {
        String username = param.get("username").toString();
        String oldPass = param.get("oldPass").toString();
        String newPass = param.get("newPass").toString();
        System.out.println(param);
        User user = userService.getUserByName(username);
        if (passwordEncoder.matches(oldPass, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPass));
            userService.update(user);
            return "密码修改成功";
        } else {
            return "密码输入错误";
        }
    }


    /**
     * 邮件订阅
     * @param param
     * @return
     */
    @PostMapping("/subscribe")
    @ResponseBody
    public String subscribe(@RequestBody JSONObject param) {
        return null;
    }

    /**
     * 个人信息修改
     * @param param
     * @return
     */
    @PostMapping("/modify")
    @ResponseBody
    public String modify(@RequestBody JSONObject param) {
        System.out.println(param);
        String username = param.get("username").toString();
        User user = userService.getUserByName(username);
        user.setCompany(param.get("company") == null ? "" : param.get("company").toString());
        user.setPhone(param.get("phone").toString());
        user.setProfession(param.get("profession") == null ? "" : param.get("profession").toString());
        user.setEmail(param.get("email") == null ? "" : param.get("email").toString());
        user.setGender(param.get("gender") == null ? "" : param.get("gender").toString());
        user.setRole(param.get("role").toString());
        Object password = param.get("password");
        if (!password.equals("")) {
            user.setPassword(passwordEncoder.encode(password.toString()));
        }
        System.out.println(user);
        userService.update(user);
        return "修改成功!";
    }

    /**
     * 用户列表分页查询
     * @param size
     * @param page
     * @return
     */
    @GetMapping("list/{size}/{page}")
    public List<User> list(@PathVariable("size") Integer size, @PathVariable("page") Integer page) {
        return userService.getAllUser(size, page);
    }

    /**
     * 查询用户数量
     * @return
     */
    @GetMapping("/get/count")
    public Long getTotal() {
        return userService.getUserNum();
    }

    /**
     * 删除用户
     * @param param
     * @return
     */
    @PostMapping("/delete")
    public String delete(@RequestBody JSONObject param) {
        System.out.println(param);
        String phone = param.get("phone").toString();
        userService.deleteByPhone(phone);
        return "删除成功！";
    }

    @GetMapping("/find")
    public User findByName(String username) {
        System.out.println(username);
        User user = userService.getUserByName(username);
        System.out.println(user);
        return userService.getUserByName(username);
    }

    @GetMapping("/test")
    public String test() {
        return "test...";
    }
}
