<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.doctorName" placeholder="医生名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="currentAppointmentList" highlight-current-row v-loading="listLoading"
              style="width: 100%;">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="orderId" label="订单id" sortable>
      </el-table-column>
      <el-table-column prop="doctorAvatar" label="医生头像">
        <template v-slot="scope">
          <el-avatar :size="100" :src="currentAppointmentList[scope.$index].doctorAvatar" />
        </template>
      </el-table-column>
      <el-table-column prop="name" label="医生姓名">
      </el-table-column>
      <el-table-column prop="reserveTime" label="预约时间" sortable>
      </el-table-column>
      <el-table-column prop="departmentName" label="预约科室">
      </el-table-column>
      <el-table-column prop="state" label="订单状态">
        <template v-slot="scope">
          <el-tag class="ml-2" v-if="currentAppointmentList[scope.$index].state==0" type="success">
            当前预约
          </el-tag>
          <el-tag class="ml-2" v-if="currentAppointmentList[scope.$index].state==1" type="warning">预约结束
          </el-tag>
          <el-tag class="ml-2" v-if="currentAppointmentList[scope.$index].state==2" type="danger">预约取消
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)" type="danger" :disabled="currentAppointmentList[scope.$index].state!=0">取消预约
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

  </section>
</template>

<script>
import {
  cancelAppointment,
  findAllHistoryOrderForm,
  findOrderCompletion,
  getAllCurrentAppointment,
} from '../../api/api';
import {ElMessage, ElMessageBox} from "element-plus";

export default {
  data() {
    return {
      currentAppointmentList: [],
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
      listLoading: false,
      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      patientInformation: JSON.parse(localStorage.getItem('patient'))
    }
  },
  methods: {
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
      getAllCurrentAppointment(para,configs).then((res) => {
        if (res.data.msgId == 'C200') {
          this.currentAppointmentList = res.data.result.list;
          this.total = res.data.result.total
          this.listLoading = false;
        }
      })
    },
    handleEdit: function (index, row) {
      ElMessageBox.confirm(
          '确定撤销预约吗，注意医生已经就诊预约无法撤销?',
          '注意',
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
            cancelAppointment(row.orderId,configs).then((res)=>{
              if(res.data.msgId=='C200'){
                ElMessage({
                  type: 'success',
                  message: '撤销预约成功',
                })
                this.getUsers()
              }else if(res.data.msgId=='C403'){
                ElMessage({
                  type: 'error',
                  message: '预约记录不存在',
                })
              }else if(res.data.msgId=='C402'){
                ElMessage({
                  type: 'error',
                  message: '当前预约以受理，不可撤销',
                })
              }else if(res.data.msgId=='C405'){
                ElMessage({
                  type: 'error',
                  message: '撤销失败，请稍后重试',
                })
              }
            })

          })
          .catch(() => {
          })
    },
  },
  mounted() {
    this.getUsers();
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
