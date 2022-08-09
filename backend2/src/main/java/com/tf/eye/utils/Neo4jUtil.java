package com.tf.eye.utils;

import com.tf.eye.model.domain.Cpe;
import com.tf.eye.model.domain.TFiot;
import com.tf.eye.model.node.CVSSNode;
import com.tf.eye.model.node.ProductNode;
import com.tf.eye.model.node.TypeNode;
import com.tf.eye.model.node.VulnNode;
import com.tf.eye.repository.graph.CVSSRepository;
import com.tf.eye.repository.graph.ProductRepository;
import com.tf.eye.repository.graph.TypeRepository;
import com.tf.eye.repository.graph.VulnRepository;
import com.tf.eye.repository.mongo.TFiotDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhanghe
 * @date 2022/3/13 20:02
 */

@Component
public class Neo4jUtil {

    @Autowired
    TFiotDao iotDao;

    @Autowired
    VulnRepository vulnRepository;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CVSSRepository cvssRepository;

    public void clearNeo4j() {
        vulnRepository.deleteAll();
        typeRepository.deleteAll();
        productRepository.deleteAll();
        cvssRepository.deleteAll();
    }

    // 添加 TypeNode
    public void addTypeNode() {
        List<TFiot> vulns = iotDao.getAllVuln();
        Set<String> types = new HashSet<>();
        for (TFiot vuln: vulns) {
            for (String cwe: vuln.getCweID()) {
                if (!types.contains(cwe)) {
                    types.add(cwe);
                }
            }
        }
        for (String cwe: types) {
            TypeNode node = new TypeNode(cwe);
            typeRepository.save(node);
        }
    }

    // 添加 ProductNode
    public void addProductNode() {
        List<TFiot> vulns = iotDao.getAllVuln();
        Set<String> products = new HashSet<>();
        for (TFiot vuln: vulns) {
            for (Cpe cpe: vuln.getCpe()) {
                String[] cpeUri = cpe.getCpe23Uri().split(":");
                String vendor = cpeUri[3];
                String name = cpeUri[4];
                System.out.println("vendor: " + vendor);
                System.out.println("name: " + name);
                if (products.contains(name)) {
                    continue;
                }
                products.add(name);
                ProductNode node = new ProductNode(name, vendor);
                productRepository.save(node);
            }
        }
    }


    public void test() {
        System.out.println(typeRepository.findTypeNodeBycweId("CWE-193"));
    }

    public void mongo2Neo4j() {
        vulnRepository.deleteAll();
        List<TFiot> vulns = iotDao.getAllVuln();
        for (TFiot vuln: vulns) {
            VulnNode vulnNode = new VulnNode();
            Set<TypeNode> typeNodes = new HashSet<>();
            Set<ProductNode> productNodes = new HashSet<>();
            Set<CVSSNode> cvssNodes = new HashSet<>();
            Set<VulnNode> vulnNodes = new HashSet<>();

            // 漏洞 - 类型
            for (String cwe: vuln.getCweID()) {
                TypeNode node = typeRepository.findTypeNodeBycweId(cwe);
                System.out.println(node);
                typeNodes.add(node);
            }
            // 漏洞 - CVSS
            CVSSNode cvssNode = new CVSSNode();
            if (vuln.getCvssV3() != null) {
                cvssNode.setAttack(vuln.getCvssV3().getCvssV3().getAttackVector());
                cvssNode.setScore(vuln.getCvssV3().getCvssV3().getBaseScore());
                cvssNode.setLevel(vuln.getCvssV3().getCvssV3().getBaseSeverity());
            }
            // 漏洞 - 产品
            for (Cpe cpe: vuln.getCpe()) {
                String[] cpeUri = cpe.getCpe23Uri().split(":");
                String productName = cpeUri[4];
                ProductNode node = productRepository.findProductNodeByName(productName);
                productNodes.add(node);
            }

            cvssNodes.add(cvssNode);
            vulnNode.setCvss(cvssNodes);
            vulnNode.setTypes(typeNodes);
            vulnNode.setProducts(productNodes);
            vulnNode.setTitle(vuln.getTitle());
            vulnNode.setCveId(vuln.getCveID());
            vulnNode.setPublishedDate(vuln.getPublishedDate());
            vulnNode.setLastModified(vuln.getLastModifiedDate());
            System.out.println(vulnNode);
            vulnRepository.save(vulnNode);
        }
    }

    // 计算 漏洞-漏洞 关联度 => 添加边
    public void CaculateRelation() {
        List<VulnNode> list = vulnRepository.findAll();
        for (VulnNode node: list) {
            Set<VulnNode> vulns = new HashSet<>();
            int similar = 0;
            Set<TypeNode> types = node.getTypes();
            Set<ProductNode> products = node.getProducts();
            String attack1 = new ArrayList<CVSSNode>(node.getCvss()).get(0).getAttack();
            for (VulnNode node2: list) {
                if (node.equals(node2)) {
                    continue;
                }
                if (types.equals(node2.getTypes())) {
                    similar ++;
                }
                if (products.equals(node2.getProducts())) {
                    similar ++;
                }
                String attack2 = new ArrayList<CVSSNode>(node2.getCvss()).get(0).getAttack();
                if (attack1.equals(attack2)) {
                    similar ++;
                }
                if (similar >= 2) {
                    vulns.add(node2);
                }
            }
            node.setVulns(vulns);
            vulnRepository.save(node);
        }
        int similar = 0;
    }


    public static void main(String[] args) {

    }
}
