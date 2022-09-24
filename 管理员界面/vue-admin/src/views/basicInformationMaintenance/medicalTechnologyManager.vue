<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item style="width: 200px">
          <el-input v-model="filters.name" placeholder="医技名称"></el-input>
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
    <el-table :data="users" highlight-current-row v-loading="listLoading"
              style="width: 100%;">
      <el-table-column prop="id" label="id" width="100">
      </el-table-column>
      <el-table-column label="封面">
        <template scope="scope">
          <a href="http://localhost:8080/#/medicalTechnologyManager">
            <img :src="users[scope.$index].introduce" style="border-radius:50%; " width="100" height="100"
                 alt="用户头像">
          </a>
        </template>
      </el-table-column>
      <el-table-column prop="projectName" label="医技分类">
      </el-table-column>
      <el-table-column prop="technologyname" label="医技名称">
      </el-table-column>
      <el-table-column label="医技下属医师">
        <template scope="scope">
          <span v-for="(s,index) in users[scope.$index].doctor" >
            {{s.name}} <span v-if="users[scope.$index].doctor.length-1!== index">,</span>
          </span>
        </template>
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
      <el-form :model="editForm" label-width="95px" :rules="editFormRules" ref="editForm">
        <el-form-item label="封面">
          <img :src="editForm.introduce" style="border-radius:50%; " width="100" height="100"
               alt="用户头像">
          <el-upload
              action="http://localhost:8000/clinic/medicalTechnologyByAdmin/upload"
              ref="edit"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog v-model="dialogVisible" size="tiny">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
        <el-form-item label="医技分类" prop="projectCoding" >
          <el-select v-model="editForm.projectCoding" clearable placeholder="选择检查项">
            <el-option
                v-for="item in medicalList"
                :key="item.id"
                :label="item.value"
                :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="医技名称" prop="technologyname">
          <el-input v-model="editForm.technologyname"></el-input>
        </el-form-item>
        <el-form-item label="附属医生" prop="doctorList">
          <el-select v-model="editForm.doctor" clearable placeholder="选择医生" multiple>
              <el-option
                  v-for="item in doctorList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
              />
          </el-select>
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
  editGeneral, editMedicalTechnology,
  findAllByAllDuty, findAllMedicalDoctor, findAllMedicalTechnology, getMedicalProjectNameList
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
        projectCoding: [
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
        medicalName:this.filters.name,
        medicalId:this.filters.medicalId
      };
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      this.listLoading = true;
      findAllMedicalTechnology(para,configs).then((res)=>{
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
            this.$refs.edit.clearFiles();
            let para={
              id:this.editForm.id,
              medicalName:this.editForm.technologyname,
              medicalId:this.editForm.projectCoding,
              ids:this.editForm.doctor
            }
            // alert(JSON.stringify(para))
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            editMedicalTechnology(para,configs).then((res)=>{
              if(res.data.msgId==='C200'){
                this.orderVisible = false;
                this.getUsers()
                this.$notify.success({
                  title: '成功',
                  message: '修改成功'
                });
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
    getMedicalProjectNameList(configs).then((res) => {
      if (res.data.msgId === 'C200') {
        this.medicalList = res.data.result;
      }
    })
    findAllMedicalDoctor(configs).then((res) => {
      if (res.data.msgId === 'C200') {
        this.doctorList = res.data.result;
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
