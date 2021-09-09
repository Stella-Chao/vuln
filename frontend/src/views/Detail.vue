<template>
  <div>
      {{ name }}
  </div>
</template>

<script>
import { getDetail } from "../api/index";
export default {
    name : "Detail",
    data () {
        return {
            name: this.$route.params, 
            test: "hello"
        }
    },
    mounted () {
        getDetail(this.$route.params)
    }
}
</script>

<style>

</style>