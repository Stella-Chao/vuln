<template>
  <div>
    <div :style="{ borderBottom: '1px solid #E9E9E9' }">
      <a-checkbox :indeterminate="indeterminate" :checked="checkAll" @change="onCheckAllChange">
        Check all
      </a-checkbox>
    </div>
    <br />
    <a-checkbox-group v-model="checkedList" :options="plainOptions" @change="onChange" />
    <a-button type="primary" @click="submit">
      订阅
    </a-button>
  </div>
</template>
<script>
const plainOptions = ["视频监控类", "智能家居类", "工业控制类", "移动设备类"];
const defaultCheckedList = [];
export default {
  data() {
    return {
      checkedList: defaultCheckedList,
      indeterminate: true,
      checkAll: false,
      plainOptions,
    };
  },
  methods: {
    onChange(checkedList) {
      this.indeterminate = !!checkedList.length && checkedList.length < plainOptions.length;
      this.checkAll = checkedList.length === plainOptions.length;
    },
    onCheckAllChange(e) {
      Object.assign(this, {
        checkedList: e.target.checked ? plainOptions : [],
        indeterminate: false,
        checkAll: e.target.checked,
      });
    },
    submit() {
      this.$message.success("订阅成功!", 2)
    }
  },
};
</script>
