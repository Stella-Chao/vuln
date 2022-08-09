package com.tf.eye.model.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseMetricV2 implements Serializable {

    private static final long serialVersionUID = -7394203293996260397L;

    /* CVSS2*/
    private CvssV2 cvssV2;

    /* 等级 */
    private String severity;

    /* 可利用性分数 */
    private Double exploitabilityScore;

    /* 影响分数 */
    private Double impactScore;

    /* *** */
    private boolean acInsufInfo;

    /* 获取所有权限 */
    private boolean obtainAllprivilege;

    /* 获取用户权限 */
    private boolean obtainUserPrivilege;

    /* 获取其他授权 */
    private boolean obtainOtherPrivilege;

    /* 用户交互要求 */
    private boolean userInteractionRequired;
}
