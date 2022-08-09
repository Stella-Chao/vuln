package com.tf.eye.service;

import com.tf.eye.model.domain.Nvd;

import java.util.List;


public interface NvdService {

    public List<Nvd> getList();

    public Nvd queryName(String name);

    public List<Nvd> queryDescription(String description);
}
