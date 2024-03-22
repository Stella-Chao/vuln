package com.tf.eye.utils;

import com.tf.eye.model.domain.*;
import com.tf.eye.model.node.*;
import com.tf.eye.repository.graph.*;
import com.tf.eye.repository.mongo.CAPECDao;
import com.tf.eye.repository.mongo.PocDao;
import com.tf.eye.repository.mongo.TFiotDao;
import com.tf.eye.repository.mongo.WeaknessDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zhanghe
 * @date 2022/3/13 20:02
 */

@Component
public class Neo4jUtil {

    @Autowired
    TFiotDao iotDao;

    @Autowired
    PocDao pocDao;

    @Autowired
    WeaknessDao weaknessDao;

    @Autowired
    CAPECDao capecDao;

    @Autowired
    VulnRepository vulnRepository;

    @Autowired
    CapecRepository capecRepository;

    @Autowired
    WeaknessRepository weaknessRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CVSSRepository cvssRepository;
    @Autowired
    VendorRepository vendorRepository;
    @Autowired
    MeasureRepository measureRepository;
    @Autowired
    PocRepository pocRepository;

    public void clearNeo4j() {
        vulnRepository.deleteAll();
        weaknessRepository.deleteAll();
        productRepository.deleteAll();
        cvssRepository.deleteAll();
    }

    // 添加 PocNode
    public void addPocNode() {
        List<POC> list = pocDao.getAllPoc();
        for (POC item: list) {
            CodeNode node = new CodeNode();
            node.setCode(item.getCodeUrl());
            pocRepository.save(node);
        }
    }


    public void addMeasure() {

    }

    // 添加 WeaknessNode
    public void addWeaknessNode() {

        List<CWE> cwes = weaknessDao.getAllWeakness();
        for (CWE node: cwes) {
            System.out.println(node);
            WeaknessNode weaknessNode = new WeaknessNode();
            weaknessNode.setCweId(node.getCweId());
            weaknessNode.setName(node.getName());
            weaknessNode.setDescription(node.getDescription());
            System.out.println(weaknessNode);
            weaknessRepository.save(weaknessNode);
        }

    }

    // 添加 AttackPatternNode
    public void addCapecNode() {
        List<CAPEC> capecs = capecDao.getAllCapec();
        for (CAPEC node: capecs) {
            CapecNode capecNode = new CapecNode();
            capecNode.setCapecId(node.getCapecId());
            capecNode.setDescription(node.getDescription());
            capecNode.setName(node.getName());
            capecNode.setSeverity(node.getSeverity());
            capecNode.setLikelihood(node.getLikelihood());
            List<WeaknessNode> cwes = new ArrayList<>();
            List<String> cweIds = node.getCwe();
            if (cweIds != null && cweIds.size() > 0) {
                for (String cweId: cweIds) {
                    cwes.add(weaknessRepository.findTypeNodeBycweId(cweId));
                }
            }
            if (cwes != null && cwes.size() > 0) {
                capecNode.setCwes(cwes);
            }
            System.out.println(capecNode);
            if (capecNode != null) {
                try {
                    capecRepository.save(capecNode);
                } catch (Exception e) {
                    continue;
                }

            }
        }

    }

