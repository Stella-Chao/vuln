package com.tf.eye.model.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseMetricV3 implements Serializable {

    private static final long serialVersionUID = 852615252892887230L;

    /* CVSS3*/
    private CvssV3 cvssV3;

    /* 可利用性分数 */
    private Double exploitabilityScore;

    /* 影响性分数 */
    private Double impactScore;
}

