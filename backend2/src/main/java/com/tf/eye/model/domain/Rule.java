package com.tf.eye.model.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * @author zh
 * @date 2023/3/28 11:01
 */
@Data
@Document(collection = "rule")
public class Rule implements Serializable {
    private static final long serialVersionUID = 9125756423479006771L;

    private Long ruleID;

    private List<String> weaknessProperties;

    private List<String> productProperties;

    private List<String> cvssProperties;

    private List<String> codeProperties;

    private Double threshold;

    private Integer limit;

}
