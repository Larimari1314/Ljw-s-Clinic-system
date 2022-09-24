<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="化验查询"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.medicalId" clearable placeholder="选择检查项">
            <el-option
                v-for="item in medicalList"
                :key="item.id"
                :label="item.value"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table v-loading="listLoading" :data="department" highlight-current-row
              style="width: 100%;">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column label="检查项封面" prop="cover" width="320">
        <template v-slot="scope">
          <a href="http://192.168.1.106:3088/#/medicalTechnologyAppointment">
            <img :src="department[scope.$index].introduce" alt="科室封面" height="150" style="border-radius:10%; "
                 width="250">
          </a>
        </template>
      </el-table-column>
      <el-table-column label="检查分类" prop="projectName">
      </el-table-column>
      <el-table-column label="检查项名称" prop="technologyname">
      </el-table-column>
      <el-table-column label="检查科室" prop="name">
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button size="small" type="primary" @click="chooseThisMedical(scope.$index, scope.row)">选择该检查项
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination :page-size="5" :total="total" layout="prev, pager, next" style="float:right;"
                     @current-change="handleCurrentChange">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog v-model="editFormVisible" :close-on-click-modal="false" title="预约检查">
      <el-steps :active="active" finish-status="success">
        <el-step title="选择预约检查项" />
        <el-step title="选择预约医生" />
        <el-step title="确定预约时间" />
        <el-step title="确定付款" />
      </el-steps>
      <br/><br/>
      <el-tabs v-model="activeTap" class="chooseMedicalClass" @tab-click="handleClick">
        <el-tab-pane label="选择预约检查项" name="AppointmentCheckItem" :disabled="top1">
          <el-table :data="medicalClassificationList" stripe style="width: 100%">
            <el-table-column prop="id" label="检查项id"/>
            <el-table-column prop="name" label="检查项名称"/>
            <el-table-column prop="money" label="检查价格"/>
            <el-table-column prop="technologyname" label="检查项分类"/>
            <el-table-column label="操作">
              <template v-slot="scope">
                <el-button size="small" type="primary" @click="chooseMedicalClassification(scope.$index, scope.row)">选择检查项
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="选择预约医生" name="chooseDoctorAppointment" :disabled="top2">
          <el-table :data="doctorList" stripe style="width: 100%">
            <el-table-column prop="name" label="医生姓名"/>
            <el-table-column prop="dutyTime" label="医生班次"/>
            <el-table-column prop="RegisName" label="医生挂号级别"/>
            <el-table-column prop="price" label="医生挂号金额"/>
            <el-table-column label="操作">
              <template v-slot="scope">
                <el-button size="small" type="primary" @click="chooseDoctor(scope.$index, scope.row)">选择该医生
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="确定预约时间" name="determineAppointmentTime" :disabled="top3">
          <el-alert title="系统会将此次医技预约自动绑定最近的一次未完成预约，如果想绑定其他预约请点击下面选择其他预约进行绑定" type="warning" />
          <el-collapse v-model="activeNames" @change="handleChange">
            <el-collapse-item title="选择您的预约" name="1" :disabled="appointmentDropDownBox">
              <el-table :data="appointmentLists" style="width: 100%">
                <el-table-column prop="id" label="预约id"/>
                <el-table-column prop="departmentName" label="预约科室"/>
                <el-table-column prop="doctorName" label="预约医生" />
                <el-table-column prop="reserveTime" label="预约时间" width="195px"/>
                <el-table-column label="绑定">
                  <template v-slot="scope">
                    <el-button size="small" type="primary" :disabled="scope.row.id===this.medicalClassInformation.reserveId" @click="queryBindingReserve(scope.$index, scope.row)">绑定该预约记录
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-collapse-item>
          </el-collapse>
          <el-form :model="dutyTimelIstFormData" ref="vForm" :rules="rules" label-position="left" label-width="120px"
                   size="default" @submit.native.prevent>
            <el-form-item label="选择预约日期" prop="date" class="required">
              <el-date-picker
                  v-model="dutyTimelIstFormData.date"
                  type="date"
                  placeholder="请选择预约日期"
                  :disabled-date="disabledDate"
                  size="default"
              />
            </el-form-item>
            <el-form-item label="选择预约时间" prop="time" class="required">
              <el-select v-model="dutyTimelIstFormData.time" class="full-width-input" clearable  placeholder="选择时间">
                <el-option v-for="(item, index) in dutyTime" :key="index" :label="item.value" :value="item.id"
                           :disabled="item.disabled"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="confirmAppointment()" :disabled="confirmAppointmentViu">确定预约</el-button>
              <el-button type="success" @click="ConfirmPaymentPage" :disabled="ConfirmPaymentPageViu">前往付款</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="确定付款" name="paying" :disabled="top4">
          <div class="table-container">
            <table id="msg" class="msg">
              <tr>
                <td style="text-align: left">预约ID:&nbsp;&nbsp;{{ appointmentInformation.id }}</td>
                <td style="text-align: left">预约医生:&nbsp;&nbsp;{{ appointmentInformation.name }}</td>
              </tr>
              <tr>
                <td style="text-align: left">预约医师级别:&nbsp;&nbsp;{{ appointmentInformation.doctorRegis }}</td>
                <td style="text-align: left">总金额:&nbsp;&nbsp;{{ appointmentInformation.totalMoney }}</td>
              </tr>
              <tr>
                <td style="text-align: left">预约医师金额:&nbsp;&nbsp;{{ appointmentInformation.regisMoney }}</td>
                <td style="text-align: left">检查项目金额:&nbsp;&nbsp; {{ appointmentInformation.medicalMoney }}</td>
              </tr>
              <tr>
                <td style="text-align: left">预约时间:&nbsp;&nbsp;{{ appointmentInformation.reserveTime }}</td>
                <td style="text-align: left">下单时间:&nbsp;&nbsp;{{ appointmentInformation.appointmentTime }}</td>
              </tr>
            </table>
          </div>
          <el-button @click="considerPaying">考虑一下</el-button>
          <el-button type="success" @click="payingNow">确定付款</el-button>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </section>
