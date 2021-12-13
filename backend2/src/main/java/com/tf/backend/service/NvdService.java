package com.zhang.backend2.service;

import com.zhang.backend2.model.domain.Nvd;
import org.springframework.stereotype.Service;

import java.util.List;


public interface NvdService {

    public List<Nvd> getList();

    public Nvd queryName(String name);

    public List<Nvd> queryDescription(String description);
}
