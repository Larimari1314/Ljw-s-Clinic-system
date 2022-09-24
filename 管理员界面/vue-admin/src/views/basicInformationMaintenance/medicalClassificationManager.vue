<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item style="width: 200px">
          <el-input v-model="filters.name" placeholder="医技名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="users" highlight-current-row v-loading="listLoading"
              style="width: 100%;">
      <el-table-column prop="id" label="id" width="100">
      </el-table-column>
      <el-table-column label="医技名称" prop="name">
      </el-table-column>
      <el-table-column prop="technologyname" label="医技分类">
      </el-table-column>
      <el-table-column prop="maxNumber" label="最大接待人数">
      </el-table-column>
      <el-table-column prop="money" label="价格">
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button size="small" @click="lookOrderFrom(scope.$index, scope.row)">编辑医技信息</el-button>
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
      <el-form :model="editForm" label-width="100px" :rules="editFormRules" ref="editForm">
        <el-form-item label="封面" prop="technologyname">
          <el-input v-model="editForm.technologyname" disabled></el-input>
        </el-form-item>
        <el-form-item label="医技名称" prop="name" >
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="金额" prop="money">
          <el-input-number v-model="editForm.money" min="10" max="9999"></el-input-number>
        </el-form-item>
        <el-form-item label="最大就诊人数" prop="maxNumber">
          <el-input-number v-model="editForm.maxNumber" min="1" max="100"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="orderVisible = false;sameOrder=true">取消</el-button>
        <el-button type="primary" @click.native="editSubmit">提交
        </el-button>
      </div>
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
  findAllMedicalTechnology,
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
        medicalName:this.filters.name
      };
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      this.listLoading = true;
      findAllMedicalClassification(para,configs).then((res)=>{
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
      this.orderVisible = true;
      this.editForm = Object.assign({}, row);
      let ids=[];
      for (let i = 0; i < row.doctor.length; i++) {
        ids.push(row.doctor[i].id)
      }
      this.editForm.doctor=ids
    },
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            let para={
              id:this.editForm.id,
              name:this.editForm.name,
              maxNumber:this.editForm.maxNumber,
              money:this.editForm.money
            }
            // alert(JSON.stringify(para))
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            editMedicalClassification(para,configs).then((res)=>{
              if(res.data.msgId==='C200'){
                this.orderVisible=false;
                this.$notify.success({
                  title: '成功',
                  message: '修改成功'
                });
                this.getUsers()
              }else{
                this.$notify.error({
                title: '失败',
                message: '修改失败'
              });
              }
            })
          })
        }
      })
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
