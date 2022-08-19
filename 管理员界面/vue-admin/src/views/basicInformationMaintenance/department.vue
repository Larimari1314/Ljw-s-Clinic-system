<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="科室名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="department" highlight-current-row v-loading="listLoading"
              style="width: 100%;">
      <el-table-column type="index" width="60">
      </el-table-column>
      <el-table-column prop="id" label="科室编码" width="120" sortable>
      </el-table-column>
      <el-table-column prop="cover" label="科室图片" width="300" sortable>
        <template scope="scope">
          <a href="http://localhost:8080/#/department">
          <img :src="department[scope.$index].cover" style="border-radius:10%; " width="250" height="150"
               alt="科室封面">
          </a>
        </template>
      </el-table-column>
      <el-table-column prop="introduce" label="科室介绍" width="400" sortable>
      </el-table-column>
      <el-table-column prop="name" label="科室名称" width="200" sortable>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)" type="info">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="5" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="科室编码" prop="name">
          <el-input v-model="editForm.id" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="科室封面" prop="avatar">
          <template scope="scope">
            <a href="http://localhost:8080/#/department">
            <img :src="editForm.cover" style="border-radius:10%; " width="100" height="100"
                 alt="科室封面">
            </a>
            <el-upload
                action="http://localhost:8000/clinic/department/upload"
                list-type="picture-card"
                ref="edit"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove">
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog v-model="dialogVisible" size="tiny">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
          </template>
        </el-form-item>
        <el-form-item label="科室名称" prop="name" :rules="[
                      { required: true, message: '科室名称不得为空'}]">
          <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="科室介绍" prop="introduce" :rules="[
                      { required: true, message: '科室介绍不得为空'}]">
          <el-input type="textarea" v-model="editForm.introduce"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false;">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="80px"  ref="addForm">
        <el-form-item label="科室编码" prop="id" :rules="[
                      { required: true, message: '科室编码不能为空'},
                      {pattern: /^[0-9]{3}$/,message: '长度必须为三位，且为数字'}
                      ]">
          <el-input v-model="addForm.id" auto-complete="off" >
            <template slot="prepend">DE</template>
          </el-input>
        </el-form-item>
        <el-form-item label="科室图片">
          <template scope="scope">
            <el-upload
                action="http://localhost:8000/clinic/department/upload"
                ref="upload"
                list-type="picture-card"
                :on-preview="handlePictureCardPreview"
                :on-remove="handleRemove"
                :limit="1"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog v-model="dialogVisible" size="tiny">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
          </template>
        </el-form-item>
        <el-form-item label="科室名称" prop="name" :rules="[
                      { required: true, message: '科室名称不得为空'}]">
          <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="科室介绍" prop="introduce" :rules="[
                      { required: true, message: '科室介绍不得为空'}]">
          <el-input type="textarea" v-model="addForm.introduce"  ></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import {
  addDepartment,
   editDepartment, findAllDepartment,
} from '../../api/api';

export default {
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      category: '',
      options: [],
      filters: {
        name: ''
      },
      department: [],
      total: '',
      page: 1,
      listLoading: false,
      sels: [],//列表选中列

      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      //编辑界面数据
      editForm: {
      },

      addFormVisible: false,//新增界面是否显示
      addLoading: false,
      //新增界面数据
      addForm: {
        id:'',
        name: '',
        cover:'',
        introduce:''
      }

    }
  },
  methods: {
    handleRemove(file, fileList) {
      let index = fileList.findIndex( fileItem => {
        return fileItem.uid === file.uid
      })
      fileList.splice(index, 1)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    getDepartClassList: function () {
      getDepartClassList().then((res) => {
        this.options = res.data.result
      })
    },
    //获取用户列表
    getUsers() {
      let para = {
        page: this.page,
        name: this.filters.name
      };
      this.listLoading = true;
      findAllDepartment(para).then((res) => {
        if (res.data.msgId == 'C200') {
          this.department = res.data.result.list;
          this.total = res.data.result.total
          this.listLoading = false;
        }
      })
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
    },
    //显示新增界面
    handleAdd: function () {
      this.addFormVisible = true;
      this.addForm = {
        id:'',
        name: '',
        cover:'',
        introduce:''
      };
    },
    //编辑
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            editDepartment(this.editForm).then((res)=>{
              this.$refs.edit.clearFiles()
              if(res.data.msgId=='C200'){
                this.editFormVisible = false;
                this.editLoading = false;
                this.dialogImageUrl='';
                this.$notify.success({
                  title: '成功',
                  message: '修改成功',
                  offset: 100
                });
                this.getUsers()
              }else if(res.data.msgId=='C404'){
                this.$message.error('请上传封面');
              }else {
                this.$refs.edit.clearFiles()
                this.editFormVisible = false;
                this.editLoading = false;
                this.dialogImageUrl='';
                this.$notify.error({
                  title: '错误',
                  message: '修改失败'
                });
              }
            })
          });
        }
      });
    },
    //新增
    addSubmit: function () {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addForm.id='DE'+this.addForm.id
            this.addLoading = true;
            //NProgress.start();
            // alert(JSON.stringify(this.addForm))
            addDepartment(this.addForm).then((res)=>{
              if(res.data.msgId=='C200'){
                this.$refs.upload.clearFiles()
                this.addFormVisible = false;
                this.addLoading = false;
                this.dialogImageUrl='';
                this.$notify.success({
                  title: '成功',
                  message: '添加成功',
                  offset: 100
                });
                this.getUsers()
              }else if(res.data.msgId=='C403'){
                this.addLoading = false;
                this.addForm.id=''
                this.$message.error('科室编码重复');
              }else if(res.data.msgId=='C404'){
                this.addLoading = false;
                this.$refs.upload.clearFiles()
                this.$message.error('请上传封面');
              }
              else {
                this.$refs.upload.clearFiles()
                this.addFormVisible = false;
                this.addLoading = false;
                this.dialogImageUrl='';
                this.$notify.error({
                  title: '错误',
                  message: '添加失败'
                });
              }
            })
          });
        }
      });
    }
  },
  mounted() {
    this.getUsers();
    this.getDepartClassList();
  }
}

</script>

<style scoped>

</style>
