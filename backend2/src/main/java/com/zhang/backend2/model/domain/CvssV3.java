package com.zhang.backend2.model.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class CvssV3 implements Serializable {

    private static final long serialVersionUID = 3011314590187987940L;

    /* 版本信息 */
    private String version;

    /* Vector */
    private String vectorString;

    /* 攻击途径 */
    private String attackVector;

    /* 攻击复杂度 */
    private String attackComplexity;

    /* 权限要求 */
    private String privilegesRequired;

    /* 用户交互 */
    private String userInteraction;

    /* 范围 */
    private String scope;

    /* 机密性 */
    private String confidentialityImpact;

    /* 完整性 */
    private String integrityImpact;

    /* 可利用性 */
    private String availabilityImpact;

    /* 基础分数 */
    private Double baseScore;

    /* 基础等级 */
    private String baseSeverity;
}
