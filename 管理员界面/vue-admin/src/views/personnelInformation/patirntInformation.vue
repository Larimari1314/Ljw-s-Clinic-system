<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters" >
        <el-form-item style="width: 150px">
          <el-input v-model="filters.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item style="width: 150px">
          <el-select v-model="filters.sex" placeholder="请选择性别">
            <el-option
                v-for="item in sexList"
                :key="item.coding"
                :label="item.value"
                :value="item.coding">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="success" size="small" @click="uploadServer">导入数据</el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" size="small" @click="dataTemplateDownload">数据模板下载</el-button>
        </el-form-item>
        <el-form-item style="float: right">
          <el-button type="primary" size="small" @click="exportData">导出全部数据</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="avatar" label="头像" width="120">
        <template scope="scope">
          <a href="http://localhost:8080/#/patient">
            <img :src="users[scope.$index].avatar" style="border-radius:50%; " width="100" height="100"
                 alt="用户头像">
          </a>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="患者姓名" width="120">
      </el-table-column>
      <el-table-column prop="identityNumber" label="身份证号" width="200">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="100">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="100" sortable>
      </el-table-column>
      <el-table-column prop="birthday" label="生日" width="120" sortable>
      </el-table-column>
      <el-table-column prop="address" label="所在地址" min-width="180" sortable>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="editForm.name" auto-complete="off" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="用户头像" prop="avatar">
          <img :src="editForm.avatar" style="border-radius:50%; " width="100" height="100"
               alt="用户头像">
          <el-upload
              action="http://localhost:8000/clinic/patientInformation/upload"
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
        <el-form-item label="性别" prop="sexCoding">
          <el-select v-model="editForm.sexCoding" placeholder="请选择性别">
            <el-option
                v-for="item in sexList"
                :key="item.coding"
                :label="item.value"
                :value="item.coding">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生日" prop="birthdayCoding">
          <el-date-picker type="date" placeholder="选择日期" v-model="editForm.birthdayCoding"></el-date-picker>
        </el-form-item>
        <el-form-item label="身份证号" prop="identityNumber">
          <el-input type="text" v-model="editForm.identityNumber" disabled style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="家庭地址" prop="address">
          <el-input type="textarea" v-model="editForm.address"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <el-dialog title="上传表单" v-model="uploadServerVisibleUser" :close-on-click-modal="false">
      <el-alert
          title="上传须知"
          type="warning"
          description="上传表单格式务必按照模板，一次仅限上传一个文件,导入数据登录名为表中姓名，密码统一为123456"
          show-icon>
      </el-alert>
      <!--最多上传1个文件，点击之后不立即上传-->
      <el-upload
          class="upload-demo"
          ref="upload"
          drag
          :limit="1"
          action="http://localhost:8000/clinic/patientInformation/uploadToServer"
          :file-list="fileList"
          :on-exceed="handleExceed"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :before-remove="beforeRemove"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传xlsx文件，且文件大小不宜过大</div>
      </el-upload>
      <el-button type="success" plain @click="uploadToServer">确认提交</el-button>
    </el-dialog>
    <!--新增界面-->
    <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false">
      <el-alert
          title="新增数据登录名为表中姓名，密码统一为123456"
          type="warning"
      >
      </el-alert>
      <br/>
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="addForm.name" auto-complete="off" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="addForm.sex" placeholder="请选择性别">
            <el-option
                v-for="item in sexList"
                :key="item.coding"
                :label="item.value"
                :value="item.coding">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="身份证号" prop="identityNumber">
          <el-input v-model="addForm.identityNumber" auto-complete="off" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="生日" prop="birthday">
          <el-date-picker type="date" placeholder="选择日期" v-model="addForm.birthday"></el-date-picker>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input type="textarea" v-model="addForm.address"></el-input>
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
import util from '../../common/js/util'
import {
  addPatientInformation
  , analyseFileByPatientInformation,
  dataTemplateDownloadByPatientInformation,
  deleteByIdsInPatient,
  editPatientInformation,exportDataByPatientInformation,
  findAllPatient,
  findSexCoding
} from '../../api/api';