</template>

<script>
import {
  confirmPayment,
  findDoctorDutyTimeListByDoctorId,
  findDoctorListByDepartmentId,
  findMedicalTechnologyList,
  getMedicalClassificationList,
  getMedicalProjectNameList, medicalTechnologyAppointments, payingEncryption, queryBindingAppointment,
} from '../../api/api';
import {ElMessage, ElMessageBox, ElNotification} from "element-plus";

export default {
  data() {
    return {
      appointmentInformation:[],
      appointmentId:'',
      confirmAppointmentViu:false,
      ConfirmPaymentPageViu:true,
      appointmentLists:[],
      appointmentDropDownBox:true,
      activeNames:'0',
      dutyTimelIstFormData:{},
      dutyTime:[],
      medicalClassInformation:{},
      medicalClassificationList:[],
      active:0,
      activeTap : 'chooseMedicalClass',
      top1:true,
      top2:true,
      top3:true,
      top4:true,
      editFormVisible:false,
      listLoading: false,
      medicalList: [],
      doctorList: [],
      appointmentActive: 0,
      dialogVisible: false,
      category: '',
      options: [],
      filters: {},
      department: [],
      total: 0,
      page: 1,
      patientInformation: JSON.parse(localStorage.getItem('patient')),
      rules: {
        date: [{
          required: true,
          message: '预约日期不可为空',
        }],
        time: [{
          required: true,
          message: '预约时间不可为空',
        }],
      }
    }
  },
  methods: {
    payingNow(){
      let configs = {
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      payingEncryption(this.appointmentId,configs).then((res)=>{
        let that=this
        if(res.data.msgId==='C200'){
          that.$router.replace({path:'/payMedicalTechnology/'+res.data.result})
        }
      })
    },
    considerPaying(){
      this.editFormVisible=false;
      ElNotification({
        title: '注意',
        message: '付款可在未支付订单查看',
        type: 'warning',
      })
    },
    ConfirmPaymentPage(){
      this.top3=true;
      this.top4=false;
      this.activeTap='paying';
      this.active=3;
      // alert(this.appointmentId)
      let configs = {
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      //根据编号获取当前支付信息
      confirmPayment(this.appointmentId,configs).then((res)=>{
        if(res.data.msgId==='C200'){
          this.appointmentInformation=res.data.result
        }
      })
    },
    handleChange (val)  {
      console.log(val)
    },
    confirmAppointment(){
      this.$refs["vForm"].validate((valid) => {
        if(valid){
          ElMessageBox.confirm(
              '确定预约此时间段吗?',
              '选择预约',
              {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
              }
          )
              .then(() => {
                let para={
                  medicalid:this.medicalClassInformation.medicalId,
                  doctorid:this.medicalClassInformation.doctorId,
                  reserveid:this.medicalClassInformation.reserveId,
                  reservedate:this.dutyTimelIstFormData.date,
                  reservetime:this.dutyTimelIstFormData.time
                }
                let configs = {
                  headers: {
                    token: sessionStorage.getItem('permissionToken')
                  }
                };
                medicalTechnologyAppointments(para,configs).then((res)=>{
                  if(res.data.msgId==='C200'){
                    //预约成功
                    this.confirmAppointmentViu=true;
                    this.ConfirmPaymentPageViu=false;
                    this.appointmentId=res.data.result.id;
                    if(res.data.result.noSystemChanges!=null){
                      ElMessage({
                        message: '患者预约成功，预约编号为'+res.data.result.queueNumber,
                        type: 'success',
                      })
                    }else{
                      ElMessage({
                        message: '患者预约成功，因为选择班次医生预约已满，系统自动为您分配'+res.data.result.systemChanges+'班次，当前班次您的预约编号为'+res.data.result.queueNumber,
                        type: 'waring',
                      })
                    }

                  }else if(res.data.msgId==='C203'){
                    //订单存在
                    ElMessage({
                      message: '患者当天已经预约该项目，或者关联订单已经预约此项目',
                      type: 'error',
                    })
                  }else if(res.data.msgId==='C403'){
                    //医生班次满了
                    ElMessage({
                      message: '医生当天班次已满，请用户选择其他天',
                      type: 'error',
                    })
                  }else if(res.data.msgId==='C404'){
                    //医生不负责该数据
                    ElMessage({
                      message: '基础数据出错，医生不负责该检查项目',
                      type: 'error',
                    })
                  }else if(res.data.msgId==='C405'){
                    //预约失败
                    ElMessage({
                      message: '预约失败，如出现多次此情况，请及时联系管理员',
                      type: 'error',
                    })
                  }
                })
              })
        }
      })
    },
    queryBindingReserve(index,row){
      this.medicalClassInformation.reserveId=row.id;
    },
    disabledDate:function (time){
      return  time.getTime()+3600 * 1000 * 24 < Date.now() ||  time.getTime() >= Date.now()+ 3600 * 1000 * 24 * 4
    },
    chooseDoctor(index,row){
      ElMessageBox.confirm(
          '当前选择'+row.name+'，该医生挂号级别为'+row.RegisName+'，确定选择吗?',
          '选择预约',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            //将医生id记录
            this.medicalClassInformation.doctorId=row.id;
            //接收时间表
            let configs = {
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            findDoctorDutyTimeListByDoctorId(row.id,configs).then((res)=>{
              this.dutyTime=res.data.result;
              this.active=2;
              this.top2=true;
              this.top3=false;
              this.activeTap='determineAppointmentTime';
            })
            //提示用户选择了什么预约进行绑定
            queryBindingAppointment(configs).then((res)=>{
              if(res.data.msgId==='C404'){
                ElMessage({
                  message: '未查询到患者的预约记录，系统将不会为此绑定预约.',
                  type: 'warning',
                })
              }else if(res.data.msgId==='C200'){
                this.appointmentDropDownBox=false;
                this.medicalClassInformation.reserveId=res.data.result[0].id
                this.appointmentLists=res.data.result;
                ElMessage({
                  message: '系统自动为患者绑定预约编号为'+this.medicalClassInformation.reserveId+'的记录，如若修改请查看页面提示进行修改',
                  type: 'success',
                })
              }
            })
          })
    },
    chooseMedicalClassification(index,row){
      ElMessageBox.confirm(
          '当前选择'+row.name+'，它的价格为'+row.money+'元，确定选择吗?',
          '选择预约',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            /*记录选择的规格*/
            this.medicalClassInformation.medicalId=row.id;
            /*根据科室id查询医生列表*/
            let configs = {
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            let para={
              did:this.medicalClassInformation.did,
              medicalId:this.medicalClassInformation.medicalIds
            }
            findDoctorListByDepartmentId(para,configs).then((res)=>{
              if(res.data.msgId==='C200'){
                //通过验证后赋值列表
                this.doctorList=res.data.result;
                //跳转界面
                this.active=1;
                this.top1=true;
                this.top2=false;
                this.activeTap='chooseDoctorAppointment';
              }
            })
            // alert(this.medicalClassInformation.did)
          })
    },
    chooseThisMedical(index,row){
      this.medicalClassInformation={}
      this.medicalClassInformation.medicalIds=row.id;
      let configs = {
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      getMedicalClassificationList(row.id,configs).then((res)=>{
        if(res.data.msgId==='C200'){
          this.medicalClassInformation.did=row.did;
          this.medicalClassificationList=res.data.result
          this.editFormVisible=true;
        }
      })
      //初始化数据
      this.appointmentId='';
      this.confirmAppointmentViu=false;
      this.ConfirmPaymentPageViu=true;
      this.appointmentDropDownBox=true;
      this.dutyTimelIstFormData={};
      this.dutyTime=[];
      this.top1=false;
      this.top2=true;
      this.top3=true;
      this.top4=true;
      this.activeTap='AppointmentCheckItem';
      this.active=0;
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    getUsers() {
      let para = {
        page: this.page,
        name: this.filters.name,
        medicalId: this.filters.medicalId
      };
      // alert(JSON.stringify(para))
      this.listLoading = true;
      let configs = {
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      findMedicalTechnologyList(para, configs).then((res) => {
        if (res.data.msgId == 'C200') {
          this.department = res.data.result.list;
          this.total = res.data.result.total
          this.listLoading = false;
        }
      })
    }
  },
  mounted() {
    this.getUsers();
    let configs = {
      headers: {
        token: sessionStorage.getItem('permissionToken')
      }
    };
    getMedicalProjectNameList(configs).then((res) => {
      if (res.data.msgId === 'C200') {
        this.medicalList = res.data.result;
      }
    })
  }
}

</script>

<style scoped>
.msg {
  width: 100%;
  height: 150px;
  text-align: center;
  table-layout: fixed
}

</style>
