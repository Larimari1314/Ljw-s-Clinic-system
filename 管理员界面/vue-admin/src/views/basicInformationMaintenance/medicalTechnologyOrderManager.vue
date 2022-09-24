<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item style="width: 200px">
          <el-input v-model="filters.name" placeholder="订单id"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="users" highlight-current-row v-loading="listLoading"
              style="width: 100%;">
      <el-table-column prop="id" label="id" width="300">
      </el-table-column>
      <el-table-column label="预约医师" prop="name">
      </el-table-column>
      <el-table-column prop="status" label="订单状态">
      </el-table-column>
      <el-table-column prop="reserveTime" label="预约时间">
      </el-table-column>
      <el-table-column prop="totalMoney" label="总金额">
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button size="small" :disabled="scope.row.statusCoding!='GST03'" @click="lookOrderFrom(scope.$index, scope.row)">查看报告</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <el-dialog title="编辑基本信息" v-model="orderVisible" :close-on-click-modal="false">
    </el-dialog>
  </section>
</template>

<script>
import html2canvas from "html2canvas"
import {
  editGeneral, editMedicalClassification,
  editMedicalTechnology,
  findAllByAllDuty,
  findAllMedicalClassification,
  findAllMedicalDoctor,
  findAllMedicalTechnology, findMedicalTechnologyOrder,
  getMedicalProjectNameList
} from '../../api/api';

export default {
  data() {
    return {
      doctorList:[],
      medicalList:[],
      editForm:'',
      bill:false,
      sameOrder:true,
      totalPrice:'',
      orderList:'',
      orderFrom:[],
      orderVisible:false,
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      filters: {},
      editFormRules: {
        name: [
          {required: true, message: '请提交编码', trigger: 'blur'}
        ],
        technologyname: [
          {required: true, message: '请输入编码值', trigger: 'blur'}
        ]
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
        orderId:this.filters.name
      };
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      this.listLoading = true;
      findMedicalTechnologyOrder(para,configs).then((res)=>{
        if (res.data.msgId === "C200") {
          this.total = res.data.result.total;
          this.users = res.data.result.list;
        } else {
          this.$message.error('数据获取失败，请稍后重试');
        }
        this.listLoading = false;
      })
    },
    //查看订单情况
    lookOrderFrom: function (index, row) {
      window.open("http://localhost:8080/#/viewTestReport/"+row.id,"_blank");
    },
    editSubmit: function () {

    },
  },
  mounted() {
    this.getUsers();
    let configs = {
      headers: {
        token: sessionStorage.getItem('permissionToken')
      }
    };
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
