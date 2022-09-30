package com.tf.eye.model.domain.cve;

import com.tf.eye.model.domain.BaseMetricV3;
import com.tf.eye.model.domain.BaseMetricV2;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author zh
 * @date 2022/8/25 16:28
 */
@Data
public class Impact implements Serializable {

    private static final long serialVersionUID = 4442831517867831643L;

    @Field("baseMetricV3")
    BaseMetricV3 baseMetricV3;

    @Field("baseMetricV2")
    BaseMetricV2 baseMetricV2;
}
