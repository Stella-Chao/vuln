package com.tf.backend.service;

import com.tf.backend.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhanghe
 * @date 2021/12/12 16:22
 */
@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public User getUserByName(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return mongoTemplate.findOne(query, User.class);
    }

    public User add(User user) {
//        SimpleDateFormat sf = new SimpleDateFormat();
//        sf.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        user.setCreatedDate(date);
        return mongoTemplate.save(user);
    }

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
        return mongoTemplate.findAndModify(query, update, User.class);
    }

    public void delete(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        mongoTemplate.findAndRemove(query, User.class);
    }
}
