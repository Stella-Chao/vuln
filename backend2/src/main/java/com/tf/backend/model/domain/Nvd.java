package com.tf.backend.model.domain;

import lombok.Data;

@Data
public class Nvd {
    Integer id;
    String name;
    String url;
    String description;
    String cvss2Score;
    String cvss3Score;
    String cvss2Vector;
    String cvss3Vector;
    String refer;
    String cwe_name;
    String cwe_id;
    String cpe;
    String techLevel;
    String compLevel;
}
