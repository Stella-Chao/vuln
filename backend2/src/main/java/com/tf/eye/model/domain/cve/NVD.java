package com.tf.eye.model.domain.cve;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @author zh
 * @date 2022/8/25 16:11
 */
@Data
@Document(collection = "nvd01")
public class NVD implements Serializable {

    private static final long serialVersionUID = -1157207322902031916L;

    @Field("cve")
    CVE cve;

    @Field("configurations")
    Configurations configurations;

    @Field("impact")
    Impact impact;

    @Field("publishedDate")
    String publishedDate;

    @Field("lastModifiedDate")
    String lastModifiedDate;
}
