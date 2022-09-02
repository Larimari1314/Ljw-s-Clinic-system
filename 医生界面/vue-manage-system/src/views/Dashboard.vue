<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card shadow="hover" class="mgb20" style="height:252px;">
          <div class="user-info">
            <img :src="user.avatar" class="user-avator" alt/>
            <div class="user-info-cont">
              <div class="user-info-name">{{ user.name }}</div>
              <div>工资：<b>{{ user.salary }}</b></div>
            </div>
          </div>
          <div class="user-info-list">
            登录时间：
            <span>{{ loginTime }}</span>
          </div>
        </el-card>
        <el-card shadow="hover" style="height:320px;">
          <template #header>
            <div class="clearfix">
              <span>预约详情图状</span>
            </div>
          </template>
          预约完成
          <el-progress :percentage="appointmentNumber.overAppointmentNumber" color="#42b983"></el-progress>
          预约取消
          <el-progress :percentage="appointmentNumber.appointmentNumber" color="#f1e05a"></el-progress>
          全部预约
          <el-progress :percentage="appointmentNumber.allAppointmentNumber"></el-progress>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-row :gutter="20" class="mgb20">
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-1">
                <i class="el-icon-user-solid grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ appointmentNumber.overAppointmentNumber }}</div>
                  <div>预约完成</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-2">
                <i class="el-icon-user-solid grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ appointmentNumber.appointmentNumber }}</div>
                  <div>预约取消</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card shadow="hover" :body-style="{ padding: '0px' }">
              <div class="grid-content grid-con-3">
                <i class="el-icon-s-goods grid-con-icon"></i>
                <div class="grid-cont-right">
                  <div class="grid-num">{{ appointmentNumber.allAppointmentNumber }}</div>
                  <div>全部预约</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-card shadow="hover" style="height:470px;">
          <template #header>
            <el-scrollbar height="450px">
              <p v-for="item in appointmentList" :key="item" class="scrollbar-demo-item">
                <span style="text-align: left">
                  患者：{{ item.patientName }}，将于{{ item.reserveTime }}进行预约，请医生提前准备 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </span>
                <span style="text-align: right"><el-link type="info" href="http://localhost:3000/#/currentAppointment">前往就诊界面</el-link></span>
              </p>
            </el-scrollbar>
          </template>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {findCurrentAppointment, getAppointmentNumber} from "../api/api";

export default {
  data() {
    return {
      loginTime: localStorage.getItem("loginTime"),
      user: JSON.parse(localStorage.getItem("doctor")),
      appointmentNumber: [],
      appointmentList: []
    }
  },
  methods: {}, mounted() {
    getAppointmentNumber(this.user.id).then((res) => {
      if (res.data.msgId == 'C200') {
        this.appointmentNumber = res.data.result
      }
    })

    let para = {
      page: 1,
      patientName: '',
      doctorId: JSON.parse(localStorage.getItem("doctor")).id,
      orderId: ''
    }
    findCurrentAppointment(para).then((res) => {
      if (res.data.msgId === "C200") {
        this.appointmentList = res.data.result.list
      }
    })
  }
};
</script>

<style scoped>
.scrollbar-demo-item {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 50px;
  margin: 10px;
  text-align: left;
  border-radius: 4px;
  background: var(--el-color-primary-light-9);
  color: var(--el-color-primary);
}

.el-row {
  margin-bottom: 20px;
}

.grid-content {
  display: flex;
  align-items: center;
  height: 100px;
}

.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: #999;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}

.grid-con-icon {
  font-size: 50px;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  color: #fff;
}

.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 20px;
}

.user-avator {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

.user-info-cont {
  padding-left: 50px;
  flex: 1;
  font-size: 14px;
  color: #999;
}

.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}

.user-info-list {
  font-size: 14px;
  color: #999;
  line-height: 25px;
}

.user-info-list span {
  margin-left: 70px;
}

.mgb20 {
  margin-bottom: 20px;
}

.todo-item {
  font-size: 14px;
}

.todo-item-del {
  text-decoration: line-through;
  color: #999;
}

.schart {
  width: 100%;
  height: 300px;
}
</style>
