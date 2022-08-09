package com.tf.eye.controller;

import com.tf.eye.model.domain.Nvd;
import com.tf.eye.service.NvdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class NvdController {
    @Autowired
    NvdService nvdService;

    @GetMapping("/vuln")
    public List<Nvd> getAllVuln() {
        return nvdService.getList();
    }

    @GetMapping("/vuln/{name}")
    public Nvd getVulnByName(String name) {
        return nvdService.queryName(name);
    }

    @GetMapping("/vuln/{description}")
    public List<Nvd> getVulnByDescription(String description) {
        return nvdService.queryDescription(description);
    }
}
