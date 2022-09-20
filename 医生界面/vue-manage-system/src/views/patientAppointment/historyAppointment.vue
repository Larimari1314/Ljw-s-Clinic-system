<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item style="width: 200px">
          <el-input v-model="filters.orderId" placeholder="订单id"></el-input>
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
    <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column width="30">
      </el-table-column>
      <el-table-column prop="id" label="订单id" width="120">
      </el-table-column>
      <el-table-column prop="doctorName" label="医生姓名" width="120">
      </el-table-column>
      <el-table-column prop="patientName" label="患者姓名" width="120">
      </el-table-column>
      <el-table-column prop="departmentName" label="预约科室" width="150">
      </el-table-column>
      <el-table-column prop="reserveTime" label="预约时间" width="200" sortable>
      </el-table-column>
      <el-table-column prop="NumberReminders" label="提醒次数" min-width="120" sortable>
      </el-table-column>
      <el-table-column prop="reserveState" label="当前状态" min-width="120" sortable>
        <template v-slot="scope">
          <el-tag v-if="users[scope.$index].reserveState==1" type="success">预约结束</el-tag>
          <el-tag v-if="users[scope.$index].reserveState==2" type="primary">预约取消</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template v-slot="scope">
          <el-button v-if="users[scope.$index].reserveState==1" type="primary" round size="small"
                     @click="checkOrder(scope.$index, scope.row)">查看订单
          </el-button>
          <el-button v-if="users[scope.$index].reserveState==2" type="warning" round size="small"
                     @click="reschedule(scope.$index, scope.row)">重新预约
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total" background
                     style="float:right;">
      </el-pagination>
    </el-col>

    <el-dialog title="查看订单详情" v-model="checkOrderVisible" width="50%">
      <el-form :model="checkOrderData" ref="vForm" :rules="rules" label-position="left" label-width="80px" size="small"
               @submit.native.prevent>
        <div class="table-container">
          <table id="msg" class="msg">
            <tr>
              <td style="text-align: left">订单ID:&nbsp;&nbsp;{{ doctorInformationList.id }}</td>
              <td style="text-align: left">开药科室:&nbsp;&nbsp;{{ doctorInformationList.departmentName }}</td>
            </tr>
            <tr>
              <td style="text-align: left">最后操作时间:&nbsp;&nbsp;{{ doctorInformationList.operatingTime }}</td>
              <td style="text-align: left">开单医生:&nbsp;&nbsp; {{ doctorInformationList.doctorName }}</td>
            </tr>
            <tr>
              <td style="text-align: left">患者姓名:&nbsp;&nbsp;{{ doctorInformationList.patientName }}</td>
              <td style="text-align: left">当前状态:&nbsp;&nbsp;{{ doctorInformationList.orderState }}</td>
            </tr>
          </table>
        </div>
      </el-form>
      <el-table :data="drugList" style="width: 100%">
        <el-table-column prop="id" label="药品id" width="80"/>
        <el-table-column prop="cover" label="药品封面" width="180">
          <template v-slot="scope">
            <el-image class="table-td-thumb" :src="scope.row.cover" :preview-src-list="[scope.row.cover]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="drugName" label="药品名称" />
        <el-table-column prop="price" label="药品单价" />
        <el-table-column prop="number" label="药品数量" />
        <el-table-column prop="totalPrice" label="总价" />
      </el-table>
      <h1>总价：{{totalPrice}}</h1>
    </el-dialog>
    <el-dialog title="预约医生" v-model="reserveVisible" width="50%">
      <el-date-picker
          v-model="appointmentDate"
          type="date"
          placeholder="请选择预约日期"
          :disabled-date="disabledDate"
          :shortcuts="shortcuts"
          size="default"
          :disabled="reserveDisabled"
      />&nbsp;&nbsp;&nbsp;&nbsp;
      <el-button type="primary" @click="getTime" :disabled="appointmentDate=='' || appointmentDateDisable">确定日期</el-button>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <el-select v-model="appointmentTime" class="m-2" placeholder="选择预约时间" size="large" :disabled="appointmentTimeDisable">
        <el-option
            v-for="item in timeList"
            :key="item.value"
            :label="item.value"
            :value="item.value"
            :disabled="item.disable"
        />
      </el-select>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <el-button type="success" @click="confirmAppointment" :disabled="appointmentTime==''">确定预约</el-button>
    </el-dialog>
  </section>
</template>

<script>
import {
  checkOrderDrug,
  checkOrderInformation, confirmAppointment,
  findHistoryAppointment, getAppointmentTime, getOnDutyHours, getTotalPrice
} from '../../api/api';
import {ElMessage, ElMessageBox} from "element-plus";
import { Search } from '@element-plus/icons-vue'

