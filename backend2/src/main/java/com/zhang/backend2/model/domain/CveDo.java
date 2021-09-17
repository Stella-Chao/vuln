package com.zhang.backend2.model.domain;

import com.zhang.backend2.model.domain.cve.Configurations;
import com.zhang.backend2.model.domain.cve.Cve;
import com.zhang.backend2.model.domain.cve.Impact;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class CveDo {
    @Id
    private Long id;

    /* CVE信息 */
    private Cve cve;

    /* 结构信息 */
    private Configurations configurations;

    /* 影响信息 */
    private Impact impact;

    /* 发布时间 */
    private Date publishedDate;

    /* 上次修改时间 */
    private Date lastModifiedDate;
}