    // 添加 ProductNode, VendorNode
    public void addProductNode() {
        List<TFiot> vulns = iotDao.getAllVuln();
        Set<String> products = new HashSet<>();
        Map<String, Set<ProductNode>> vendors = new HashMap<>();
        for (TFiot vuln: vulns) {
            if (vuln.getCpe() == null) continue;
            for (Cpe cpe: vuln.getCpe()) {
                String[] cpeUri = cpe.getCpe23Uri().split(":");
                String vendor = cpeUri[3];
                String name = cpeUri[4];
                if (products.contains(name)) {
                    continue;
                }
                System.out.println("vendor: " + vendor);
                System.out.println("name: " + name);
                products.add(name);
                ProductNode node = new ProductNode(name, vendor);
                productRepository.save(node);
                // 统计 vendor 对应 product
                Set<ProductNode> vProducts = vendors.getOrDefault(vendor, new HashSet<>());
                vProducts.add(node);
                vendors.put(vendor, vProducts);
            }
        }
        for (String vendor: vendors.keySet()) {
            VendorNode vendorNode = new VendorNode(vendor);
            vendorNode.setProducts(vendors.get(vendor));
            vendorRepository.save(vendorNode);
        }
    }

//    public void addCVSS2VulnNode() {
//        List<TFiot> vulns = iotDao.getAllVuln();
//        int cnt = 0;
//        for (TFiot vuln: vulns) {
//            if (cnt ++ == 100) break;
//            Long cvssId = vuln.get
//            // 漏洞 - CVSS
//            CVSSNode cvssNode = cvssRepository.findCVSSNodeById()
//            if (vuln.getCvssV3() != null) {
//                cvssNode.setAttack(vuln.getCvssV3().getCvssV3().getAttackVector());
//                cvssNode.setScore(vuln.getCvssV3().getCvssV3().getBaseScore());
//                cvssNode.setLevel(vuln.getCvssV3().getCvssV3().getBaseSeverity());
//                cvssNode.setNode(vulnNode);
//            }
//            // 漏洞 - 产品
//            for (Cpe cpe: vuln.getCpe()) {
//                String[] cpeUri = cpe.getCpe23Uri().split(":");
//                String productName = cpeUri[4];
//                ProductNode node = productRepository.findProductNodeByName(productName);
////                node.setNode(vulnNode);
//            }
//
//            vulnNode.setCvss(cvssNode);
//            vulnNode.setTypes(typeNodes);
//            vulnNode.setProducts(productNodes);
//            vulnNode.setTitle(vuln.getTitle());
//            vulnNode.setCveId(vuln.getCveID());
//            vulnNode.setPublishedDate(vuln.getPublishedDate());
//            vulnNode.setLastModified(vuln.getLastModifiedDate());
//            System.out.println(vulnNode);
//            vulnRepository.save(vulnNode);
//        }
//    }

    public void test() {
        System.out.println(weaknessRepository.findTypeNodeBycweId("CWE-193"));
    }

    public void mongo2Neo4j() {
        List<TFiot> vulns = iotDao.getAllVuln();
        int cnt = 0;
        for (TFiot vuln : vulns) {
            try {
                VulnerabilityNode vulnerabilityNode = new VulnerabilityNode();
                Set<ProductNode> productNodes = new HashSet<>();
                Set<WeaknessNode> weaknessNodes = new HashSet<>();
                // 漏洞 - 类型
                if (vuln.getCweID() != null) {
                    for (String cwe : vuln.getCweID()) {
                        WeaknessNode node = weaknessRepository.findTypeNodeBycweId(cwe);
                        weaknessNodes.add(node);
                    }
                }

                // 漏洞 - CVSS
                CVSSNode cvssNode = new CVSSNode();
                if (vuln.getCvssV3() != null) {
                    cvssNode.setAttack(vuln.getCvssV3().getCvssV3().getAttackVector());
                    cvssNode.setScore(vuln.getCvssV3().getCvssV3().getBaseScore());
                    cvssNode.setLevel(vuln.getCvssV3().getCvssV3().getBaseSeverity());
                } else {
                    cvssNode.setAttack(vuln.getCvssV2().getCvssV2().getAccessComplexity());
                    cvssNode.setScore(vuln.getCvssV2().getCvssV2().getBaseScore());
                    cvssNode.setLevel(vuln.getCvssV2().getSeverity());
                }
                // 漏洞 - 产品
                if (vuln.getCpe() != null) {
                    for (Cpe cpe : vuln.getCpe()) {
                        String[] cpeUri = cpe.getCpe23Uri().split(":");
                        String productName = cpeUri[4];
                        ProductNode node = productRepository.findProductNodeByName(productName);
                        if (node != null) productNodes.add(node);
                    }
                }
                if (cvssNode != null) vulnerabilityNode.setCvss(cvssNode);
                if (weaknessNodes != null) vulnerabilityNode.setWeaknessNodes(weaknessNodes);
                if (productNodes != null) vulnerabilityNode.setProducts(productNodes);
                vulnerabilityNode.setTitle(vuln.getTitle());
                vulnerabilityNode.setCveId(vuln.getCveID());
                vulnerabilityNode.setDescription(vuln.getDescription());
                vulnerabilityNode.setPublishedDate(vuln.getPublishedDate());
                vulnerabilityNode.setLastModified(vuln.getLastModifiedDate());
                System.out.println(vulnerabilityNode);
                vulnRepository.save(vulnerabilityNode);
            } catch (Exception e) {
                continue;
            }
        }
    }



    public static void main(String[] args) {
        Neo4jUtil util = new Neo4jUtil();
        util.addWeaknessNode();
    }
}
