package com.zhang.backend2.model.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;
@Data
@Document(collection = "tf_iot")
public class TFiot implements Serializable {

    private static final long serialVersionUID = -3785669996632327769L;

    /* CVE-ID */
    @Field("CVE-ID")
    private String cveID;

    /* CWE-ID */
    @Field("CWE-ID")
    private List<String> cweID;

    /* 相关链接 */
    private List<Reference> refer;

    /* 描述信息 */
    private String description;

    /* CPE */
    private List<List<Cpe>> cpe;

    /* CVSS2 */
    @Field("cvss2")
    private CvssV2 cvssV2;

    /* CVSS3 */
    @Field("cvss3")
    private CvssV3 cvssV3;

    /* 发布时间 */
    private String publishedDate;

    /* 上次修改时间 */
    private String lastModifiedDate;
}
