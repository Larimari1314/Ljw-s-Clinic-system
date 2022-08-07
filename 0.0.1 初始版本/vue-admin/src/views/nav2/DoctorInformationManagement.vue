<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="searchDoctor">
        <el-form-item>
          <el-input v-model="searchDoctor.name" placeholder="姓名"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="findByName">查询</el-button>
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
    <!--导入数据到服务器解析-->
    <el-dialog title="上传表单" v-model="uploadServerVisibleDoctor" :close-on-click-modal="false">
      <el-alert
          title="上传须知"
          type="warning"
          description="上传表单格式务必按照模板，一次仅限上传一个文件"
          show-icon>
      </el-alert>
      <!--最多上传1个文件，点击之后不立即上传-->
      <el-upload
          class="upload-demo"
          ref="upload"
          drag
          :limit="1"
          action="http://localhost:8080/doctor/uploadToServer"
          :file-list="fileListDoctor"
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
    <!--列表-->
    <el-table :data="DoctorInformation" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" label="序号" width="100">
      </el-table-column>
      <el-table-column prop="doctorName" label="医生姓名" width="130">
      </el-table-column>
      <el-table-column prop="department.departmentName" label="所在科目" width="130">
      </el-table-column>
      <el-table-column prop="doctorPhone" label="电话" width="140">
      </el-table-column>
      <el-table-column prop="doctorPassword" label="密码" width="120">
      </el-table-column>
      <el-table-column prop="salary" label="薪资" width="140" sortable>
      </el-table-column>
      <el-table-column prop="doctorAge" label="年龄" width="100" sortable>
      </el-table-column>
      <el-table-column prop="doctorAddr" label="家庭住址" min-width="170">
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
    <el-dialog title="编辑" v-model="editFormDoctorVisibleDoctor" :close-on-click-modal="false">
      <el-form :model="editFormDoctor" label-width="80px" :rules="editFormDoctorRules" ref="editFormDoctor">
        <el-form-item label="电话号码" prop="doctorPhone" :rules="[
                      { pattern:  /^1[3456789]\d{9}$/,
                        message: '请填写正确的电话号码',
                        trigger: 'blur'}
                      ]">
          <el-input v-model="editFormDoctor.doctorPhone" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="editFormDoctor.doctorAddr"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormDoctorVisibleDoctor = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--    新增界面-->
    <el-dialog title="新增" v-model="addFormDoctorVisible" :close-on-click-modal="false">
      <el-form :model="addFormDoctor" label-width="80px" :rules="addFormDoctorRules" ref="addFormDoctor">
        <el-form-item label="真实姓名" prop="doctorName" :rules="[
                      { required: true, message: '真实姓名不能为空'}
                      ]">
          <el-input v-model="addFormDoctor.doctorName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话号码" prop="doctorPhone" :rules="[
                      { pattern:  /^1[3456789]\d{9}$/,
                        message: '请填写正确的电话号码',
                        trigger: 'blur'}
                      ]">
          <el-input v-model="addFormDoctor.doctorPhone" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="选择科室" prop="department">
          <el-select v-model="addFormDoctor.department" placeholder="请选择选择科室" clearable
                     :style="{width: '100%'}">
            <el-option v-for="(item, index) in departmentOptions" :key="index"
                       :label="item.departmentName" :value="item.id" :disabled="false"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="生日" prop="doctorBirth">
          <el-date-picker
              v-model="addFormDoctor.doctorBirth"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="addFormDoctor.doctorSex">
            <el-radio class="radio" :label="1">男</el-radio>
            <el-radio class="radio" :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="登陆密码" :rules="[
                          { required: true, message: '登陆密码不能为空'}
                          ]">
          <el-input type="password" v-model="addFormDoctor.doctorPassword"></el-input>
        </el-form-item>
        <el-form-item label="薪资">
          <el-input type="number" v-model="addFormDoctor.salary"></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input type="textarea" v-model="addFormDoctor.doctorAddr"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormDoctorVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import util from '../../common/js/util'
//import NProgress from 'nprogress'
import {
  addDoctor,
  analyseFileDoctor,
  batchRemoveDoctor,
  DoctorFindByName,
  editDoctor,
  exportDataDoctor,
  getDoctorListPage,
  removeDoctor,
  templateDownloadDoctor
} from '../../api/api';

