package com.zhang.backend2.service.impl;

import com.zhang.backend2.dao.NvdMapper;
import com.zhang.backend2.model.domain.Nvd;
import com.zhang.backend2.service.NvdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NvdServiceImpl implements NvdService {
    @Autowired
    NvdMapper nvdMapper;

    @Override
    public List<Nvd> getList() {
        return nvdMapper.getList();
    }

    @Override
    public Nvd queryName(String name) {
        return nvdMapper.getVulnByName(name);
    }

    @Override
    public List<Nvd> queryDescription(String description) {
        return nvdMapper.getVulnsByDescription(description);
    }
}
