package com.tf.eye.model.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author zh
 * @date 2023/3/28 11:01
 */
@Data
@Document(collection = "alert")
public class Alert implements Serializable {
    private static final long serialVersionUID = 9125756463479023471L;

    private Long alertID;

    private String cveID;

    private String weakness;

    private String severity;

    private List<String> product;

    private List<TFiot> similarVuln;

    private Long ruleID;


    private Date alertDate;

}
