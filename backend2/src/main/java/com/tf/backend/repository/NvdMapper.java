package com.tf.backend.repository;

import com.tf.backend.model.domain.Nvd;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface NvdMapper {

    List<Nvd> getList();

    Nvd getVulnByName(String name);

    List<Nvd> getVulnsByDescription(String description);
}
