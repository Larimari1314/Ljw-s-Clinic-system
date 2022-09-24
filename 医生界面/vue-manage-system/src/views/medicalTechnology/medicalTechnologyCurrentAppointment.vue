<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item style="width: 200px">
          <el-input v-model="filters.checkParts" placeholder="检查部位"></el-input>
        </el-form-item>
        <el-form-item style="width: 200px">
          <el-input v-model="filters.patientName" placeholder="患者姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers" icon="el-icon-search">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange">
      <el-table-column prop="id" label="预约id">
      </el-table-column>
      <el-table-column prop="patientName" label="患者姓名" >
      </el-table-column>
      <el-table-column prop="reserveId" label="关联预约" >
      </el-table-column>
      <el-table-column prop="reserveTime" label="预约时间"  width="180" sortable>
      </el-table-column>
      <el-table-column prop="queueNumber" label="当前编号" sortable>
      </el-table-column>
      <el-table-column prop="checkParts" label="检查患处" sortable>
      </el-table-column>
      <el-table-column prop="status" label="当前状态" >
        <template v-slot="scope">
          <el-tag v-if="users[scope.$index].status==='未支付'" type="warning">未支付</el-tag>
          <el-tag v-if="users[scope.$index].status==='已支付'" type="success">已支付</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template v-slot="scope">
          <el-tooltip
              class="box-item"
              effect="dark"
              content="为该患者编写报告"
              placement="left-start">
            <el-button  type="primary" round size="small" :disabled="users[scope.$index].status==='未支付'" @click="editAppointmentPatient(scope.$index, scope.row)" icon="el-icon-edit" circle>
            </el-button>
          </el-tooltip>
          <el-tooltip
              class="box-item"
              effect="dark"
              content="撤销预约患者"
              placement="top">
            <el-button type="danger" round size="small" :disabled="users[scope.$index].status==='已支付'" @click="cancelAppointment(scope.$index, scope.row)" icon="el-icon-delete" circle>
            </el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total" background
                     style="float:right;">
      </el-pagination>
    </el-col>

  </section>
</template>

<script>
import {
  addDrugByPatient,
  cancelAppointment,
  checkOrderDrug,
  checkOrderInformation,
  confirmAppointment,
  endAppointmentByDoctor,
  findAllByAllDuty,
  findAllReserveMedicalTechnology,
  findCurrentAppointment,
  findDepartmentCoding,
  findDrugByDrugName,
  findDutyTimeCoding,
  getAppointmentTime,
  getOnDutyHours,
  getTotalPrice,
  judgmentStatusPaid,
  medicalTechnologyCancelAppointment,
  remindPatientNumber,
  removeDrug,
  searchMedicinesRemotely
} from '../../api/api';
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      dialogVisible: false,
      filters: {
      },
      users: [],
      totalDrug:0,
      total: 0,
      page: 1,
      pageDrug:1,
      listLoading: false,
      sels: [],//列表选中列
    }
  },
  methods: {
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    //获取用户列表
    getUsers() {
      let para = {
        page: this.page,
        patientName: this.filters.patientName,
        checkParts: this.filters.checkParts
      };
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      this.listLoading = true;
      findAllReserveMedicalTechnology(para,configs).then((res) => {
        this.listLoading = false;
        if (res.data.msgId === "C200") {
          this.total = res.data.result.total;
          this.users = res.data.result.list;
        } else {
          this.$message.error('数据获取失败，请稍后重试');
        }
      });
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
    editAppointmentPatient(index,row){
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      // 服务器判断当前用户的状态是否是已支付
      judgmentStatusPaid(row.id,configs).then((res)=>{
        if(res.data.msgId==='C200'){
          //通过检测，将界面转移到新的界面中
          window.open("http://localhost:3000/#/diagnoseReport/"+row.id,"_blank");
        }else{
          this.$message.error('订单状态异常，请刷新界面后重试！');
        }
      })
    },cancelAppointment(index,row){
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      ElMessageBox.confirm(
          '确定撤销该患者预约，并进行退款?',
          '撤销预约',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            medicalTechnologyCancelAppointment(row.id,configs).then((res)=>{
              if(res.data.msgId==='C200'){
                ElMessage.success('撤销成功！')
                this.getUsers();
              }else if(res.data.msgId==='C404'){
                ElMessage.error('订单状态异常，请刷新后重试！')
              }else{
                ElMessage.error('撤销失败请稍后重试！')
              }
            })
          })
    }
  },
  mounted() {
    this.doctorInformation = JSON.parse(localStorage.getItem("doctor"));
    this.getUsers();
  }
}

</script>

<style scoped>
.demo-date-picker {
  display: flex;
  width: 100%;
  padding: 0;
  flex-wrap: wrap;
}
.demo-date-picker .block {
  padding: 30px 0;
  text-align: center;
  border-right: solid 1px var(--el-border-color);
  flex: 1;
}
.demo-date-picker .block:last-child {
  border-right: none;
}
.demo-date-picker .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
.confirm > span {
  flex: 1;
  height: 50px;
  line-height: 50px;
  font-size: 16px;
  text-align: center;
}

.confirm > span:nth-child(1) {
  color: red;
}

.confirm > span:nth-child(2) {
  border-left: 1px solid #eee;
}


.title > p {
  height: 30px;
  line-height: 40px;
  text-align: center;
  font-size: 18px;
  font-weight: bold;

}


.content > table {
  padding: 0 10px 10px 10px;
  margin: 0 auto;
  width: 100%;

}

.msg {
  width: 100%;
  height: 150px;
  text-align: center;
  table-layout: fixed
}

</style>

