package com.tf.eye.repository;

import com.tf.eye.model.domain.Nvd;

import java.util.List;

public interface NvdMapper {

    List<Nvd> getList();

    Nvd getVulnByName(String name);

    List<Nvd> getVulnsByDescription(String description);
}