export default {
  data() {
    return {
      fileList: [],
      uploadServerVisibleUser: false,
      dialogImageUrl: '',
      dialogVisible: false,
      sexList: [{coding: '', value: ''}],
      filters: {
        name: '',
        sex: ''
      },
      users: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],//列表选中列

      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      editFormRules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        sexCoding: [
          {required: true, message: '请选择性别', trigger: 'blur'}
        ], address: [
          {required: true, message: '请输入家庭地址', trigger: 'blur'}
        ],
      },
      //编辑界面数据
      editForm: {},
      addFormVisible: false,//新增界面是否显示
      addLoading: false,
      addFormRules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ], sex: [
          {required: true, message: '请选择性别', trigger: 'blur'}
        ], identityNumber: [
          {
            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/,
            message: '请填写正确的身份证号码',
            trigger: 'blur'
          },
          {required: true, message: '请输入身份证号', trigger: 'blur'}
        ], address: [
          {required: true, message: '请输入家庭地址', trigger: 'blur'}
        ],
      },
      //新增界面数据
      addForm: {
        name: '',
        avatar: '',
        birthday: '',
        sex: '',
        identityNumber: '',
        address: ''
      }

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
    //性别显示转换
    formatSex: function (row, column) {
      return row.sex == 1 ? '男' : row.sex == 0 ? '女' : '未知';
    },
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    //获取用户列表
    getUsers() {
      let para = {
        page: this.page,
        name: this.filters.name,
        sex: this.filters.sex,
        did: this.filters.did,
        registereId: this.filters.registereId
      };
      this.listLoading = true;
      findAllPatient(para).then((res) => {
        if (res.data.msgId === "C200") {
          this.total = res.data.result.total;
          this.users = res.data.result.list;
        } else {
          this.$message.error('数据获取失败，请稍后重试');
        }
        this.listLoading = false;
      });
    },
    //删除
    handleDel: function (index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        //NProgress.start();
        let para = {"ids": [row.id]};
        deleteByIdsInPatient(para).then((res) => {
          this.listLoading = false;
          if (res.data.msgId === "C402") {
            this.$message.error('删除失败！当前选中患者已预约，不可删除');
          } else if (res.data.msgId === "C405") {
            this.$message.error('删除失败！请稍后重试');
          } else if (res.data.msgId === "C200") {
            this.$notify.success({
              title: '成功',
              message: '删除成功'
            });
            this.getUsers();
          }
        })
      }).catch(() => {
        this.listLoading = false;
        this.$message.error('删除失败！请稍后重试');
      });
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
        name: '',
        avatar: '',
        birthday: '',
        sex: '',
        identityNumber: '',
        address: ''
      };
    },
    //编辑
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.$refs.edit.clearFiles();
            this.editLoading = true;
            let para = Object.assign({}, this.editForm);
            para.birthdayCoding = (!para.birthdayCoding || para.birthdayCoding == '') ? '' : util.formatDate.format(new Date(para.birthdayCoding), 'yyyy-MM-dd');
            editPatientInformation(para).then((res => {
              this.editLoading = false;
              if (res.data.msgId === 'C403') {
                this.$message.error('错误：患者姓名已存在，如重名，请在名称后面添加编号(如：王五1、王五2)以便区分');
              }
              if (res.data.msgId === "C200") {
                this.$notify.success({
                  title: '成功',
                  message: '修改成功'
                });
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;
                this.getUsers();
              } else if (res.data.msgId === "C405") {
                this.$notify.error({
                  title: '错误',
                  message: '修改失败，请稍后重试'
                });
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;
                this.getUsers();
              }
            })).catch((error) => {
              this.$notify.error({
                title: '错误',
                message: '修改失败，请稍后重试'
              });
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
            this.addLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.addForm);
            para.birthday = (!para.birthday || para.birthday == '') ? '' : util.formatDate.format(new Date(para.birthday), 'yyyy-MM-dd');
            addPatientInformation(para).then((res) => {
              this.addLoading = false;
              if (res.data.msgId === 'C402') {
                this.$message.error('错误：身份证号已经存在，请检查后重试');
              }
              if (res.data.msgId === 'C403') {
                this.$message.error('错误：患者姓名已存在，如重名，请在名称后面添加编号(如：王五1、王五2)以便区分');
              }
              if (res.data.msgId === 'C200') {
                this.$notify.success({
                  title: '成功',
                  message: '添加成功'
                });
                this.$refs['addForm'].resetFields();
                this.addFormVisible = false;
                this.getUsers();
              }
              if (res.data.msgId === 'C405') {
                this.$message.error('错误：请稍后重试');
              }
            }).catch((res) => {
              this.addLoading = false;
              this.$message.error('错误：请稍后重试');
            })
          });
        }
      });
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
    //批量删除
    batchRemove: function () {
      var ids = this.sels.map(item => item.id);
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        let para = {"ids": ids};
        deleteByIdsInPatient(para).then((res) => {
          this.listLoading = false;
          if (res.data.msgId === "C402") {
            this.$message.error('删除失败！当前选中患者已预约，不可删除');
          } else if (res.data.msgId === "C405") {
            this.$message.error('删除失败！请稍后重试');
          } else if (res.data.msgId === "C200") {
            this.$notify.success({
              title: '成功',
              message: '删除成功'
            });
            this.getUsers();
          }
        })
      }).catch((error) => {
        this.listLoading = false;
        this.$message.error('删除失败！请稍后重试');
      });
    },
    dataTemplateDownload() {
      dataTemplateDownloadByPatientInformation().then((res) => {
        const disposition = res.headers['content-disposition'];
        let fileName = disposition.match(/=(.*)$/)[1];
        let blob = new Blob([res.data])
        let downloadElement = document.createElement('a')
        let href = window.URL.createObjectURL(blob)
        downloadElement.href = href
        downloadElement.download = fileName
        document.body.appendChild(downloadElement)
        downloadElement.click()
        document.body.removeChild(downloadElement)
        window.URL.revokeObjectURL(href)
      })
    },
    exportData(){
      exportDataByPatientInformation().then((res) => {
        const disposition = res.headers['content-disposition'];
        let fileName = disposition.match(/=(.*)$/)[1];
        let blob = new Blob([res.data])
        let downloadElement = document.createElement('a')
        let href = window.URL.createObjectURL(blob)
        downloadElement.href = href
        downloadElement.download = fileName
        document.body.appendChild(downloadElement)
        downloadElement.click()
        document.body.removeChild(downloadElement)
        window.URL.revokeObjectURL(href)
      })
    },
    uploadServer: function () {
      this.uploadServerVisibleUser = true;
    },
    uploadToServer() {
      analyseFileByPatientInformation().then((res) => {
        this.$refs.upload.clearFiles()
        if(res.data.msgId=='C500'){
          this.$notify.error({
            title: '错误',
            message: '数据异常，拒绝添加'
          });
        }else if(res.data.msgId=='C501' || res.data.msgId=='C405'){
          this.$notify.error({
            title: '错误',
            message: '可能的原因是：数据库存在相同数据，拒绝添加'
          });
        }else if(res.data.msgId=='C200'){
          this.uploadServerVisibleUser = false;
          this.$notify.success({
            title: '成功',
            message: '添加成功'
          });
          this.getUsers()
        }else if(res.data.msgId=='C404'){
          this.$notify.error({
            title: '错误',
            message: '请上传文件'
          });
          this.getUsers()
        }
      })
    }
  },
  mounted() {
    this.getUsers();
    findSexCoding().then((res) => {
      if (res.data.msgId === "C200") {
        this.sexList = res.data.result
      } else {
        this.$message.error('数据初始化失败，请稍后重试');
      }
    });
  }
}

</script>

<style scoped>

</style>
