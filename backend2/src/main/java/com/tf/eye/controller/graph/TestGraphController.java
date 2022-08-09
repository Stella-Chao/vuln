package com.tf.eye.controller.graph;

import com.tf.eye.model.node.TypeNode;
import com.tf.eye.model.node.VulnNode;
import com.tf.eye.repository.graph.VulnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;

/**
 * @author zhanghe
 * @date 2022/3/10 23:17
 */
@RestController
@RequestMapping("/graph")
public class TestGraphController {

    @Autowired
    private VulnRepository vulnRepository;

    @GetMapping("hello")
    public String helloGraph() {
        VulnNode node = new VulnNode();
        TypeNode typeNode = new TypeNode();
        node.setCveId("CVE-2022-0003");
        HashSet<TypeNode> typeSet = new HashSet<>();
        typeSet.add(typeNode);
        node.setTypes(typeSet);
        vulnRepository.save(node);
        System.out.println("节点添加成功...");
        return node.toString();
    }

    @GetMapping("get")
    public String getVulnTop(String cveId) {
        System.out.println(cveId);
        VulnNode node = vulnRepository.findVulnNodeByCveId(cveId);
        System.out.println(node);
//        List<VulnNode> list = vulnRepository.findAllByCveId();
//        List<VulnNode> list = vulnRepository.getAllByCveId();
//        System.out.println(list);

//        List<VulnNode> vulns = vulnRepository.findAll();
//        System.out.println(vulns);
//        return vulns.toString();
        return "";
    }
}

