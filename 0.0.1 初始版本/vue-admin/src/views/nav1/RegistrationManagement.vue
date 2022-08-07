<template>
  <section>
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="search">

        <el-form-item>
          <el-input v-model="search.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-tooltip class="item" effect="dark" content="根据患者和医生名称查询" placement="top">
            <el-button type="primary" v-on:click="findByName">查询</el-button>
          </el-tooltip>
        </el-form-item>

      </el-form>
    </el-col>
    <!--列表-->
    <template>
      <!--      <a href="/?#/DepartmentDoctorList">www</a>-->
      <el-table :data="reservationTime" highlight-current-row v-loading="loading"
                style="width: 100%;text-align: center">
        <el-table-column type="index" label="序号" width="100">
        </el-table-column>
        <el-table-column prop="patient.patientName" label="患者名称" width="150">
        </el-table-column>
        <el-table-column prop="appointment" label="预约时间" width="120" sortable>
        </el-table-column>
        <el-table-column prop="patient.patientAge" label="患者年龄" width="120" sortable>
        </el-table-column>
        <el-table-column prop="doctor.department.departmentName" label="预约科室" width="130">
        </el-table-column>
        <el-table-column prop="doctor.doctorName" label="医生名称" width="150">
        </el-table-column>
        <el-table-column prop="doctor.doctorPhone" label="医生联系电话" width="170">
        </el-table-column>
        <el-table-column label="操作" width="300">
          <template scope="scope">
            <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">撤销预约</el-button>
            &nbsp;&nbsp;&nbsp;
            <el-button type="info" size="small" @click="handleEdit(scope.$index, scope.row)">点击查看订单详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-col :span="24" class="toolbar">
        <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total"
                       style="float:right;">
        </el-pagination>
      </el-col>
      <el-dialog title="订单详情" v-model="order" :close-on-click-modal="false">
        <template>
          <div class="modal" id="modal" v-watermark="watermark">
            <div class="title">
              <div class="important"><b>仁城中心医院</b></div>
            </div>
            <div class="content">
              <table id="msg" class="msg">
                <tr>
                  <td style="text-align: left">订单ID:&nbsp;&nbsp;{{ orderList.id }}</td>
                  <td style="text-align: left">开药科室:&nbsp;&nbsp;{{ department.departmentName }}</td>
                </tr>
                <tr>
                  <td style="text-align: left">就诊时间:&nbsp;&nbsp;{{ orderList.appointment }}</td>
                  <td style="text-align: left">开单医生:&nbsp;&nbsp; {{ doctor.doctorName }}</td>
                </tr>
                <tr>
                  <td style="text-align: left">患者姓名:&nbsp;&nbsp;{{ patient.patientName }}</td>
                  <td style="text-align: left">身份证号:&nbsp;&nbsp;{{ patient.identityNumber }}</td>
                </tr>
              </table>
              <el-table
                  :data="tableData"
                  style="width: 100%">
                <el-table-column
                    prop="drug.drugName"
                    label="药品名称"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="number"
                    label="个数"
                    width="180">
                </el-table-column>
                <el-table-column
                    prop="drug.originalPrice"
                    label="单价">
                </el-table-column>
              </el-table>
              <br>
            </div>
          </div>
        </template>
      </el-dialog>

    </template>

  </section>
</template>
<script>
//import NProgress from 'nprogress'
import {
  deleteReservation,
  findByIdRegistration,
  findByIdReservation,
  findByNameReservation,
  GetReservationTimeList
} from "../../api/api";

export default {
  data() {
    return {
      search: {name: ''},
      tableData: [],
      watermark: '',
      order: false,
      reservationTime: [],
      loading: false,
      sels: [],
      total: 20,
      page: 1,
      orderList: [],
      doctor: [],
      patient: [],
      department: []

    }
  },
  methods: {
    findByName: function () {
      let _this=this
      let params = {name: this.search.name, page: this.page}
      findByNameReservation(params).then((res) => {
        if(res.data.code=='200'){
          this.reservationTime = res.data.data.list
          this.total = res.data.data.total
          _this.$notify({
            title: '查找成功',
            message: res.data.message,
            type: 'success'
          });
        }else{
          _this.$notify({
            title: '失败',
            message: res.data.message,
            type: 'error'
          });
        }
      })
    },
    getReservationTimeList: function () {
      let para = {
        page: this.page
      };
      GetReservationTimeList(para).then((res) => {
        this.reservationTime = res.data.data.list
        this.total = res.data.data.total
      })
    },
    handleDel: function (index, row) {
      this.$confirm('确认撤销该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        //NProgress.start();
        let para = {id: row.id};
        let _this = this
        deleteReservation(para).then(function (res) {
          if (res.data.code == '200') {
            _this.$notify({
              title: '成功',
              message: res.data.message,
              type: 'success'
            });
            _this.getReservationTimeList()
          } else {
            _this.$notify({
              title: '失败',
              message: res.data.message,
              type: 'error'
            });
            _this.getReservationTimeList()
          }
        })
      })
    },
    handleEdit: function (index, row) {
      this.order = true
      let param = {id: row.id}
      findByIdReservation(param).then((res) => {
        if (res.data.code) {
          this.watermark = {
            text1: res.data.data.patient.patientPhone,
            text2: res.data.data.patient.patientName,
            textColor: '#f1bbbd'
          }
          this.orderList = res.data.data
          this.doctor = res.data.data.doctor
          this.patient = res.data.data.patient
          this.department = this.doctor.department
        }
      })
      findByIdRegistration(param).then((res) => {
        this.tableData = res.data.data
      })
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getReservationTimeList();
    }
  },
  mounted() {
    this.getReservationTimeList()
  }
};

</script>

<style scoped>

</style>
<style>
.modal {
  width: 600px;
  margin: 0 auto;
  position: relative;
  top: 50%; /*偏移*/
  background: #ffffff;
  border-radius: 5px;
}

.confirm {
  height: 50px;
  border-top: 1px solid #eee;
  display: flex;
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

.title {
  text-align: center;
  border-bottom: 1px solid #eee;
  margin-bottom: 10px;
}

.title > p {
  height: 30px;
  line-height: 40px;
  text-align: center;
  font-size: 18px;
  font-weight: bold;

}

.important {
  color: rgb(197, 113, 106);
  font-size: 30px;
}

.close-but:hover {
  opacity: 0.5;
}

.content {
  max-height: 500px;
  overflow-y: auto;
  width: 600px;
}

.content > table {
  padding: 0 10px 10px 10px;
  margin: 0 auto;
  width: 100%;

}

.msg {
  /* border-spacing: 80px 10px; */
  width: 100%;
  height: 150px;
  text-align: center;
  /* border-style:solid;
  border-color:rgb(120, 121, 164); */
  table-layout: fixed
}

.msg2 {
  /* border-spacing: 70px 10px; */
  width: 100%;
  height: 50px;
  border-style: solid;
  border-color: rgb(120, 121, 164);
  table-layout: fixed;
  text-align: center;
}


</style>