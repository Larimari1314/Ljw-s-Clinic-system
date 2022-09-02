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
          <el-select v-model="filters.orderStatus" placeholder="请选择订单状态">
            <el-option
                v-for="item in dutyTimeLIst"
                :key="item.coding"
                :label="item.value"
                :value="item.coding">
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
      <el-table-column type="index" label="序号" width="100">
      </el-table-column>
      <el-table-column prop="id" label="订单id" width="120">
      </el-table-column>
      <el-table-column prop="doctorName" label="医生姓名" width="120">
      </el-table-column>
      <el-table-column prop="patientName" label="患者姓名" width="120">
      </el-table-column>
      <el-table-column prop="departName" label="预约科室" width="150">
      </el-table-column>
      <el-table-column prop="orderState" label="当前状态" width="100">
        <template scope="scope">
          <el-tag v-if="users[scope.$index].orderState=='未支付'" type="warning">未支付</el-tag>
          <el-tag v-if="users[scope.$index].orderState=='已支付'" type="success">已支付</el-tag>
          <el-tag v-if="users[scope.$index].orderState=='订单完成'" type="primary">订单完成</el-tag>
          <el-tag v-if="users[scope.$index].orderState=='订单取消'" type="danger">已取消</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="reserveTime" label="预约时间" width="150" sortable>
      </el-table-column>
      <el-table-column prop="orderPrice" label="价格" min-width="120" sortable>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button type="success" size="small" @click="lookOrderFrom(scope.$index, scope.row)">查看订单详情</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <el-dialog title="查看订单详情" v-model="orderVisible" :close-on-click-modal="false">
      <div class="modal" id="modal">
        <div class="content">
          <table id="msg" class="msg">
            <tr>
              <td style="text-align: left">订单ID:&nbsp;&nbsp;{{ orderList.id }}</td>
              <td style="text-align: left">开药科室:&nbsp;&nbsp;{{ orderList.departName }}</td>
            </tr>
            <tr>
              <td style="text-align: left">预约时间:&nbsp;&nbsp;{{ orderList.reserveTime }}</td>
              <td style="text-align: left">开单医生:&nbsp;&nbsp; {{ orderList.doctorName }}</td>
            </tr>
            <tr>
              <td style="text-align: left">患者姓名:&nbsp;&nbsp;{{ orderList.patientName }}</td>
              <td style="text-align: left">身份证号:&nbsp;&nbsp;{{ orderList.identityNumber }}</td>
            </tr>
          </table>
          <el-table
              :data="orderFrom"
              style="width: 100%">
            <el-table-column
                prop="drugName"
                label="药品名称"
                width="150">
            </el-table-column>
            <el-table-column
                prop="drugPrice"
                label="单价">
            </el-table-column>
            <el-table-column
                prop="number"
                label="数量">
            </el-table-column>
            <el-table-column
                prop="totalPrice"
                label="总价/￥">
            </el-table-column>
          </el-table>
          <br>
        </div>
        <div>
          <font style="text-align: left">总价:&nbsp;&nbsp;{{ totalPrice }}</font>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="orderVisible = false;sameOrder=true">取消</el-button>
        <el-button type="primary" @click.native="handleDownload" :disabled="sameOrder">下载发票
        </el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import html2canvas from "html2canvas"
import {
  findAllByAllDuty, findAllOrderFrom,
  findDepartmentCoding, findOrderDrugList, findPayInformation, getTotalPrice
} from '../../api/api';

export default {
  data() {
    return {
      bill:false,
      sameOrder:true,
      totalPrice:'',
      orderList:'',
      orderFrom:[],
      orderVisible:false,
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
        orderStatus: ''
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
        orderId:this.filters.orderId,
        patientName: this.filters.patientName,
        doctorName: this.filters.doctorName,
        did: this.filters.did,
        orderStatus: this.filters.orderStatus
      };
      this.listLoading = true;
      findAllOrderFrom(para).then((res) => {
        if (res.data.msgId === "C200") {
          this.total = res.data.result.total;
          this.users = res.data.result.list;
        } else {
          this.$message.error('数据获取失败，请稍后重试');
        }
        this.listLoading = false;
      });
    },
    //查看订单情况
    lookOrderFrom: function (index, row) {
      this.orderVisible=true;
      this.sameOrder=true;
      this.totalPrice=0;
      this.orderList= Object.assign({}, row);
      if(this.orderList.orderState==='已支付' || this.orderList.orderState==='订单完成' ){
        this.sameOrder=false
      }
      findOrderDrugList(row.id).then((res)=>{
       if(res.data.msgId=='C200'){
         this.orderFrom=res.data.result
       } else{
         this.$message.error('数据获取失败，请稍后重试');
       }
      })
      getTotalPrice(row.id).then((res)=>{
        if(res.data.msgId=='C200'){
          this.totalPrice=res.data.result;
        } else{
          this.$message.error('数据获取失败，请稍后重试');
        }
      })
    },
    handleDownload(){
      window.open("#/bill/"+this.orderList.id)
    },
    selsChange: function (sels) {
      this.sels = sels;
    }
  },
  mounted() {
    this.getUsers();
    findDepartmentCoding().then((res) => {
      if (res.data.msgId === "C200") {
        this.departmentList = res.data.result
      } else {
        this.$message.error('数据初始化失败，请稍后重试');
      }
    });
    findPayInformation().then((res) => {
      if (res.data.msgId === "C200") {
        this.dutyTimeLIst = res.data.result
      } else {
        this.$message.error('数据初始化失败，请稍后重试');
      }
    })
    findAllByAllDuty().then((res) => {
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
.content {
  max-height: 500px;
  overflow-y: auto;
  width: 600px;
}

.modal {
  width: 600px;
  margin: 0 auto;
  position: relative;
  top: 50%; /*偏移*/
  background: #ffffff;
  border-radius: 5px;
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
