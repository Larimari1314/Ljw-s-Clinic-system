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
      <el-table-column width="50" type="selection" >
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
      <el-table-column prop="NumberReminders" label="提醒次数" width="120" sortable>
      </el-table-column>
      <el-table-column width="75">
        <template v-slot="scope">
        <el-tooltip
        class="box-item"
        effect="dark"
        content="提醒患者"
        placement="top">
        <el-button icon="el-icon-plus" @click="remindPatient(scope.$index, scope.row)" circle/>
        </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="reserveState" label="当前状态" width="140">
        <template v-slot="scope">
          <el-tag v-if="users[scope.$index].reserveState==0" type="success">已预约未查看</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template v-slot="scope">
          <el-tooltip
              class="box-item"
              effect="dark"
              content="查看患者医技报告单"
              placement="left-start">
            <el-button  round size="small" :disabled="users[scope.$index].COUNT==0" @click="viewTheReport(scope.$index, scope.row)" icon="el-icon-view" circle>
            </el-button>
          </el-tooltip>
          <el-tooltip
              class="box-item"
              effect="dark"
              content="配药"
              placement="top">
          <el-button  type="primary" round size="small" @click="editAppointmentPatient(scope.$index, scope.row)" icon="el-icon-edit" circle>
          </el-button>
          </el-tooltip>
          <el-tooltip
              class="box-item"
              effect="dark"
              content="撤销预约患者"
              placement="top">
          <el-button type="danger" round size="small" @click="cancelAppointment(scope.$index, scope.row)" icon="el-icon-delete" circle>
          </el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0" size="small" round>批量撤销</el-button>
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total" background
                     style="float:right;">
      </el-pagination>
    </el-col>


    <el-dialog title="当前患者报告" v-model="viewReportDialog" width="60%">
      <el-tabs v-model="activeTopName" class="demo-tabs" @tab-click="handleClick">
        <el-tab-pane label="当前订单绑定报告" name="first" :disabled="top1">
          <el-table :data="medicalTableData" style="width: 100%">
            <el-table-column prop="id" label="报告id" />
            <el-table-column prop="name" label="就诊医师"  />
            <el-table-column prop="reserveTime" label="预约时间" />
            <el-table-column prop="totalMoney" label="总金额" />
            <el-table-column prop="status" label="订单状态">
              <template v-slot="scope">
                <el-tag class="ml-2" type="success" v-if="medicalTableData[scope.$index].statusCoding==='GST03'">已完成</el-tag>
                <el-tag class="ml-2" type="warning"  v-if="medicalTableData[scope.$index].statusCoding==='GST01'">订单未付款</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template v-slot="scope">
                <el-button type="success" size="small" @click="viewReport(scope.$index, scope.row)" icon="el-icon-view" :disabled="medicalTableData[scope.$index].statusCoding==='GST01'">查看报告</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="历史报告" name="second" :disabled="top2">
          <el-table :data="medicalTableDataHistory" style="width: 100%">
            <el-table-column prop="id" label="报告id" />
            <el-table-column prop="name" label="就诊医师"  />
            <el-table-column prop="reserveTime" label="预约时间" />
            <el-table-column prop="totalMoney" label="总金额" />
            <el-table-column prop="status" label="订单状态">
              <template v-slot="scope">
                <el-tag class="ml-2" type="success" v-if="medicalTableDataHistory[scope.$index].statusCoding==='GST03'">已完成</el-tag>
                <el-tag class="ml-2" type="warning"  v-if="medicalTableDataHistory[scope.$index].statusCoding==='GST01'">订单未付款</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template v-slot="scope">
                <el-button type="success" size="small" @click="viewReport(scope.$index, scope.row)" icon="el-icon-view" :disabled="medicalTableDataHistory[scope.$index].statusCoding==='GST01'">查看报告</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>


    <el-dialog title="为预约患者配药" v-model="viewAppointmentPatients" width="60%">
      <el-select
          v-model="drugName"
          filterable
          remote
          placeholder="请输入关键词"
          :remote-method="remoteMethod"
          :loading="loading"
      >
        <el-option
            v-for="item in options"
            :key="item.drugname"
            :label="item.drugname"
            :value="item.drugname"
        >
        </el-option>
      </el-select>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <el-button type="primary" @click="getDrugList" plain>查询药品</el-button>
      <el-table :data="drugList" style="width: 100%">
        <el-table-column prop="id" label="药品id" width="80"/>
        <el-table-column prop="cover" label="药品封面" width="180">
          <template v-slot="scope">
            <el-image class="table-td-thumb" :src="scope.row.cover" :preview-src-list="[scope.row.cover]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="drugname" label="名称" width="100"/>
        <el-table-column prop="price" label="单价" width="80" />
        <el-table-column prop="number" label="药品数量" width="80"/>
        <el-table-column label="操作">
          <template v-slot="scope">
          <el-input-number v-model="drugList[scope.$index].enable" :min="0" :max="drugList[scope.$index].number" @change="handleChange" style="width: 150px"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <el-tooltip
                class="box-item"
                effect="dark"
                content="添加药品"
                placement="top">
              <el-button  type="primary" icon="el-icon-plus" @click="addDrugReserve(scope.$index, scope.row)" circle></el-button></el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <el-col :span="24" class="toolbar">
        <el-pagination layout="prev, pager, next" @current-change="handleCurrentChangeDrug" :page-size="5" :total="totalDrug" background
                       style="float:right;">
        </el-pagination>
      </el-col>
      <br/><br/>
      <h1 style="text-align: center;color: #20a0ff">患者药品</h1>
      <el-table :data="newDrugList" style="width: 100%">
        <el-table-column prop="id" label="药品id" width="80"/>
        <el-table-column prop="cover" label="药品封面" width="200">
          <template v-slot="scope">
            <el-image class="table-td-thumb" :src="scope.row.cover" :preview-src-list="[scope.row.cover]">
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="drugName" label="药品名称" width="120" />
        <el-table-column prop="price" label="药品单价" />
        <el-table-column prop="number" label="药品数量" />
        <el-table-column prop="totalPrice" label="总价" />
        <el-table-column  label="操作" width="220">
          <template v-slot="scope">
          <el-tooltip
              class="box-item"
              effect="dark"
              content="删除药品"
              placement="top">
            <el-button type="danger" icon="el-icon-delete" @click="removeDrug(scope.$index, scope.row)" circle /></el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <h1>总价：{{totalPrice}}</h1>
      <br/>
      <br/>
      <div style="text-align: right">
        <el-button  @click="viewAppointmentPatients=false">取消</el-button>
        &nbsp;&nbsp;&nbsp;&nbsp;   &nbsp;&nbsp;&nbsp;&nbsp;
        <el-button  type="primary" @click="endAppointment">结束预约</el-button>
      </div>
    </el-dialog>

  </section>
