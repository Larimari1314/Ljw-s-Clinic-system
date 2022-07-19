<template>
  <section>
    <!--工具条-->
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
    <!--列表-->
    <el-table :data="DoctorInformation" highlight-current-row
              style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" label="序号" width="100">
      </el-table-column>
      <el-table-column prop="doctor.avatarUrl" width="150" label="医生头像" scope="scope">
        <template scope="scope">
          <img :src="DoctorInformation[scope.$index].doctor.avatarUrl" style="border-radius:50%; " width="100"
               height="100"
               alt="医生头像">
        </template>
      </el-table-column>
      <el-table-column prop="doctor.doctorName" label="医生姓名" width="130">
      </el-table-column>
      <el-table-column prop="doctor.department.departmentName" label="所在科目" width="130">
      </el-table-column>
      <el-table-column label="上午可预约时间" width="340">
        <template scope="scope">
          <!--          <el-select v-model="form" placeholder="查看预约情况" style="display: block; width: 50%;"
                               @click.native="getTimeMorning(scope.$index, scope.row,1)">
                      <el-option label="区域一" value="shanghai"></el-option>
                    </el-select>-->
          <el-dropdown :hide-on-click="false" @click.native="getTimeMorning(scope.$index, scope.row,1)">
            <span class="el-dropdown-link">
              {{ AppointmentTime }}}<i class="el-icon-caret-bottom el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                  v-for="item in morningTime"
                  :key="item.value"
                  :command="{value:item.value,label:item.label,flag:1}"
                  :disabled="item.disable || !(DoctorInformation[scope.$index].timeNumber==='1' || DoctorInformation[scope.$index].timeNumber==='3')"
              > {{ item.time }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>&nbsp;&nbsp;&nbsp;
          <el-tooltip content="关闭后医生当天上午无法就诊" placement="top">
            <!--            v-model="DrugInformation[scope.$index].exhibit"&ndash;&gt;-->
            <el-switch
                v-model="DoctorInformation[scope.$index].timeNumber==='1' || DoctorInformation[scope.$index].timeNumber==='3'"
                on-text=""
                off-text=""
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="100"
                inactive-value="0"
            >
            </el-switch>
          </el-tooltip>
          <el-button type="primary" size="mini" @click="changeSwing(scope.row,'morning')">修改状态</el-button>
        </template>
      </el-table-column>
      <el-table-column label="下午可预约时间" width="340">
        <template scope="scope">
          <!--          <el-select v-model="form" placeholder="查看预约情况" style="display: block; width: 50%;"
                               @click.native="getTimeMorning(scope.$index, scope.row,1)">
                      <el-option label="区域一" value="shanghai"></el-option>
                    </el-select>-->
          <el-dropdown :hide-on-click="false" @click.native="getTimeAfternoon(scope.$index, scope.row,1)">
            <span class="el-dropdown-link">
              {{ AppointmentTime }}}<i class="el-icon-caret-bottom el-icon--right"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                  v-for="item in afternoonTime"
                  :key="item.value"
                  :command="{value:item.value,label:item.label,flag:1}"
                  :disabled="item.disable || !(DoctorInformation[scope.$index].timeNumber==2 || DoctorInformation[scope.$index].timeNumber==3)"
              > {{ item.time }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>&nbsp;&nbsp;&nbsp;
          <el-tooltip content="关闭后医生当天下午无法就诊" placement="top">
            <!--            v-model="DrugInformation[scope.$index].exhibit"&ndash;&gt;-->
            <el-switch
                v-model="DoctorInformation[scope.$index].timeNumber==2 || DoctorInformation[scope.$index].timeNumber==3"
                on-text=""
                off-text=""
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="100"
                inactive-value="0"
            >
            </el-switch>
          </el-tooltip>
          <el-button type="primary" size="mini" @click="changeSwing(scope.row,'afternoon')">修改状态</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>
  </section>
</template>

<script>
//import NProgress from 'nprogress'
import {DoctorFindByName, editDoctor, exitNumber, findSwitch, getAfternoon, getMorningTime} from '../../api/api';

export default {
  data() {
    return {
      value1: '',
      value2:'12:30',
      app: 'true',
      morningTime: '',
      afternoonTime: '',
      AppointmentTime: '点击查看预约时间',
      fileListDoctor: [],
      searchDoctor: {
        name: ''
      },
      DoctorInformation: [{
        id: '',
        doctorName: '',
        doctorPhone: '',
        doctorPassword: '',
        avatarUrl: '',
        salary: '',
        doctorAge: 0,
        doctorSex: -1,
        doctorBirth: '',
        doctorAddr: '',
        department: undefined
      }],
      total: '',
      page: 1,
      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      editFormDoctorRules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ]
      },

      //编辑界面数据
      editFormDoctor: {},
      addLoading: false,
      //新增界面数据
      departmentOptions: [
        {
          "departmentName": "放射科",
          "id": 1
        }, {
          "departmentName": "皮肤科",
          "id": 10
        }, {
          "departmentName": "妇科",
          "id": 11
        }, {
          "departmentName": "临床科",
          "id": 2
        }, {
          "departmentName": "口腔科",
          "id": 3
        }, {
          "departmentName": "神经科",
          "id": 4
        }, {
          "departmentName": "体检科",
          "id": 5
        }, {
          "departmentName": "男科",
          "id": 6
        }, {
          "departmentName": "内科",
          "id": 7
        }, {
          "departmentName": "外科",
          "id": 8
        }, {
          "departmentName": "眼科",
          "id": 9
        }],
    }
  },
  methods: {
    handleAdd: function () {
      this.editFormVisible = true;
    },
    changeSwing(row, text) {
      let params = {id: row.id, text: text}
      exitNumber(params).then((res) => {
        if (res.data.code == '200') {
          this.$notify({
            title: '成功',
            message: res.data.message,
            type: 'success'
          });
          this.getUsers()
        }
      })
    },
    handleRemove(file, fileListDoctor) {
      console.log(file, fileListDoctor);
    },
    handlePreview(file) {
      console.log(file);
    },
    beforeRemove(file, fileListDoctor) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    passwordValidation() {
      if (this.addFormDoctor.password !== this.checkPass) {
        callback(new Error('两次密码不一致'));
      }
    },

    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    //获取用户列表
    getUsers() {
      let para = {
        page: this.page
      };
      this.listLoading = true;
      //NProgress.start();
      findSwitch(para).then((res) => {
        // let data = JSON.parse(res.data);
        // alert(data)
        if (res.data.code == '200') {
          this.total = res.data.data.total;
          this.DoctorInformation = res.data.data.list;
          /*        this.DoctorInformation.forEach((item,index, arr) => {
                    if (item.timeNumber == 1) {
                      item.timeNumber = true
                    } else {
                      item.timeNumber = false
                    }
                  });*/
          this.listLoading = false;
        } else {

        }
        //NProgress.done();
      });
    },
    findByName() {
      let para = {
        page: this.page,
        name: this.searchDoctor.name
      };
      let _this = this;
      DoctorFindByName(para).then(function (res) {
        _this.total = res.data.total;
        _this.DoctorInformation = res.data.list;
        _this.listLoading = false;
      })
    },
    getTimeMorning: function (index, row) {
      let params = {id: row.id}
      getMorningTime(params).then((res) => {
        if (res.data.code == '200') {
          this.morningTime = res.data.data
        } else {
          this.$notify({
            title: '失败',
            message: res.data.message,
            type: 'error'
          });
        }
      })
    }, getTimeAfternoon: function (index, row) {
      let params = {id: row.id}
      getAfternoon(params).then((res) => {
        if (res.data.code == '200') {
          this.afternoonTime = res.data.data
        } else {
          this.$notify({
            title: '失败',
            message: res.data.message,
            type: 'error'
          });
        }
      })
    },
    //编辑
    editSubmit: function () {
      this.$refs.editFormDoctor.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.editFormDoctor);
            editDoctor(para).then((res) => {
              this.editLoading = false;
              //NProgress.done();
              this.$message({
                message: '提交成功',
                type: res.data
              });
              this.$refs['editFormDoctor'].resetFields();
              this.editFormVisible = false;
              this.getUsers();
            });
          });
        }
      });
    }
  },
  created() {
    this.getUsers();
  }
}

</script>

<style scoped>

</style>
