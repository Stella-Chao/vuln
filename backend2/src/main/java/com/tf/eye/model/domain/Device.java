package com.tf.eye.model.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Data
@Document(collection = "device")
public class Device implements Serializable {
    private static final long serialVersionUID = -3785667696632327769L;

    /* 厂商名 */
    @Field("brand")
    private String brand;

    /* 设备名 */
    @Field("product")
    private String product;

    /* 设备类型1 */
    @Field("type1")
    private String type1;

    /* 设备类型2 */
    @Field("type2")
    private String type2;

    /* 是否已确认 */
    @Field("verify")
    private String verify;

    /* 相关漏洞信息 */
    @Field("vulns")
    private String vulns;

    /* 厂商-IoT关联度 */
    @Field("iot_brand_rel")
    private String iotBrandRel;

    /* 版本 */
    @Field("version")
    private String version;

    /* 设备使用范围 */
    @Field("range")
    private String range;

    /* 厂商中文名 */
    @Field("brand_cn")
    private String brandCN;

    /* brand_alias */
    @Field("厂商别名")
    private String brandAlias;

}
