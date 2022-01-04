<template>
  <page-layout title="漏洞图谱">
    <div>
        <div id="graph" style="width:100%;height:500px"></div>
    </div>
  </page-layout>
</template>
<script>
import * as echarts from "echarts/lib/echarts";
import "echarts/lib/chart/graph";
import { TitleComponent } from 'echarts/components';
echarts.use([TitleComponent]);

export default {
  name: "Graph",
  props: ['vuln'],
  data() {
    return {
      graph: "",
      pointData: [],
      linkData: [],
      categoryData: [],
      options: {
        title: {
          text: "漏洞图谱"
        },
        series: [
          {
            name: "知识图谱",
            type: "graph",
            layout: "force",
            force: {
              repulsion: 60,
              gravity: 0.1,
              edgeLength: 40,
              layoutAnimation: true
            },
            data: [],
            links: [],
            categories: [],
            roam: false,
            symbol: 'circle',
            symbolSize: 60,
            cursor: 'pointer',
            label: {
              normal: {
                show: true,
                position: "inside",
                formatter: "{b}",
                fontSize: 16,
                fontStyle: "600"
              }
            },
            lineStyle: {
              normal: {
                opacity: 0.9,
                width: 2.0,
                curveness: 0
              }
            }
          }
        ]
      }
    };
  },
  methods: {
    setPointData(list, category) {
      list.forEach((name, index) => {
        this.pointData.push({
          x: index * 50,
          y: 20 + index * 50,
          name,
          category,
          draggable: true
        });
      });
    },
    setLinkData(list, target) {
      list.forEach(source => {
        this.linkData.push({
          source,
          target,
          lineStyle: {
            normal: {
              color: "source"
            }
          }
        });
      });
    },
    setCategoryData(list) {
      list.forEach(name => {
        this.categoryData.push({ name });
      });
    }
  },
  created() {},
  mounted() {
    console.log(this.vuln)
    this.graph = echarts.init(document.getElementById("graph"));
    let pointList1 = ["详情"];
    pointList1.push(this.vuln["type01"][0]);
    pointList1.push(this.vuln["cweID"][0]);
    let pointList2 = ["攻击"];
    pointList2.push(String(this.vuln["cvssV3"]["cvssV3"]["baseScore"]));
    pointList2.push(this.vuln["cvssV3"]["cvssV3"]["baseSeverity"]);
    pointList2.push(this.vuln["cvssV3"]["cvssV3"]["attackVector"]);
    pointList2.push(this.vuln["cvssV3"]["cvssV3"]["attackComplexity"]);
    let pointList3 = ["影响", "硬件", "Cisco", "路由器"];
    let pointList4 = ["CVE"];
    this.setPointData(pointList1, "红");
    this.setPointData(pointList2, "蓝");
    this.setPointData(pointList3, "黄");
    this.setPointData(pointList4, "CVE");
    this.setLinkData(pointList1, "详情");
    this.setLinkData(pointList2, "攻击");
    this.setLinkData(pointList3, "影响");
    this.setLinkData(["详情", "攻击", "影响"], "CVE");
    this.setCategoryData(["红", "蓝", "黄", "CVE"]);
    this.options.series[0].data = this.pointData;
    this.options.series[0].links = this.linkData;
    this.options.series[0].categories = this.categoryData;
    // this.options.legend.data = this.categoryData;
    this.$nextTick(() => {
      window.console.log(this.options);
      this.graph.setOption(this.options);
    });
  }
};
</script>
<style scoped>
#graph {
}
</style>