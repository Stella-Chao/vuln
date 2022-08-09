package com.tf.eye.model.domain;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(collection = "submit_iot")
public class SubmitVuln implements Serializable {

    private static final long serialVersionUID = 1055321345539809339L;

    /* 漏洞标题 */
    @Field("title")
    private String title;

    /* 漏洞发现日期 */
    @Field("date")
    private Date date;

    /* 漏洞类型 */
    @Field("type")
    private String type;

    /* 受影响产品(型号) */
    @Field("product")
    private String product;

    /* 受影响产品厂商 */
    @Field("ventor")
    private String ventor;

    /* 详细描述 */
    @Field("description")
    private String description;

    /* 攻击方式 */
    @Field("attacker")
    private String attacker;

    /* 联系方式 */
    @Field("address")
    private String address;

}
