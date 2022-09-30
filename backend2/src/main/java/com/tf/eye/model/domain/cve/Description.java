package com.tf.eye.model.domain.cve;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zh
 * @date 2022/8/25 16:56
 */
@Data
public class Description implements Serializable {
    private static final long serialVersionUID = 6483590999249124454L;

    List<DescriptionData> descriptionData;
}
