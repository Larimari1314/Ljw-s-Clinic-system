<template>
  <section>
    <!--列表-->
    <template>
      <!--      <a href="/?#/DoctorInformationManagement"></a>-->
      <el-table :data="doctorList" highlight-current-row v-loading="loading" @selection-change="selsChange"
                style="width: 100%;">
        <el-table-column type="index" label="序号" width="80">
        </el-table-column>
        <el-table-column prop="avatarUrl" width="150" label="医生头像" scope="scope">
          <template scope="scope">
              <img :src="doctorList[scope.$index].avatarUrl" style="border-radius:50%; " width="100" height="100"
                   alt="医生头像">
          </template>
        </el-table-column>
        <el-table-column prop="doctorName" label="医生姓名" width="130">
        </el-table-column>
        <el-table-column prop="doctorPhone" label="电话" width="140">
        </el-table-column>
        <el-table-column prop="doctorSex" label="性别" width="100" :formatter="formatSex" sortable>
        </el-table-column>
        <el-table-column prop="doctorPassword" label="密码" width="120">
        </el-table-column>
        <el-table-column prop="salary" label="薪资" width="140" sortable>
        </el-table-column>
        <el-table-column prop="doctorAge" label="年龄" width="100" sortable>
        </el-table-column>
        <el-table-column prop="doctorAddr" label="家庭住址" min-width="170">
        </el-table-column>
      </el-table>
    </template>

  </section>
</template>

<script>
//import NProgress from 'nprogress'
import {findDepartmentDoctor} from '../../api/api';

export default {
  data() {
    return {
      loading: false,
      doctorList: []
    }
  },
  methods: {
    formatSex: function (row, column) {
      return row.doctorSex == 1 ? '男' : row.doctorSex == 0 ? '女' : '未知';
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
    handleEdit() {

    },
    //获取用户列表
    getDepartmentDoctor: function () {
      var id = this.$route.query.id + '';
      this.loading = true;
      //NProgress.start();
      findDepartmentDoctor(id).then((res) => {
        if (res.data.code === '200') {
          this.doctorList = res.data.data;
          this.loading = false;
        } else {
          this.$notify.error({
            title: '错误',
            message: res.data.message
          });
        }
        //NProgress.done();
      });
    }
  },
  mounted() {

    this.getDepartmentDoctor();
  }
};

</script>

<style scoped>

</style>