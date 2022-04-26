<template>
  <div>
    <a-input-search v-model="cveid"
        placeholder="CVE-ID"
        enter-button="Search"
        size="large"
        @search="onSearch"
      />
      <br /><br />
      <div ref="graph" id="graph"></div>
  </div>
      
    
</template>

<script>
import ForceGraph from "force-graph";
import axios from 'axios'

const base_url = process.env.VUE_APP_API_BASE_URL

const N = 300;
const gData = {
  nodes: [...Array(N).keys()].map((i) => ({ id: i })),
  links: [...Array(N).keys()]
    .filter((id) => id)
    .map((id) => ({
      source: id,
      target: Math.round(Math.random() * (id - 1)),
    })),
};

export default {
  name: "Graph02",
  data() {
    return {
      cveid: "",
      myGraph: null,
      graphData: null,
    };
  },
  mounted() {
    this.initGraph();
  },
  methods: {
    async initGraph() {
      this.myGraph = ForceGraph()(document.getElementById("graph"))
        .linkDirectionalParticles(2)
        .graphData(gData)
        .nodeLabel("id")
        .onNodeClick((node) => {
          // Center/zoom on node
          this.myGraph.centerAt(node.x, node.y, 1000);
          this.myGraph.zoom(8, 2000);
          
        });
    },
    onSearch() {
      axios.get(base_url + '/graph/get?cveId=' + this.cveid)
      for (let node in gData.nodes) {
        if (this.cveid === node) {
          console.log(node)
          this.myGraph.centerAt(node.x, node.y, 1000)
          console.log(node.x, " ", node.y)
          this.myGraph.zoom(8, 2000)
          break;
        }
      }
      console.log("hello")
      console.log(gData)
    },
  },
};
</script>