export default {
  data() {
    return {
      timeList:[{
        disable:false,
        value:''
      }],
      appointmentDateDisable:false,
      appointmentTimeDisable:true,
      appointmentTime:'',
      patientId:'',
      doctorInformationList:'',
      reserveDisabled:false,
      shortcuts : [
        {
          text: '今天',
          value: new Date(),
        },
        {
          text: '明天',
          value: () => {
            const date = new Date()
            date.setTime(date.getTime() + 3600 * 1000 * 24)
            return date
          },
        },
        {
          text: '四天后',
          value: () => {
            const date = new Date()
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 4)
            return date
          },
        },
      ],
      scheduledTime:'',
      appointmentDate:'',
      reserveVisible:false,
      totalPrice:'',
      drugList:[],
      rules:{},
      checkOrderData:'',
      checkOrderVisible:false,
      doctorInformation: '',
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      filters: {
        doctorName: '',
        orderId: ''
      },
      users: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],//列表选中列
    }
  },
  methods: {
    confirmAppointment(){
      ElMessageBox.confirm(
          '确定为该患者重新预约吗?',
          '重新预约',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            let para={
              doctorid:this.doctorInformation.id,
              patientid:this.patientId,
              reservetime:new Date(this.appointmentDate.getFullYear()+"-"+(this.appointmentDate.getMonth()+1)+"-"+this.appointmentDate.getDate()+" "+this.appointmentTime)
            }
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            confirmAppointment(para,configs).then((res)=>{
              if(res.data.msgId=='C200'){
                this.reserveVisible=false;
                ElMessage({
                  type: 'success',
                  message: '重新预约成功',
                })
              }else if(res.data.msgId=='C403'){
                ElMessage({
                  type: 'info',
                  message: '预约失败,当前患者已经预约',
                })
              }else if(res.data.msgId=='C404'){
                ElMessage({
                  type: 'info',
                  message: '预约失败,医生该时间段已经预约',
                })
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '重新预约取消',
            })
          })
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
        doctorId: JSON.parse(localStorage.getItem("doctor")).id,
        orderId: this.filters.orderId
      };
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      this.listLoading = true;
      findHistoryAppointment(para,configs).then((res) => {
        this.listLoading = false;
        if (res.data.msgId === "C200") {
          this.total = res.data.result.total;
          this.users = res.data.result.list;
        } else {
          this.$message.error('数据获取失败，请稍后重试');
        }
      });
    },
    disabledDate:function (time){
         return  time.getTime()+3600 * 1000 * 24 < Date.now() ||  time.getTime() >= Date.now()+ 3600 * 1000 * 24 * 4
    },
    getTimeList(startTime,endTime,separation){
      let startSplit = startTime.trim().split(":");
      let startHour=parseInt(startSplit[0])
      let startMinute=parseInt(startSplit[1])
      let endSplit = endTime.trim().split(":");
      let endHour=parseInt(endSplit[0])
      let endMinute=parseInt(endSplit[1])
      let time=[];
      while (true){
        if(startHour<10){
          if (startMinute < 10) {
            time.push({
              value: '0'+startHour+':'+'0'+startMinute,
              disable: false
            });
          } else {
            time.push(
                {
                  value: '0'+startHour+':'+startMinute,
                  disable: false
                });
          }
        }else{
          if(startMinute<10){
            time.push({value:startHour+':'+'0'+startMinute,
              disable:false
            })
          }else{
            time.push({value:startHour+':'+startMinute,
              disable:false
            })
          }
        }
        startMinute=startMinute+separation
        if(startMinute===60){
          startHour+=1
          startMinute=0
        }
        if(startHour===endHour){
          if(startMinute>endMinute){
            break;
          }
        }
      }
      return time;
    },
    reschedule: function (index, row) {
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      getOnDutyHours(this.doctorInformation.id,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          let startTime=res.data.result.startTime
          let endTime=res.data.result.endTime
          this.timeList = this.getTimeList(startTime,endTime,30);
        }
      })
      this.appointmentDateDisable=false;
      this.reserveVisible=true;
      this.patientId=row.patientId;
      this.appointmentTime='';
      this.appointmentDate='';
      this.appointmentTimeDisable=true;
      this.reserveDisabled=false;
    },
    getTime(){
      let para={
        doctorId:this.doctorInformation.id,
        date:this.appointmentDate,
        patientId:this.patientId
      }
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      getAppointmentTime(para,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          this.reserveDisabled=true;
          this.appointmentTimeDisable=false;
          this.scheduledTime=res.data.result;
          this.appointmentDateDisable=true;
          let _this=this
          this.timeList.forEach(function (x,i,a) {
            _this.scheduledTime.forEach(function (value,index,arr) {
               if(value.reserveTime===x.value){
                 x.disable=true;
               }
            })
          })
          ElMessage({
            type: 'success',
            message: '请选择就诊时间',
          })
        }else if(res.data.msgId=='C403'){
          this.appointmentDate='';
          ElMessage({
            type: 'error',
            message: '当前患者已预约当前天数，请重新选择日期',
          })
        }
      })
    },
    checkOrder: function (index, row) {
      this.checkOrderVisible=true;
      let para = {
        reserveId: row.id,
        doctorId: this.doctorInformation.id
      }
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      checkOrderInformation(para,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          this.doctorInformationList=res.data.result
        }
      })
      checkOrderDrug(row.id,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          this.drugList=res.data.result
        }
      })
      getTotalPrice(row.id,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          this.totalPrice=res.data.result
        }
      })
    },
    selsChange: function (sels) {
      this.sels = sels;
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

