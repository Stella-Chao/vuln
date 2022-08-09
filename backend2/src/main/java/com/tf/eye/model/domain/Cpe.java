package com.tf.eye.model.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class Cpe implements Serializable {

    private static final long serialVersionUID = 5648164685455600686L;

    /* 脆弱 */
    private boolean vulnerable;

    /* cpe 标志 */
    private String cpe23Uri;

    /* 受影响版本开始 */
    private String versionStartIncluding;

    /* 受影响版本结束 */
    private String versionEndExcluding;

    /* cpe name */
    private List<String> cpe_name;
}
