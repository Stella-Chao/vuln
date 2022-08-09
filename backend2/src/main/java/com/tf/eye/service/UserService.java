package com.tf.eye.service;

import com.tf.eye.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhanghe
 * @date 2021/12/12 16:22
 */
@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    public User getUserByName(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, User.class);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    public User add(User user) {
//        SimpleDateFormat sf = new SimpleDateFormat();
//        sf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        user.setCreatedDate(date);
        return mongoTemplate.save(user);
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    public User update(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(user.getUsername()));
        Update update = new Update();
        update.set("username", user.getUsername());
        update.set("password", user.getPassword());
        update.set("gender", user.getGender());
        update.set("email", user.getEmail());
        update.set("phone", user.getPhone());
        update.set("company", user.getCompany());
        update.set("profession", user.getProfession());
        update.set("role", user.getRole());
        return mongoTemplate.findAndModify(query, update, User.class);
    }

    /**
     * 通过用户名删除用户
     * @param username
     */
    public void deleteByName(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        mongoTemplate.findAndRemove(query, User.class);
    }

    /**
     * 通过手机号删除用户
     * @param phone
     */
    public void deleteByPhone(String phone) {
        Query query = new Query();
        System.out.println("正在删除用户：" + phone);
        query.addCriteria(Criteria.where("phone").is(phone));
        mongoTemplate.findAndRemove(query, User.class);
    }

    /**
     * 统计用户数量
     * @return
     */
    public Long getUserNum() {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        Long total = mongoTemplate.count(query, User.class);
        System.out.println("用户总数：" + total);
        return total;
    }

    /**
     * 查询所有用户
     * @param size
     * @param page
     * @return
     */
    public List<User> getAllUser(Integer size, Integer page) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").exists(true));
        query.skip((page - 1) * size).limit(size);
        return mongoTemplate.find(query, User.class);
    }
}