</template>

<script>
import {
  addDrugByPatient,
  cancelAppointment,
  checkOrderDrug, checkOrderInformation, confirmAppointment, endAppointmentByDoctor,
  findAllByAllDuty, findBindOrderIdMedicalList, findCurrentAppointment, findDepartmentCoding, findDrugByDrugName,
  findDutyTimeCoding, findHistoryBingOrderIdMedicalList, getAppointmentTime, getOnDutyHours,
  getTotalPrice, remindPatientNumber, removeDrug, searchMedicinesRemotely
} from '../../api/api';
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      medicalTableDataHistory:[],
      medicalTableData:[],
      top1:true,
      top2:true,
      activeTopName:'',
      viewReportDialog:false,
      newDrugList:[],
      reserveId:'',
      drugNumber:0,
      options: [],
      drugName: '',
      loading: false,
      timeList:[{
        disable:false,
        value:''
      }],
      viewAppointmentPatients:false,
      appointmentTime:'',
      patientId:'',
      reserveDisabled:false,
      scheduledTime:'',
      appointmentDate:'',
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
      totalDrug:0,
      total: 0,
      page: 1,
      pageDrug:1,
      listLoading: false,
      sels: [],//列表选中列
    }
  },
  methods: {
    viewReport(index,row){
      window.open("http://localhost:3000/#/viewTestReport/"+row.id,"_blank");
    },
    viewTheReport(index,row){
      let para={
        orderId:row.id,
        patientId:row.patientId
      }
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      findBindOrderIdMedicalList(para,configs).then((res)=>{
        if(res.data.msgId==='C200'){
          //表示当前患者存在医技报告，开启按钮
          this.top1=false;
          this.top2=false;
          this.activeTopName='first';
          this.medicalTableData=res.data.result;
          this.viewReportDialog=true;
          findHistoryBingOrderIdMedicalList(row.patientId,configs).then((res)=>{
            if(res.data.msgId==='C404'){
              this.viewReportDialog=false;
              ElMessage({
                type: 'error',
                message: '报告状态异常',
              })
            }else if(res.data.msgId==='C200'){
              this.medicalTableDataHistory=res.data.result
            }
          })
        }else if(res.data.msgId==='C404'){
          //不存在将页面转移到第二个界面，并查询历史订单
          this.viewReportDialog=true;
          this.top1=true;
          this.top2=false;
          this.activeTopName='second';
        }
      })
    },
    handleClick (tab,event)  {
      console.log(tab, event)
    },
    removeDrug(index,row){
      ElMessageBox.confirm(
          '确定为该订单删除此药品吗?',
          '删除药品',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
          }
      )
          .then(() => {
            let para={
              orderId:this.reserveId,
              drugId:row.id,
              number:row.number
            }
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            removeDrug(para,configs).then((res)=>{
              if(res.data.msgId=='C401'){
                ElMessage({
                  type: 'error',
                  message: '当前预约单状态异常，请刷新页面重试',
                })
              }else if(res.data.msgId=='C403'){
                ElMessage({
                  type: 'error',
                  message: '删除药品失败，请稍后重试',
                })
              }else if(res.data.msgId=='C405'){
                ElMessage({
                  type: 'error',
                  message: '药品信息更新失败，请联系管理员修正',
                })
              }else if(res.data.msgId=='C200'){
                this.getMedicinesList();
                this.getDrugList();
                ElMessage({
                  type: 'success',
                  message: '药品删除成功',
                })
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '删除药品取消',
            })
          })
    },
    endAppointment(){
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      endAppointmentByDoctor(this.reserveId,configs).then((res)=>{
        if(res.data.msgId=='C401'){
          ElMessage({
            type: 'error',
            message: '当前预约已结束或已取消，请刷新页面重试',
          })
        }else  if(res.data.msgId=='C403'){
          ElMessage({
            type: 'error',
            message: '订单金额未成功更新',
          })
        }else  if(res.data.msgId=='C200'){
          this.viewAppointmentPatients=false
          this.getUsers();
          ElMessage({
            type: 'success',
            message: '结束预约成功',
          })
        }
      })
    },
    getMedicinesList(){
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      checkOrderDrug(this.reserveId,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          this.newDrugList=res.data.result
        }
      })
      getTotalPrice(this.reserveId,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          this.totalPrice=res.data.result
        }
      })
    },
    addDrugReserve(index,row){
      ElMessageBox.confirm(
          '确定为该患者配此药品吗?',
          '患者配药',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'success',
          }
      )
          .then(() => {
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            if(row.enable===0 || row.enable>row.number){
              ElMessage({
                type: 'error',
                message: '药品数量异常,请检查后重新选择',
              })
            }
            let para={
              orderId:this.reserveId,
              drugId:row.id,
              number:row.enable
            }
            addDrugByPatient(para,configs).then((res)=>{
              if(res.data.msgId=='C403'){
                ElMessage({
                  type: 'error',
                  message: '创建订单失败，请稍后重试',
                })
              }else  if(res.data.msgId=='C405'){
                ElMessage({
                  type: 'error',
                  message: '添加药品失败，请稍后重试',
                })
              }else  if(res.data.msgId=='C402'){
                this.getDrugList();
                ElMessage({
                  type: 'error',
                  message: '药品数量不足',
                })
              }else  if(res.data.msgId=='C401'){
                ElMessage({
                  type: 'error',
                  message: '当前预约已结束或已取消，请刷新页面重试',
                })
              }else  if(res.data.msgId=='C404'){
                ElMessage({
                  type: 'error',
                  message: '更新药品信息失败',
                })
              }else  if(res.data.msgId=='C200'){
                this.getMedicinesList();
                this.getDrugList();
                ElMessage({
                  type: 'success',
                  message: '添加药品成功',
                })
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '撤销预约取消',
            })
          })
    },
    handleChange (value) {
      console.log(value)
    },
    getDrugList(){
      let para={
        page:this.pageDrug,
        drugName:this.drugName
      }
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      findDrugByDrugName(para,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          this.drugList=res.data.result.list
          this.totalDrug=res.data.result.total
        }
      })
    },
    remoteMethod(query) {
      if (query !== "") {
        this.loading = true;
        let configs={
          headers: {
            token: sessionStorage.getItem('permissionToken')
          }
        };
        searchMedicinesRemotely(query,configs).then((req)=>{
          if(req.data.msgId=='C200'){
            let res = req.data.result
            this.loading = false
            this.options = res.filter((item)=>{
              return item.drugname.toLowerCase().indexOf(query.toLowerCase()) > -1
            })
          }
        })

      } else {
        this.options = [];
      }
      },
    editAppointmentPatient(index,row){
      this.newDrugList=[];
      this.totalPrice=0;
      this.reserveId=row.id;
      this.getDrugList();
      this.getMedicinesList();
      this.drugName='';
      this.patientId=row.patientId;
      this.viewAppointmentPatients=true;
    },
    cancelAppointment(index,row){
      ElMessageBox.confirm(
          '确定为该患者撤销预约吗?',
          '撤销预约',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
          }
      )
          .then(() => {
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            let para = {"ids": [row.id]};
            cancelAppointment(para,configs).then((res)=>{
              if(res.data.msgId=='C200'){
                this.getUsers();
                ElMessage({
                  type: 'success',
                  message: '撤销预约成功',
                })
              }else{
                ElMessage({
                  type: 'error',
                  message: '撤销预约失败',
                })
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '撤销预约取消',
            })
          })
    },
    batchRemove(){
      let ids = this.sels.map(item => item.id).toString();
      ElMessageBox.confirm(
          '确定为该患者撤销预约吗?',
          '撤销预约',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'error',
          }
      )
          .then(() => {
            let para = {"ids": [ids]};
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            cancelAppointment(para,configs).then((res)=>{
              if(res.data.msgId=='C200'){
                this.getUsers();
                ElMessage({
                  type: 'success',
                  message: '撤销预约成功',
                })
              }else{
                ElMessage({
                  type: 'error',
                  message: '撤销预约失败',
                })
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '撤销预约取消',
            })
          })
    },
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
    handleCurrentChangeDrug(val) {
      this.pageDrug = val;
      this.getDrugList();
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
      findCurrentAppointment(para,configs).then((res) => {
        this.listLoading = false;
        if (res.data.msgId === "C200") {
          this.total = res.data.result.total;
          this.users = res.data.result.list;
        } else {
          this.$message.error('数据获取失败，请稍后重试');
        }
      });
    },
    remindPatient(index,row){
      ElMessageBox.confirm(
          '确定提醒该患者尽快来医院吗?',
          '注意',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
            draggable: true,
          }
      )
          .then(() => {
            let para={
              numberreminders:row.NumberReminders,
              id:row.id
            }
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            remindPatientNumber(para,configs).then((res)=>{
              if(res.data.msgId=='C200'){
                ElMessage({
                  type: 'success',
                  message: '已成功提醒',
                })
                this.getUsers()
              }else if(res.data.msgId=='C403'){
                ElMessage({
                  type: 'error',
                  message: '提醒失败，最大提醒次数为五次',
                })
              }else{
                ElMessage({
                  type: 'info',
                  message: '提醒失败',
                })
              }
            })

          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '取消提醒',
            })
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
          this.doctorInformation=res.data.result
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

