package com.tf.backend.model.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Document(collection = "exploit")
public class POC implements Serializable {

    private static final long serialVersionUID = -8838026546464781218L;

    @Field("title")
    private String title;

    @Field("edb_id")
    private String edbId;

    @Field("cve")
    private String cveId;

    @Field("verified")
    private String verified;

    @Field("author")
    private String author;

    @Field("type")
    private String type;

    @Field("code_url")
    private String codeUrl;

    @Field("plat")
    private String plat;

    @Field("date")
    private String date;

    @Field("code")
    private String code;
}
