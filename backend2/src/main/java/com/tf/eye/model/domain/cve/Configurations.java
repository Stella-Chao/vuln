package com.tf.eye.model.domain.cve;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zh
 * @date 2022/8/25 16:34
 */
@Data
public class Configurations implements Serializable {

    private static final long serialVersionUID = -8548027070313930631L;

    String cve_data_version;

    List<Nodes> nodes;
}
