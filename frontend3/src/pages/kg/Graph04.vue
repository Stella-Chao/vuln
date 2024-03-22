<template>
    <a-card>
      <a-input-search v-model="cveid" 
        class="inputStyle"
        placeholder="CVE-ID"
        enter-button="Search"
        size="large"
        @search="getData"
      />
      <!-- <a-button type="primary" loading style="height: 2.5rem; border-left: 50px;">推理中</a-button> -->
      <br /><br />
      <div id="graph" style="width:100%;height:500px"></div>
    </a-card>
    
  </template>
  
  <script>
  import * as echarts from 'echarts';
  
  const base_url = process.env.VUE_APP_API_BASE_URL
  import axios from 'axios'
  
  // const test_cve_id = 'CVE-2021-34703'
  
  export default {
    name: "Graph04",
    data() {
      return {
        echartsData: [
            {
                name: "CVE-2021-0285",
                category: "漏洞",
                id: "v1",
                symbolSize: 100
            },
            {
                name: "低危",
                category: "评估",
                id: "c1",
                symbolSize: 50
            },
            {
                name: "CWE-770",
                category: "弱点",
                id: "w1",
                symbolSize: 75
            },
            {
                name: "juniper",
                category: "厂商",
                id: "vd1",
                symbolSize: 75
            },
            {
                name: "junos 15.1",
                category: "受影响产品",
                id: "p1",
                symbolSize: 50
            },
            {
                name: "junos 17.3",
                category: "受影响产品",
                id: "p2",
                symbolSize: 50
            },
            {
                name: "junos 17.4",
                category: "受影响产品",
                id: "p3",
                symbolSize: 50
            },
            {
                name: "junos 18.4",
                category: "受影响产品",
                id: "p4",
                symbolSize: 50
            },
            {
                name: "junos 19.1",
                category: "受影响产品",
                id: "p5",
                symbolSize: 50
            },
            {
                name: "junos 19.2",
                category: "受影响产品",
                id: "p6",
                symbolSize: 50
            },
            {
                name: "HTTP DoS",
                category: "攻击模式",
                id: "cap1",
                symbolSize: 50
            },
            {
                name: "TCP Flood",
                category: "攻击模式",
                id: "cap2",
                symbolSize: 50
            }
            ,
            {
                name: "UDP Flood",
                category: "攻击模式",
                id: "cap3",
                symbolSize: 50
            }
            ,
            {
                name: "ICMP Flood",
                category: "攻击模式",
                id: "cap4",
                symbolSize: 50
            }
            ,
            {
                name: "HTTP Flood",
                category: "攻击模式",
                id: "cap5",
                symbolSize: 50
            }
            ,
            {
                name: "SSL Flood",
                category: "攻击模式",
                id: "cap6",
                symbolSize: 50
            }
            ,
            {
                name: "Amplification",
                category: "攻击模式",
                id: "cap7",
                symbolSize: 50
            }
            ,
            {
                name: "暂无PoC",
                category: "代码",
                id: "code1",
                symbolSize: 50
            }
            ,
            {
                name: "暂无补丁",
                category: "措施",
                id: "m1",
                symbolSize: 50
            }
        ],
        echartsRelation: [
            {
                source: "v1",
                target: "c1",
                value: "hasScore",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "v1",
                target: "w1",
                value: "hasWeakness",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "cap1",
                target: "w1",
                value: "hasExploited",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "cap2",
                target: "w1",
                value: "hasExploited",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "cap3",
                target: "w1",
                value: "hasExploited",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "cap4",
                target: "w1",
                value: "hasExploited",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "cap5",
                target: "w1",
                value: "hasExploited",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "cap6",
                target: "w1",
                value: "hasExploited",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "cap7",
                target: "w1",
                value: "hasExploited",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "v1",
                target: "p1",
                value: "hasInfluenced",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "v1",
                target: "p2",
                value: "hasInfluenced",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "v1",
                target: "p3",
                value: "hasInfluenced",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "v1",
                target: "p4",
                value: "hasInfluenced",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "v1",
                target: "p5",
                value: "hasInfluenced",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "v1",
                target: "p6",
                value: "hasInfluenced",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "vd1",
                target: "p1",
                value: "hasProduct",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "vd1",
                target: "p2",
                value: "hasProduct",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "vd1",
                target: "p3",
                value: "hasProduct",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "vd1",
                target: "p4",
                value: "hasProduct",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "vd1",
                target: "p5",
                value: "hasProduct",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "vd1",
                target: "p6",
                value: "hasProduct",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                
                source: "code1",
                target: "v1",
                value: "hasProven",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
            {
                source: "v1",
                target: "m1",
                value: "hasMeasure",
                lineStyle: {
                    width: 1.5,
                      color: "#fb5400",
                      type: 'solid'
                }
            },
        ],
        myChart: '',
        cveid: 'CVE-2021-0285'
      };
    },
    methods: {
        getData() {
          this.echartsData = [];
          this.echartsRelation = [];
          axios.get(base_url + '/graph/get?cveId=' + this.cveid).then(res=>{
            console.log(res.data)
            let result = res.data
            if (result === null) {
              this.$message.warning("未找到该 CVE-ID！", 3);
              return;
            }
            if ("cveId" in result) {
              this.echartsData.push({
                  name: result["cveId"],
                  category: 'CVE',
                  id: result["id"],
                  symbolSize: 80
              })
            }
  
            // 添加 PoC
            this.echartsData.push({
                name: "PoC",
                category: 'PoC',
                id: "PoC",
                symbolSize: 50
            })
            this.echartsRelation.push({
                source: result["id"].toString(),
                target: "PoC",
                value: "hasPoC"
            })
            if ("cvss" in result && result["cvss"].length > 0) {
              this.echartsData.push({
                  name: result["cvss"][0]["level"],
                  category: 'CVSS',
                  id: result["cvss"][0]["id"],
                  symbolSize: 50
              })
              this.echartsRelation.push({
                  source: result["id"].toString(),
                  target: result["cvss"][0]["id"].toString(),
                  value: "CVSSIS",
                  lineStyle: {
                      width: 1.5,
                      curveness: 0.1,
                      color: "#fb5400",
                      type: 'solid'
                  }
              })
              // this.echartsRelation.push({
              //     source: result["cvss"][0]["id"].toString(),
              //     target: result["id"].toString(),
              //     value: "CVSS"
              // })
            }
            if ("types" in result && result["types"].length > 0) {
              this.echartsData.push({
                  name: result["types"][0]["cweId"],
                  category: 'CWE',
                  id: result["types"][0]["id"],
                  symbolSize: 50
              })
              this.echartsRelation.push({
                  source: result["id"].toString(),
                  target: result["types"][0]["id"].toString(),
                  value: "TYPEIS"
              })
              // this.echartsRelation.push({
              //     source: result["types"][0]["id"].toString(),
              //     target: result["id"].toString(),
              //     value: "TYPE"
              // })
  
              //添加 CWE-CAPEC
              // 添加 PoC
              this.echartsData.push({
                  name: "CAPEC",
                  category: 'CAPEC',
                  id: "CAPEC",
                  symbolSize: 50
              })
              this.echartsRelation.push({
                  source: result["types"][0]["id"].toString(),
                  target: "CAPEC",
                  value: "CAPECIS"
              })
            }
            if ("products" in result && result["products"].length > 0) {
              let vendor = result["products"][0]["vendor"]
              // 添加 product-family（vendor） 节点
              this.echartsData.push({
                  name: vendor,
                  category: 'CPE',
                  id: vendor,
                  symbolSize: 60
              })
              // 添加 vuln-family
              this.echartsRelation.push({
                  source: result["id"].toString(),
                  target: vendor,
                  value: "IMPACT"
              })
              for (let i = 0; i < result["products"].length; i ++) {
                  let p = result["products"][i]
                  this.echartsData.push({
                      name: p["name"],
                      category: 'CPE',
                      id: p["id"]
                  })
                  this.echartsRelation.push({
                      source: vendor,
                      target: p["id"].toString(),
                      value: "hasChildren"
                  })
                  // this.echartsRelation.push({
                  //     source: p["id"].toString(),
                  //     target: result["id"].toString(),
                  //     value: "FOUND"
                  // })
                  if (i > 5) {
                      break;
                  }
              }
            }
            this.drawGraph()
          })
          
        },
        // 绘图
        drawGraph() {
          console.log("test...")
          console.log(this.echartsData)
          console.log(typeof(this.echartsData))
          console.log(JSON.parse(JSON.stringify(this.echartsData)))
  
          console.log(this.echartsRelation)
          console.log(typeof(this.echartsRelation))
          console.log(JSON.parse(JSON.stringify(this.echartsRelation)))
  
          var chartDom = document.getElementById('graph');
          var myChart = echarts.init(chartDom);
          let option = {
          // 提示框的配置
            tooltip: {
              formatter: x => {
                  return [x.data.name];//设置提示框的内容和格式 节点和边都显示name属性
              }
            },
            title: {
                text: 'IoT-KG'
            },
            
            animationDurationUpdate: 1500,
            animationEasingUpdate: 'quinticInOut',
            //图形上的文本标签，可用于说明图形的一些数据信息
            label: {
                show: true,
                textStyle: {
                    fontSize: 12
                },
            },
            legend: {
                x: "center",
                show: true
            },
            series: [{
              type: 'graph',
              layout: 'force',//图的布局，类型为力导图
              symbolSize: 35,//节点大小
              focusNodeAdjacency: true,//当鼠标移动到节点上，突出显示节点以及节点的边和邻接节点
              draggable: true,//指示节点是否可以拖动
              roam: true,
              borderColor: 'rgba(0, 0, 0, 255)', // 设置图形边框为淡金色,透明度为0.4
              borderWidth: 2, // 图形的描边线宽。为 0 时无描边。
              itemStyle: {  // 给节点加上阴影，显着立体
                  shadowColor: '#C0C0C0', 
                  shadowOffsetX: 2,
                  shadowOffsetY: 2
              },
              emphasis: {
                  scale: true, //节点放大效果
                  focus: 'adjacency'
              },
              categories: [{
                  name: '漏洞',
                  itemStyle: {
                      // color: "#E27471",
                      symbolSize: 20
                  }
              }, {
                  name: '评估',
                  itemStyle: {
                      // color: "orange",
                      symbolSize: 20
                  }
              }, {
                  name: '受影响产品',
                  itemStyle: {
                      // color: "orange",
                      symbolSize: 20
                  }
              }, {
                  name: '厂商',
                  itemStyle: {
                      // color: "orange",
                      symbolSize: 20
                  }
              }, {
                  name: '代码',
                  itemStyle: {
                      // color: "orange",
                      symbolSize: 20
                  }
              }, {
                  name: '措施',
                  itemStyle: {
                      // color: "orange",
                      symbolSize: 20
                  }
              }, {
                  name: '攻击模式',
                  itemStyle: {
                      // color: "orange",
                      symbolSize: 20
                  }
              }, {
                  name: '弱点',
                  itemStyle: {
                      // color: "orange",
                      symbolSize: 20
                  }
              }
            ],
              label: {
                  show: true,
                  textStyle: {
                      fontSize: 12,
                      color: "black",
                  }
              },
              force: {
                  repulsion: 500,//节点之间的斥力因子。支持数组表达斥力范围，值越大斥力越大。
                  edgeLength: 150,//边的两个节点之间的距离
                  gravity: 0.1, //节点受到的向中心的引力因子。该值越大节点越往中心点靠拢。
              },
              edgeSymbolSize: [5, 8], // 边两端的标记大小，可以是一个数组分别指定两端，也可以是单个统一指定。
              edgeSymbol: ['circle', 'arrow'],
              edgeLabel: {
                  show: true,
                  textStyle: {
                      fontSize: 10
                  },
                  formatter: "{c}"
              },
              lineStyle: {
                  opacity: 0.9,
                  width: 1.1,
                  // curveness: 0.1,
                  color: "#262626"
              },
              data: this.echartsData,
              links: this.echartsRelation
            }]
          };
          myChart.setOption(option);
          //节点自定义拖拽不回弹
          myChart.on('mouseup', function (params) {
              var op = myChart.getOption();
              op.series[0].data[params.dataIndex].x = params.event.offsetX;
              op.series[0].data[params.dataIndex].y = params.event.offsetY;
              op.series[0].data[params.dataIndex].fixed = true;
              myChart.setOption(op);
          });
        }
      },
      
    // 页面加载时的操作
    mounted(){
      // this.drawGraph();
      this.drawGraph()
    },
    created(){
    //   this.getData()
    }
  };
  </script>
      
  
  
  <style>
  .inputStyle {
      width: 80%;
      height: 2.5rem;
      font-size: 14px;
      line-height: 48px;
      border-radius: 25px;
      padding-left:250px;
    }
  </style>