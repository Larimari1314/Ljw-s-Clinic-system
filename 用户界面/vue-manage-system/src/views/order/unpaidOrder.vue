<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.doctorName" placeholder="科室名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="unpaidOrderFormList" highlight-current-row v-loading="listLoading"
              style="width: 100%;">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="orderId" label="订单id" sortable>
      </el-table-column>
      <el-table-column prop="doctorName" label="医生姓名">
      </el-table-column>
      <el-table-column prop="operatingTime" label="发药时间" sortable>
      </el-table-column>
      <el-table-column prop="patientName" label="预约患者">
      </el-table-column>
      <el-table-column prop="reserveTime" label="预约时间" sortable>
      </el-table-column>
      <el-table-column prop="orderPrice" label="金额" sortable>
      </el-table-column>
      <el-table-column prop="orderState" label="订单状态">
        <template v-slot="scope">
          <el-tag class="ml-2" v-if="unpaidOrderFormList[scope.$index].orderState=='未支付'" type="error">
            未支付
          </el-tag>
          <el-tag class="ml-2" v-if="unpaidOrderFormList[scope.$index].orderState=='已支付'" type="success">
            已支付
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200px">
        <template v-slot="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)" :disabled="unpaidOrderFormList[scope.$index].orderState=='已支付'" type="primary" round>支付订单</el-button>
          <el-button size="small" @click="refundOperation(scope.$index, scope.row)" :disabled="unpaidOrderFormList[scope.$index].orderState=='未支付'" type="danger" round>申请退款</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="5" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>
    <br/><br/><br/>
    <el-divider>
      <div>医技未支付订单</div>
    </el-divider><br/>
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filtersMedical">
        <el-form-item>
          <el-input v-model="filtersMedical.name" placeholder="化验查询"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filtersMedical.medicalId" clearable placeholder="选择检查项">
            <el-option
                v-for="item in medicalList"
                :key="item.id"
                :label="item.value"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getMedicalOrderList">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <el-table :data="medicalUnpartList"  highlight-current-row v-loading="listLoadingMedical"
              style="width: 100%;" border>
      <el-table-column prop="id" label="订单编号"/>
      <el-table-column prop="name" label="医生姓名"/>
      <el-table-column prop="reserveTime" label="预约日期" />
      <el-table-column prop="appointmentTime" label="确定预约时间" />
      <el-table-column prop="doctorRegis" label="挂号级别" />
      <el-table-column prop="totalMoney" label="总金额" />
      <el-table-column prop="status" label="状态" >
        <template v-slot="scope">
          <el-tag class="ml-2" v-if="medicalUnpartList[scope.$index].statusCoding=='GST01'" type="error">
            {{medicalUnpartList[scope.$index].status}}
          </el-tag>
          <el-tag class="ml-2" v-if="medicalUnpartList[scope.$index].statusCoding=='GST02'" type="success">
            {{medicalUnpartList[scope.$index].status}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200px">
        <template v-slot="scope">
          <el-button size="small" @click="handleEditMedical(scope.$index, scope.row)"  type="primary" :disabled="medicalUnpartList[scope.$index].statusCoding=='GST02'" round>支付订单</el-button>
          <el-button size="small" @click="refundOperationMedical(scope.$index, scope.row)"  type="danger" :disabled="medicalUnpartList[scope.$index].statusCoding=='GST01'" round>申请退款</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChangeMe" :page-size="5" :total="totalMe"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="订单详情" v-model="editFormVisible" :close-on-click-modal="false">
      <div class="table-container">
        <table id="msg" class="msg">
          <tr>
            <td style="text-align: left">订单ID:&nbsp;&nbsp;{{ orderInformation.id }}</td>
            <td style="text-align: left">开药科室:&nbsp;&nbsp;{{ orderInformation.departmentName }}</td>
          </tr>
          <tr>
            <td style="text-align: left">发药时间:&nbsp;&nbsp;{{ orderInformation.operatingTime }}</td>
            <td style="text-align: left">开单医生:&nbsp;&nbsp; {{ orderInformation.doctorName }}</td>
          </tr>
          <tr>
            <td style="text-align: left">患者姓名:&nbsp;&nbsp;{{ orderInformation.patientName }}</td>
            <td style="text-align: left">当前状态:&nbsp;&nbsp;{{ orderInformation.orderState }}</td>
          </tr>
          <tr>
            <td style="text-align: left">预约时间:&nbsp;&nbsp;{{ orderInformation.reserveTime }}</td>
            <td style="text-align: left">患者身份证号:&nbsp;&nbsp;{{ orderInformation.identityNumber }}</td>
          </tr>
        </table>
      </div>
      <el-table :data="orderDrugList" highlight-current-row v-loading="listLoading"
                style="width: 100%;">
        <el-table-column type="index" width="60">
        </el-table-column>
        <el-table-column prop="cover" label="封面">
          <template v-slot="scope">
            <div class="demo-image__preview">
              <el-image
                  style="width: 100px; height: 100px"
                  :src="orderDrugList[scope.$index].cover"
                  fit="cover"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="drugName" label="药品名称">
        </el-table-column>
        <el-table-column prop="number" label="药品数量" sortable>
        </el-table-column>
        <el-table-column prop="price" label="药品单价">
        </el-table-column>
        <el-table-column prop="totalPrice" label="总价" sortable>
        </el-table-column>
      </el-table>
      总价：<h1>{{orderInformation.totalPrice}}</h1>
      <div style="text-align: right">
        <el-button round @click="editFormVisible=false">取消</el-button>
        <el-button round type="primary" @click="payOrder">支付订单</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import {
  findAllUnpaidOrder,
  findUnpaidOrderForm,
  getMedicalProjectNameList,
  getOrderInformation, payingEncryption,
  payOrderBounding, refundMedicalOrder,
  refundOperationOrder,
} from '../../api/api';
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      listLoadingMedical:false,
      medicalUnpartList:[],
      filtersMedical:{},
      medicalList:[],
      payOrderId:'',
      unpaidOrderFormList: [],
      dialogVisible: false,
      category: '',
      filters: {
        doctorName: ''
      },
      orderInformation: [],
      orderDrugList: [],
      department: [],
      total: 0,
      page: 1,
      pageMe:1,
      totalMe:0,
      listLoading: false,
      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      patientInformation: JSON.parse(localStorage.getItem('patient'))
    }
  },
  methods: {
    handleEditMedical(index,row){
      ElMessageBox.confirm(
          '确定支付该订单吗?',
          '支付',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            let configs = {
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            payingEncryption(row.id,configs).then((res)=>{
              let that=this
              if(res.data.msgId==='C200'){
                that.$router.replace({path:'/payMedicalTechnology/'+res.data.result})
              }
            })
          })
    },
    refundOperationMedical(index,row){
      ElMessageBox.confirm(
          '确定支付该订单吗?',
          '支付',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            let configs = {
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            refundMedicalOrder(row.id,configs).then((res)=>{
              if(res.data.msgId=='C404'){
                ElMessage({
                  type: 'error',
                  message: '订单状态异常',
                })
              }else if(res.data.msgId=='C405'){
                ElMessage({
                  type: 'error',
                  message: '退款失败，请联系管理员后重试',
                })
              }else if(res.data.msgId=='C200'){
                ElMessage({
                  type: 'success',
                  message: '退款成功',
                })
                this.getMedicalOrderList();
              }
            })
          })
    },
    getMedicalOrderList(){
      let para = {
        page: this.page,
        name: this.filtersMedical.name,
        medicalId: this.filtersMedical.medicalId
      };
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      this.listLoadingMedical=true;
      // alert(JSON.stringify(para))
      findAllUnpaidOrder(para,configs).then((res)=>{
        if(res.data.msgId==='C200'){
          this.medicalUnpartList=res.data.result.list
          this.totalMe = res.data.result.total
          this.listLoadingMedical = false;
        }
      })
    },
    handleCurrentChangeMe(val){
      this.pageMe = val;
      this.getMedicalOrderList();
    },
    refundOperation(index,row){
      ElMessageBox.confirm(
          '确定退款吗，再次购买需重新预约流程才可购买?',
          '退款',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            refundOperationOrder(row.orderId,configs).then((res)=>{
              if(res.data.msgId=='C200'){
                ElMessage({
                  type: 'success',
                  message: '退款成功',
                })
                this.getUsers()
              }else{
                ElMessage({
                  type: 'error',
                  message: '退款失败，请联系管理员后重试',
                })
              }
            })
          })
          .catch(() => {
            ElMessage({
              type: 'info',
              message: '撤销退款',
            })
          })
    },
    payOrder(){
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      payOrderBounding(this.payOrderId,configs).then((res)=>{
        let that=this
        if(res.data.msgId=='C200'){
          that.$router.replace({path:'/payOrder/'+res.data.result})
        }else if(res.data.msgId=='C404'){
          this.editFormVisible=false;
          ElMessage({
            type: 'error',
            message: '订单状态异常，请检查后刷新页面重试',
          })
        }
      })
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    getUsers() {
      let para = {
        page: this.page,
        doctorName: this.filters.doctorName,
        patientId: this.patientInformation.id
      };
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      this.listLoading = true;
      findUnpaidOrderForm(para,configs).then((res) => {
        if (res.data.msgId == 'C200') {
          this.unpaidOrderFormList = res.data.result.list;
          this.total = res.data.result.total
          this.listLoading = false;
        }
      })
    },
    handleEdit: function (index, row) {
      this.orderInformation='';
      this.orderDrugList='';
      this.editFormVisible = true;
      this.payOrderId='';
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      getOrderInformation(row.orderId,configs).then((res) => {
        if (res.data.msgId == 'C200') {
          this.payOrderId=row.orderId
          this.orderInformation = res.data.result.informationOrder;
          this.orderDrugList = res.data.result.drugList
        } else {
          this.editFormVisible = false;
          ElMessage({
            type: 'error',
            message: '当前订单异常，请检查后刷新页面重试',
          })
        }
      })
    },
  },
  mounted() {
    this.getUsers();
    this.getMedicalOrderList();
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
.demo-image__error .image-slot {
  font-size: 30px;
}
.demo-image__error .image-slot .el-icon {
  font-size: 30px;
}
.demo-image__error .el-image {
  width: 100%;
  height: 200px;
}
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
