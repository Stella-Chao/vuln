package com.tf.backend.dao;

import com.tf.backend.model.domain.Nvd;

import java.util.List;

public interface NvdMapper {

    List<Nvd> getList();

    Nvd getVulnByName(String name);

    List<Nvd> getVulnsByDescription(String description);
}
