<template>
    <div ref="graph" id="graph"></div>
  
</template>

<script>
import ForceGraph from "3d-force-graph"

const N = 300;
const gData = {
    nodes: [...Array(N).keys()].map(i => ({ id: i })),
    links: [...Array(N).keys()]
    .filter(id => id)
    .map(id => ({
        source: id,
        target: Math.round(Math.random() * (id-1))
    }))
};


export default {
  name: "Graph01",
  data() {
    return {
      myGraph: null,
      graphData: null,
    };
  },
  mounted() {
      this.initGraph ()
  },
  methods: {
      async initGraph() {
          this.myGraph = ForceGraph()(document.getElementById('graph'))
            .linkDirectionalParticles(2)
            .graphData(gData);
      }
  }
}
</script>


