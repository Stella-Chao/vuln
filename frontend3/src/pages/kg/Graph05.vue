<template>
  <a-checkbox-group v-model:value="value1" name="checkboxgroup" :options="plainOptions" />
  <br />
  <br />
  <a-checkbox-group v-model:value="value2" :options="plainOptions" />
  <br />
  <br />
  <a-checkbox-group v-model:value="value3" :options="options" />
  <br />
  <br />
  <a-checkbox-group v-model:value="value4" :options="optionsWithDisabled" disabled>
    <template #label="{ value }">
      <span style="color: red">{{ value }}</span>
    </template>
  </a-checkbox-group>
</template>
<script>
import { defineComponent, reactive, toRefs } from 'vue';
const plainOptions = ['Apple', 'Pear', 'Orange'];
const options = [{
  label: 'Apple',
  value: 'Apple',
}, {
  label: 'Pear',
  value: 'Pear',
}, {
  label: 'Orange',
  value: 'Orange',
}];
const optionsWithDisabled = [{
  value: 'Apple',
}, {
  label: 'Pear',
  value: 'Pear',
}, {
  label: 'Orange',
  value: 'Orange',
  disabled: false,
}];
export default defineComponent({
  setup() {
    const state = reactive({
      value1: [],
      value2: ['Apple'],
      value3: ['Pear'],
      value4: ['Apple'],
    });
    return {
      plainOptions,
      options,
      optionsWithDisabled,
      ...toRefs(state),
    };
  },
});
</script>