package com.tf.eye.model.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;


@Data
@Document(collection = "subscribe")
public class Subscribe implements Serializable {

    private static final long serialVersionUID = 6804123890247352962L;

    /*订阅邮箱*/
    @Field("email")
    private String email;

    /*订阅类型：0-1-2-3-4*/
    @Field("type")
    private String type;

}
