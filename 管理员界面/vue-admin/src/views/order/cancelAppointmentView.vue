<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item style="width: 200px">
          <el-input v-model="filters.patientName" placeholder="患者姓名"></el-input>
        </el-form-item>
        <el-form-item style="width: 200px">
          <el-input v-model="filters.doctorName" placeholder="医生姓名"></el-input>
        </el-form-item>
        <el-form-item style="width: 200px">
          <el-select v-model="filters.did" placeholder="请选择科室">
            <el-option
                v-for="item in departmentList"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="width: 200px">
          <el-select v-model="filters.registereId" placeholder="请选择会诊级别">
            <el-option
                v-for="item in dutyTimeLIst"
                :key="item.id"
                :label="item.value"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="id" label="订单id" width="120">
      </el-table-column>
      <el-table-column prop="doctorName" label="医生姓名" width="120">
      </el-table-column>
      <el-table-column prop="patientName" label="患者姓名" width="120">
      </el-table-column>
      <el-table-column prop="departName" label="预约科室" width="150">
      </el-table-column>
      <el-table-column prop="value" label="会诊级别" width="100">
      </el-table-column>
      <el-table-column prop="reserveTime" label="预约时间" width="150" sortable>
      </el-table-column>
      <el-table-column prop="NumberReminders" label="提醒次数" min-width="120" sortable>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button type="success" size="small" @click="handleDel(scope.$index, scope.row)">恢复预约</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量恢复</el-button>
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>
  </section>
</template>

<script>
import {
cancelAppointmentViews,
  findAllByAllDuty,
  findDepartmentCoding,
  findDutyTimeCoding,
  resumeAppointment
} from '../../api/api';

export default {
  data() {
    return {
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      dutyTimeLIst: [{coding: '', value: ''}],
      departmentList: [{id: '', name: ''}],
      allDuty: [{id: '', value: ''}],
      filters: {
        patientName: '',
        doctorName: '',
        did: '',
        registereId: ''
      },
      users: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],//列表选中列
    }
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handleExceed(files, fileListDoctor) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileListDoctor.length} 个文件`);
    }, handlePreview(file) {
      console.log(file);
    }, beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    //获取用户列表
    getUsers() {
      let para = {
        page: this.page,
        patientName: this.filters.patientName,
        doctorName: this.filters.doctorName,
        did: this.filters.did,
        registereId: this.filters.registereId
      };
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      this.listLoading = true;
      cancelAppointmentViews(para,configs).then((res) => {
        if (res.data.msgId === "C200") {
          this.total = res.data.result.total;
          this.users = res.data.result.list;
        } else {
          this.$message.error('数据获取失败，请稍后重试');
        }
        this.listLoading = false;
      });
    },
    //删除
    handleDel: function (index, row) {
      this.$confirm('确认恢复该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        //NProgress.start();
        let para = {"ids": [row.id]};
        let configs={
          headers: {
            token: sessionStorage.getItem('permissionToken')
          }
        };
        resumeAppointment(para,configs).then((res) => {
          this.listLoading = false;
          if (res.data.msgId === "C405") {
            this.$message.error('恢复失败！请稍后重试');
          } else if (res.data.msgId === "C200") {
            this.$notify.success({
              title: '成功',
              message: '恢复成功'
            });
            this.getUsers();
          }
        })
      }).catch(() => {
        this.listLoading = false;
      });
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
    //批量删除
    batchRemove: function () {
      var ids = this.sels.map(item => item.id);
      this.$confirm('确认恢复选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        let para = {"ids": ids};
        let configs={
          headers: {
            token: sessionStorage.getItem('permissionToken')
          }
        };
        resumeAppointment(para,configs).then((res) => {
          this.listLoading = false;
          if (res.data.msgId === "C405") {
            this.$message.error('恢复失败！请稍后重试');
          } else if (res.data.msgId === "C200") {
            this.$notify.success({
              title: '成功',
              message: '恢复成功'
            });
            this.getUsers();
          }
        })
      })
    }
  },
  mounted() {
    this.getUsers();
    let configs={
      headers: {
        token: sessionStorage.getItem('permissionToken')
      }
    };
    findDepartmentCoding(configs).then((res) => {
      if (res.data.msgId === "C200") {
        this.departmentList = res.data.result
      } else {
        this.$message.error('数据初始化失败，请稍后重试');
      }
    });
    findDutyTimeCoding(configs).then((res) => {
      if (res.data.msgId === "C200") {
        this.dutyTimeLIst = res.data.result
      } else {
        this.$message.error('数据初始化失败，请稍后重试');
      }
    })
    findAllByAllDuty(configs).then((res) => {
      if (res.data.msgId === "C200") {
        this.allDuty = res.data.result
      } else {
        this.$message.error('数据初始化失败，请稍后重试');
      }
    })
  }
}

</script>

<style scoped>

</style>
