package com.tf.eye.reasoning;

import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.PrintUtil;

import java.util.Iterator;

/**
 * @author zh
 * @date 2022/10/18 23:36
 */
public class Demo02 {
    public static void main(String[] args) {
        Model onlineModel = ModelFactory.createDefaultModel();
        String prefix = "http://www.example.org/mingjiao#";

        Resource mingjiao = onlineModel.createResource(prefix + "mingjiao");
        Resource zhangwuji = onlineModel.createResource(prefix + "zhangwuji");
        Resource weifuwang = onlineModel.createResource(prefix + "weifuwang");
        Resource baimeiyingwang = onlineModel.createResource(prefix + "baimeiyingwang");

        Property zhizhang = onlineModel.createProperty(prefix + "zhizhang");
        Property leader = onlineModel.createProperty(prefix + "leader");
        Property shuyu = onlineModel.createProperty(prefix + "shuyu");

        onlineModel.add(zhangwuji, zhizhang, mingjiao);
        onlineModel.add(zhangwuji, shuyu, mingjiao);
        onlineModel.add(weifuwang, leader, zhangwuji);

        PrintUtil.registerPrefix("", prefix);

        StmtIterator i = onlineModel.listStatements(null, null, (RDFNode)null);
        System.out.println("推理前");
        while (i.hasNext()) {
            System.out.println('-' + PrintUtil.print(i.nextStatement()));
        }

        String rules = "[rule: (?p :zhizhang ?c)(?a :leader ?p) -> (?a :shuyu ?c)]";
        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
        InfModel inf = ModelFactory.createInfModel(reasoner, onlineModel);
        Iterator list = inf.listStatements(null, null, (RDFNode)null);
        System.out.println("推理后");
        while (list.hasNext()) {
            System.out.println(" - " + PrintUtil.print(list.next()));
        }
    }
}
