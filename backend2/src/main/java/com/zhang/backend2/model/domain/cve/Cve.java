package com.zhang.backend2.model.domain.cve;

import java.util.List;

public class Cve {

    /* 数据类型 */
    private String data_type;

    /* 数据格式 */
    private String data_format;

    /* 数据版本 */
    private String data_version;

    /* cve元数据 */
    private CveMeta CVE_data_meta;

    /* 问题类型 */
    private List<ProblemTypeData> problemtype;

    /* 相关链接 */

    /* 描述 */

}