export default {
  data() {
    return {
      fileListDoctor: [],
      searchDoctor: {
        name: ''
      },
      DoctorInformation: [{
        id: '',
        doctorName: '',
        doctorPhone: '',
        doctorPassword: '',
        avatarUrl: '',
        salary: '',
        doctorAge: 0,
        doctorSex: -1,
        doctorBirth: '',
        doctorAddr: '',
        department: undefined
      }],
      total: 20,
      page: 1,
      uploadServerVisibleDoctor: false,
      listLoading: false,
      sels: [],//列表选中列

      editFormDoctorVisibleDoctor: false,//编辑界面是否显示
      editLoading: false,
      editFormDoctorRules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ]
      },

      //编辑界面数据
      editFormDoctor: {
        id: 0,
        doctorName: '',
        doctorPhone: '',
        doctorPassword: '',
        avatarUrl: '',
        salary: '',
        doctorAge: 0,
        doctorSex: -1,
        doctorBirth: '',
        doctorAddr: '',
      },

      addFormDoctorVisible: false,//新增界面是否显示
      addLoading: false,
      addFormDoctorRules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ]
      },
      //新增界面数据
      addFormDoctor: {
        id: 0,
        doctorName: '',
        doctorPhone: '',
        department: undefined,
        doctorPassword: '',
        avatarUrl: '',
        salary: '',
        doctorAge: 0,
        doctorSex: -1,
        doctorBirth: '',
        doctorAddr: '',
      },
      departmentOptions: [{
        "departmentName": "放射科",
        "id": 1
      }, {
        "departmentName": "皮肤科",
        "id": 10
      }, {
        "departmentName": "妇科",
        "id": 11
      }, {
        "departmentName": "临床科",
        "id": 2
      }, {
        "departmentName": "口腔科",
        "id": 3
      }, {
        "departmentName": "神经科",
        "id": 4
      }, {
        "departmentName": "体检科",
        "id": 5
      }, {
        "departmentName": "男科",
        "id": 6
      }, {
        "departmentName": "内科",
        "id": 7
      }, {
        "departmentName": "外科",
        "id": 8
      }, {
        "departmentName": "眼科",
        "id": 9
      }],

    }
  },
  methods: {

    handleRemove(file, fileListDoctor) {
      console.log(file, fileListDoctor);
    },
    handlePreview(file) {
      console.log(file);
    },
    beforeRemove(file, fileListDoctor) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },
    passwordValidation() {
      if (this.addFormDoctor.password !== this.checkPass) {
        callback(new Error('两次密码不一致'));
      }
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
        page: this.page
      };
      this.listLoading = true;
      //NProgress.start();
      getDoctorListPage(para).then((res) => {
        // let data = JSON.parse(res.data);
        // alert(data)
        this.total = res.data.total;
        this.DoctorInformation = res.data.list;
        this.listLoading = false;
        //NProgress.done();
      });
    },
    findByName() {
      let para = {
        page: this.page,
        name: this.searchDoctor.name
      };
      let _this = this;
      DoctorFindByName(para).then(function (res) {
        _this.total = res.data.total;
        _this.DoctorInformation = res.data.list;
        _this.listLoading = false;
      })
    },
    //删除
    handleDel: function (index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        //NProgress.start();
        let para = {id: row.id};
        removeDoctor(para).then((res) => {
          this.listLoading = false;
          //NProgress.done();
          this.$message({
            message: res.data,
            type: res.data
          });
          this.getUsers();
        });
      }).catch(() => {

      });
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormDoctorVisibleDoctor = true;
      this.editFormDoctor = Object.assign({}, row);
    }, uploadServer: function () {
      this.uploadServerVisibleDoctor = true;
    },
    //显示新增界面
    handleAdd: function () {
      this.addFormDoctorVisible = true;
      this.addFormDoctor = {
        id: 0,
        doctorName: '',
        doctorPhone: '',
        department: undefined,
        doctorPassword: '',
        avatarUrl: '',
        salary: '',
        doctorAge: 0,
        doctorSex: -1,
        doctorBirth: '',
        doctorAddr: '',
      }
    },
    //编辑
    editSubmit: function () {
      this.$refs.editFormDoctor.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.editFormDoctor);
            editDoctor(para).then((res) => {
              this.editLoading = false;
              //NProgress.done();
              this.$message({
                message: '提交成功',
                type: res.data
              });
              this.$refs['editFormDoctor'].resetFields();
              this.editFormDoctorVisibleDoctor = false;
              this.getUsers();
            });
          });
        }
      });
    },
    //新增
    addSubmit: function () {
      this.$refs.addFormDoctor.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addLoading = true;
            //NProgress.start();
            let departmentOptionsNew={id:this.addFormDoctor.department,departmentName:'department',imgUrl:'',departmentIntroduce:''}
            let _this = this
            let para = Object.assign({}, this.addFormDoctor);
            para.doctorBirth = (!para.doctorBirth || para.doctorBirth === '') ? '' : util.formatDate.format(new Date(para.doctorBirth), 'yyyy-MM-dd');
            para.department=departmentOptionsNew
            addDoctor(para).then((res) => {
              this.addLoading = false;
              //NProgress.done();
              this.$message({
                message: '提交成功',
                type: 'success'
              });
              this.$refs['addFormDoctor'].resetFields();
              this.addFormDoctorVisible = false;
              this.getUsers();
            });
          });
        }
      });
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
    //批量删除
    batchRemove: function () {
      var ids = this.sels.map(item => item.id).toString();
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        //NProgress.start();
        let para = {ids: ids};
        let _this = this
        batchRemoveDoctor(para).then((res) => {
          _this.listLoading = false;
          //NProgress.done();
          _this.$message({
            message: '删除成功',
            type: res.data
          });
          _this.getUsers();
        }).catch(function (error) {
          _this.$message({
            message: '删除失败，请稍后尝试',
            type: 'error'
          });
        })
      }).catch(() => {
      });
    }, exportData() {
      exportDataDoctor().then((res) => {
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
    dataTemplateDownload() {
      templateDownloadDoctor().then((res) => {
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
    }, uploadToServer: function () {
      //将数据提交到后端执行解析方法
      // this.$refs.upload.submit();
      this.analyseFile()
    }, analyseFile: function () {
      analyseFileDoctor().then((res) => {
        if (res.data.message === "error") {
          this.$message.error(res.data.displayInformation);
        } else if (res.data.message === "success") {
          this.$message({
            message: res.data.displayInformation,
            type: 'success'
          });
        }
      })
    }
    ,
    handleExceed(files, fileListDoctor) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileListDoctor.length} 个文件`);
    }
  },
  created() {
    this.getUsers();
  }
}

</script>

<style scoped>

</style>