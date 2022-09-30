package com.tf.eye.model.domain.cve;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author zh
 * @date 2022/8/25 16:57
 */
@Data
public class DescriptionData implements Serializable {

    private static final long serialVersionUID = 247666331084258342L;

    @Field("lang")
    String lang;

    @Field("value")
    String value;
}
