<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="科室名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="department" highlight-current-row v-loading="listLoading"
              style="width: 100%;">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="cover" label="科室图片" width="320">
        <template v-slot="scope">
          <a href="http://192.168.1.106:3088/#/appointmentDoctor">
            <img :src="department[scope.$index].cover" style="border-radius:10%; " width="250" height="150"
                 alt="科室封面">
          </a>
        </template>
      </el-table-column>
      <el-table-column prop="introduce" label="科室介绍" width="500">
      </el-table-column>
      <el-table-column prop="name" label="科室名称" width="200">
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template v-slot="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)" type="primary">选择该科室</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="5" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-steps :active="appointmentActive" finish-status="success">
        <el-step title="选择医生"/>
        <el-step title="选择预约日期" />
        <el-step title="选择预约时间，确定预约" />
      </el-steps>
      <br/><br/>
      <el-tabs type="border-card" v-model="activeTab">
        <el-tab-pane label="选择医生" :disabled="top1" name="chooseDoctor" >
          <el-table :data="doctorList" highlight-current-row v-loading="listLoading"
                    style="width: 100%;">
            <el-table-column prop="name" label="姓名">
            </el-table-column>
            <el-table-column prop="age" label="年龄">
            </el-table-column>
            <el-table-column prop="sex" label="性别">
            </el-table-column>
            <el-table-column prop="register" label="挂号级别">
            </el-table-column>
            <el-table-column prop="duty" label="班次"  >
            </el-table-column>
            <el-table-column label="操作" width="160">
              <template v-slot="scope">
                <el-button size="small" @click="chooseDoctor(scope.$index, scope.row)" type="primary">选择医生</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="选择预约日期" :disabled="top2" name="appointmentDate">
          <el-date-picker
              v-model="appointmentDate"
              type="date"
              placeholder="请选择预约日期"
              :disabled-date="disabledDate"
              :shortcuts="shortcuts"
              size="default"
              :disabled="reserveDisabled"
          />&nbsp;&nbsp;&nbsp;&nbsp;
          <el-button type="primary"  @click="getTime"  :disabled="appointmentDate==''" >确定预约时间</el-button>
        </el-tab-pane>
        <el-tab-pane label="选择预约时间" :disabled="top3" name="appointmentTime">
          <el-select v-model="appointmentTime" class="m-2" placeholder="选择预约时间" size="large" :disabled="appointmentTimeDisable">
            <el-option
                v-for="item in timeList"
                :key="item.value"
                :label="item.value"
                :value="item.value"
                :disabled="item.disable"
            />
          </el-select>&nbsp;&nbsp;&nbsp;&nbsp;
          <el-button type="success" @click="confirmAppointment" :disabled="appointmentTime==''">确定预约时间</el-button>
        </el-tab-pane>
      </el-tabs>
      <br/><br/>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false;">取消</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import {
  confirmAppointment,
  findAllDepartment, getAppointmentInformation, getAppointmentTime, getDoctorListByDepartment, getOnDutyHours,
} from '../../api/api';
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      appointmentTimeDisable:true,
      appointmentTime:'',
      reserveDisabled:true,
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
      appointmentDate:'',
      activeTab:'chooseDoctor',
      top1:true,
      top2:true,
      top3:true,
      top4:true,
      doctorList:[],
      appointmentActive:0,
      dialogVisible: false,
      category: '',
      options: [],
      filters: {
        name: ''
      },
      department: [],
      total: 0,
      page: 1,
      listLoading: false,
      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      nowDoctorChoose:[],
      timeList:[{
        disable:false,
        value:''
      }],
      patientInformation:JSON.parse(localStorage.getItem('patient'))
    }
  },
  methods: {
    confirmAppointment(){
      ElMessageBox.confirm(
          '确定预约吗?',
          '预约',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            this.appointmentActive=3;
            let para={
              doctorid:this.nowDoctorChoose.id,
              patientid:this.patientInformation.id,
              reservetime:new Date(this.appointmentDate.getFullYear()+"-"+(this.appointmentDate.getMonth()+1)+"-"+this.appointmentDate.getDate()+" "+this.appointmentTime)
            }
            let that=this;
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            getAppointmentInformation(para,configs).then((res)=>{
              if(res.data.msgId==='C200'){
                that.$router.replace({path:'/pay/'+res.data.result})
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '预约取消',
            })
          })
    },
    getTime(){
      let para={
        doctorId:this.nowDoctorChoose.id,
        date:this.appointmentDate,
        patientId:this.patientInformation.id
      }
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      getAppointmentTime(para,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          this.appointmentActive=2;
          this.top3=false;
          this.top2=true;
          this.appointmentTimeDisable=false;
          this.activeTab='appointmentTime';
          this.reserveDisabled=true;
          this.scheduledTime=res.data.result;
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
    chooseDoctor(index,row){
      this.appointmentDate='';
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      getOnDutyHours(row.id,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          let startTime=res.data.result.startTime
          let endTime=res.data.result.endTime
          this.timeList = this.getTimeList(startTime,endTime,30);
        }
      })
      //记录当前医生信息，并管理top1界面将界面转到top2界面，并将进度条调整
      this.reserveDisabled=false;
      this.activeTab='appointmentDate';
      this.nowDoctorChoose=row;
      this.top1=true;
      this.top2=false;
      this.appointmentActive=1;
    },
    next(){
      if (this.appointmentActive++ > 3)this.appointmentActive = 0
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    getUsers() {
      let para = {
        page: this.page,
        name: this.filters.name
      };
      this.listLoading = true;
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      findAllDepartment(para,configs).then((res) => {
        if (res.data.msgId == 'C200') {
          this.department = res.data.result.list;
          this.total = res.data.result.total
          this.listLoading = false;
        }
      })
    },
    handleEdit: function (index, row) {
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      getDoctorListByDepartment(row.id,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          this.doctorList=res.data.result
        }
      })
      this.appointmentTime='';
      this.reserveDisabled=true;
      this.activeTab='chooseDoctor';
      this.top1=false;
      this.top2=true;
      this.top3=true;
      this.top4=true;
      this.appointmentActive=0;
      this.nowDoctorChoose=null;
      this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
    },
  },
  mounted() {
    this.getUsers();
  }
}

</script>

<style scoped>

</style>
