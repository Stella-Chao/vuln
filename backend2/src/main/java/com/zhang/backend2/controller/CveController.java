package com.zhang.backend2.controller;

import com.zhang.backend2.service.CveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cve")
public class CveController {

    @Autowired
    private CveService cveService;

}
