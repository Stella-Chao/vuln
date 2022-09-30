package com.tf.eye.model.domain.cve;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @author zh
 * @date 2022/8/25 16:24
 */
@Data
public class ProblemTypeData implements Serializable {

    private static final long serialVersionUID = 7498524006769725094L;

    @Field("description")
    List<Description> description;
}
