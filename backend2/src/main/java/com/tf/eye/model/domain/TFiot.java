package com.tf.eye.model.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
@Data
@Document(collection = "iot2")
public class TFiot implements Serializable {

    private static final long serialVersionUID = -3785669996632327769L;

    /* CVE-ID */
    @Field("CVE-ID")
    private String cveID;

    @Field("title")
    private String title;

    /* CWE-ID */
    @Field("CWE")
    private List<String> cweID;

    /* 相关链接 */
    @Field("references")
    private List<Reference> refer;

    /* 描述信息 */
    @Field("description")
    private String description;

    /* CPE */
    @Field("CPE")
    private List<Cpe> cpe;

    /* CVSS2 */
    @Field("baseMetricV2")
    private BaseMetricV2 cvssV2;

    /* CVSS3 */
    @Field("baseMetricV3")
    private BaseMatricV3 cvssV3;

    /* 发布时间 */
    @Field("publishedDate")
    private String publishedDate;

    /* 上次修改时间 */
    @Field("lastModifiedDate")
    private String lastModifiedDate;

    /* 预留类别1 */
    @Field("Type01")
    private List<String> type01;

    /* 预留类别2 */
    @Field("Type02")
    private String type02;

    /* 预留类别3 */
    @Field("Type03")
    private String type03;

    /* 预留类别4 */
    @Field("Type04")
    private String type04;

    /* POC链接 */
    @Field("POC")
    private String poc;
}
