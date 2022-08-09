package com.tf.eye.model.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -1251844816923526548L;

    @Field("username")
    private String username;

    @Field("password")
    private String password;

    @Field("role")
    private String role;

    @Field("gender")
    private String gender;

    @Field("email")
    private String email;

    @Field("phone")
    private String phone;

    @Field("compony")
    private String company;

    @Field("profession")
    private String profession;

    @Field("created_date")
    private Date createdDate;
}
