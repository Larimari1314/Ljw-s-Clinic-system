<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="个人信息修改" name="first">
    </el-tab-pane>
    <el-tab-pane label="登录信息修改" name="second">
    </el-tab-pane>
  </el-tabs>
</template>
<script>

export default {
  data() {
    return {
      editFormDoctorVisibleDoctor:true,
      activeName: 'second'
    }
  },

  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  }, mounted: function () {
  }
}
</script>

<style>

</style>
