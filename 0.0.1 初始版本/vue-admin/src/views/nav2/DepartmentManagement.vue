<template>
  <section>
    <!--列表-->
    <template>
<!--      <a href="/?#/DepartmentDoctorList">www</a>-->
      <el-table :data="users" highlight-current-row v-loading="loading" style="width: 100%;text-align: center">
        <el-table-column prop="imgUrl" width="300" label="科室封面" scope="scope">
          <template scope="scope">
            <div style="text-align:center;">
              <a href="http://localhost:9678/#/DepartmentManagement">
              <img :src="users[scope.$index].imgUrl" style="border-radius:25%; " width="250" height="150"
                   alt="科室照片"></a>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="departmentName" label="科室名称" width="120">
        </el-table-column>
        <el-table-column prop="departmentIntroduce" label="科室介绍" width="680">
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template scope="scope">
            <el-button type="info" size="small" @click="handleEdit(scope.$index, scope.row)">查看所属医师</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog title="新增" v-model="list" :close-on-click-modal="false">
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
          <el-form :inline="true" :model="searchDoctor">
            <el-form-item>
              <el-input v-model="searchDoctor.name" placeholder="姓名"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" v-on:click="findByName">查询</el-button>
            </el-form-item>
          </el-form>
        </el-col>
        <el-table :data="doctorList" highlight-current-row v-loading="loadings" @selection-change="selsChange"
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
          <el-table-column prop="doctorSex" label="性别" width="100" :formatter="formatSex" sortable>
          </el-table-column>
          <el-table-column prop="salary" label="薪资" width="140" sortable>
          </el-table-column>
          <el-table-column prop="doctorAge" label="年龄" width="100" sortable>
          </el-table-column>
        </el-table>
      </el-dialog>
    </template>

  </section>
</template>
<script>
//import NProgress from 'nprogress'
import {
  DoctorFindByName, DoctorFindByNameList,
  findAllDepartment, findDepartmentDoctor
} from '../../api/api';
export default {
  data() {
    return {
      searchDoctor:{name:''},
      list:false,
      loading: false,
      loadings: false,
      users: [],
      doctorList: [],
      rowId:''
    }
  },
  methods: {
    findByName:function () {
      let para = {
        name: this.searchDoctor.name,
        did:this.rowId
      };
      let _this = this;
      DoctorFindByNameList(para).then(function (res) {
        if(res.data.code=='200'){
          _this.doctorList = res.data.data;
        }else {
          this.$notify.error({
            title: '错误',
            message: res.data.message
          });
        }

      })
    },
    handleEdit:function (index,row){
      this.list=true
      this.rowId=row.id
      this.getDepartmentDoctor(row.id)
      /*this.$router.push(
          {
            //添加需要传值到那个页面的路径
            path:'DepartmentDoctorList',
            //携带需要传递的参数
            query:{id:row.id}
          })*/
    },
    //获取用户列表
    getDepartment: function () {
      this.loading = true;
      //NProgress.start();
      findAllDepartment().then((res) => {
        if(res.data.code==='200'){
          this.users = res.data.data;
          this.loading = false;
        }else{
          this.$notify.error({
            title: '错误',
            message: res.data.message
          });
        }
        //NProgress.done();
      });
    }, formatSex: function (row, column) {
      return row.doctorSex == 1 ? '男' : row.doctorSex == 0 ? '女' : '未知';
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
    //获取用户列表
    getDepartmentDoctor: function (id) {
      this.loadings = true;
      //NProgress.start();
      findDepartmentDoctor(id).then((res) => {
        if (res.data.code === '200') {
          this.doctorList = res.data.data;
          this.loadings = false;
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
    this.getDepartment();
  }
};

</script>

<style scoped>

</style>