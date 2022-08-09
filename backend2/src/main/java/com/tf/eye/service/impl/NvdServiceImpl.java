package com.tf.eye.service.impl;

import com.tf.eye.repository.NvdMapper;
import com.tf.eye.model.domain.Nvd;
import com.tf.eye.service.NvdService;
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
