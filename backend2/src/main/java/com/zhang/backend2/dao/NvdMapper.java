package com.zhang.backend2.dao;

import com.zhang.backend2.model.domain.Nvd;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface NvdMapper {

    List<Nvd> getList();

    Nvd getVulnByName(String name);

    List<Nvd> getVulnsByDescription(String description);
}
