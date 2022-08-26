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
      <el-table-column prop="operatingTime" label="最后操作时间" width="150">
      </el-table-column>
      <el-table-column prop="reserveTime" label="预约时间" width="150" sortable>
      </el-table-column>
      <el-table-column prop="payment" label="支付编号" min-width="170" sortable>
      </el-table-column>
      <el-table-column prop="orderPrice" label="金额" min-width="120" sortable>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template v-slot="scope">
          <el-button  type="primary" round size="small"
                     @click="checkOrder(scope.$index, scope.row)">发药
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

  </section>
</template>

<script>
import {
  dispensingMedicine,
  findDispensingInterface,
} from '../../api/api';
import {ElMessage, ElMessageBox} from "element-plus";

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
      this.listLoading = true;
      findDispensingInterface(para).then((res) => {
        this.listLoading = false;
        if (res.data.msgId === "C200") {
          this.total = res.data.result.total;
          this.users = res.data.result.list;
        } else {
          this.$message.error('数据获取失败，请稍后重试');
        }
      });
    },
    checkOrder: function (index, row) {
      ElMessageBox.confirm(
          '确定为该患者发放药品吗?',
          '发放药品',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'success',
          }
      )
          .then(() => {
            let para = {
              reserveId: row.id,
              doctorId: this.doctorInformation.id,
              payment:row.payment
            }
            dispensingMedicine(para).then((res)=>{
              if(res.data.msgId=='C401'){
                ElMessage({
                  type: 'error',
                  message: '当前订单预约未结束，请先结束预约',
                })
              }else if(res.data.msgId=='C402'){
                ElMessage({
                  type: 'error',
                  message: '查询支付结果异常，请联系管理员后重试',
                })
              }else if(res.data.msgId=='C405'){
                ElMessage({
                  type: 'error',
                  message: '修改结果异常，请联系管理员',
                })
              }else if(res.data.msgId=='C200'){
                this.getUsers()
                ElMessage({
                  type: 'success',
                  message: '发药成功',
                })
              }
            })
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

