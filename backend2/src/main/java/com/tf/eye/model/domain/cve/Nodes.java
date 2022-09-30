package com.tf.eye.model.domain.cve;

import com.tf.eye.model.domain.Cpe;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zh
 * @date 2022/8/25 16:58
 */
@Data
public class Nodes implements Serializable {

    private static final long serialVersionUID = 546337040962599398L;

    String operator;

    List<Object> children;

    List<Cpe> cpeMatch;
}
