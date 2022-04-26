<template>
  <div id="graph"></div>
</template>

<script>
import ForceGraph from "3d-force-graph";

// Random tree
const N = 80;
const gData = {
  nodes: [...Array(N).keys()].map((i) => ({ id: i })),
  links: [...Array(N).keys()]
    .filter((id) => id)
    .map((id) => ({
      source: id,
      target: Math.round(Math.random() * (id - 1)),
    })),
};

// cross-link node objects
gData.links.forEach((link) => {
  const a = gData.nodes[link.source];
  const b = gData.nodes[link.target];
  !a.neighbors && (a.neighbors = []);
  !b.neighbors && (b.neighbors = []);
  a.neighbors.push(b);
  b.neighbors.push(a);

  !a.links && (a.links = []);
  !b.links && (b.links = []);
  a.links.push(link);
  b.links.push(link);
});

const highlightNodes = new Set();
const highlightLinks = new Set();
let hoverNode = null;

export default {
  name: "Graph03",
  data() {
    return {
      myGraph: null,
      graphData: null,
    };
  },
  mounted() {
      
    this.initGraph();
  },
  created() {
      
  },
  methods: {
    
    initGraph() {
      this.myGraph = ForceGraph()(document.getElementById("graph"))
        .graphData(gData)
        .nodeColor(node => highlightNodes.has(node) ? node === hoverNode ? 'rgb(255,0,0,1)' : 'rgba(255,160,0,0.8)' : 'rgba(0,255,255,0.6)')
        .linkWidth(link => highlightLinks.has(link) ? 4 : 1)
        .linkDirectionalParticles(link => highlightLinks.has(link) ? 4 : 0)
        .linkDirectionalParticleWidth(4)
        .onNodeHover(node => {
          // no state change
          if ((!node && !highlightNodes.size) || (node && hoverNode === node)) return;

          highlightNodes.clear();
          highlightLinks.clear();
          if (node) {
            highlightNodes.add(node);
            node.neighbors.forEach(neighbor => highlightNodes.add(neighbor));
            node.links.forEach(link => highlightLinks.add(link));
          }

          hoverNode = node || null;

        //   updateHighlight();
            this.myGraph
                .nodeColor(this.Graph.nodeColor())
                .linkWidth(this.Graph.linkWidth())
                .linkDirectionalParticles(this.Graph.linkDirectionalParticles());
          
        })
        .onLinkHover(link => {
          highlightNodes.clear();
          highlightLinks.clear();

          if (link) {
            highlightLinks.add(link);
            highlightNodes.add(link.source);
            highlightNodes.add(link.target);
          }

        //   updateHighlight();
        this.myGraph
                .nodeColor(this.Graph.nodeColor())
                .linkWidth(this.Graph.linkWidth())
                .linkDirectionalParticles(this.Graph.linkDirectionalParticles());
        });
    },

  },
};
</script>
    
<style>
</style>