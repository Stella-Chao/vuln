package com.tf.eye.model.domain.cve;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @author zh
 * @date 2022/8/25 16:22
 */
@Data
public class ProblemType implements Serializable {

    private static final long serialVersionUID = -2077685379991875208L;

    @Field("problemtype")
    List<ProblemTypeData> problemTypeData;
}
