package com.tf.eye.model.domain.cve;

import com.tf.eye.model.domain.Reference;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * @author zh
 * @date 2022/8/25 16:14
 */
@Data
public class CVE implements Serializable {

    private static final long serialVersionUID = 2092988041157909574L;

    @Field("data_type")
    String dataType;

    @Field("data_format")
    String dataFormat;

    @Field("data_version")
    String dataVersion;

    @Field("CVE_data_meta")
    CveDataMeta cveDataMeta;

    @Field("problemtype")
    ProblemType problemType;

    @Field("references")
    Reference references;

    @Field("description")
    Description description;

}
