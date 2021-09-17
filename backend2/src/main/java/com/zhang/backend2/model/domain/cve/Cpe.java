package com.zhang.backend2.model.domain.cve;

import java.util.List;

public class Cpe {

    /* 脆弱 */
    private boolean vulnerable;

    /* cpe 标志 */
    private String cpe23Uri;

    /* 受影响版本开始 */
    private String versionStartIncluding;

    /* 受影响版本结束 */
    private String versionEndExcluding;

    /* cpe name */
    private List<Object> cpe_name;
}
