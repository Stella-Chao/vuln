<template>
  <div>
      {{ vuln }}
  </div>
</template>

<script>
export default {
    name = "Detail",
    data () {
        return {
            vuln: this.$router.params
        }
    },
    mounted () {
        let params = {
            
        }
    }
}
</script>

<style>

</style>