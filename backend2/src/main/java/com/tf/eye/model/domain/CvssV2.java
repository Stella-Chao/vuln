package com.tf.eye.model.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class CvssV2 implements Serializable {

    private static final long serialVersionUID = 5615972665822064452L;

    /* 版本信息 */
    private String version;

    /* Vector */
    private String vectorString;

    /* 攻击途径 */
    private String accessVector;

    /* 攻击复杂度 */
    private String accessComplexity;

    /* 权限要求 */
    private String authentication;

    /* 机密性 */
    private String confidentialityImpact;

    /* 完整性 */
    private String integrityImpact;

    /* 可利用性 */
    private String availabilityImpact;

    /* 基础分数 */
    private Double baseScore;
}
