package com.tf.backend.service.impl;

import com.tf.backend.repository.NvdMapper;
import com.tf.backend.model.domain.Nvd;
import com.tf.backend.service.NvdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NvdServiceImpl implements NvdService {

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
