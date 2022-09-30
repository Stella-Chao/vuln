package com.tf.eye.model.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zh
 * @date 2022/8/25 16:14
 */
@Data
public class CVE implements Serializable {

    private static final long serialVersionUID = 2092988041157909574L;

    String data_type;

    String data_format;

    String data_version;




}
