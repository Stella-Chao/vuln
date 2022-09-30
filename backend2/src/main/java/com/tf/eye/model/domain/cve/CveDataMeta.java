package com.tf.eye.model.domain.cve;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zh
 * @date 2022/8/25 16:20
 */
@Data
public class CveDataMeta implements Serializable {

    private static final long serialVersionUID = 7759075771081158767L;

    String ID;

    String ASSIGNER;
}
