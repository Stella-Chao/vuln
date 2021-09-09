<template>
  <el-card class="box-card">
  <template #header>
    <div class="card-header">
      <span>漏洞详情</span>
      <el-button class="button" type="text">check</el-button>
    </div>
  </template>
  <div>
      <span>CWE-ID: {{detail['name']}}</span>
      <span>URL: {{detail['url']}}</span>
      <span>CWE：{{detail['cwe_id']}}</span>
      <span>CVSS3_Score: {{detail['cvss3_nvd_base_score']}}</span>
      <span>CVSS3_Vector: {{detail['cvss3_nvd_vector']}}</span>
      <span>漏洞描述: {{detail['description']}}</span>
      <span>相关链接: {{detail['refer']}}</span>
  </div>
</el-card>
</template>


<script>
import { getLeakByCVE} from "../api/index";
export default {
    name : "Detail",
    data () {
        return {
            name: this.$route.params, 
            test: "hello",
            detail: ''
        }
    },
    mounted () {
        console.log('getDetail')
        console.log(this.$route.params['name'])
        getLeakByCVE(this.$route.params['name']).then((res) => {
            console.log(res[0].fields)
            this.detail = res[0].fields

        })
    }
}

</script>

<style>
  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .box-card {
    width: 480px;
  }
</style>