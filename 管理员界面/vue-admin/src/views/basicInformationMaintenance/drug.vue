<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters" >
        <el-form-item style="width: 150px">
          <el-input v-model="filters.drugName" placeholder="药品名称"></el-input>
        </el-form-item>
        <el-form-item style="width: 150px">
          <el-select v-model="filters.specification" placeholder="请选择药品规格">
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
      <el-table-column prop="cover" label="封面" width="200">
        <template scope="scope">
          <a href="http://localhost:8080/#/drug">
            <img :src="users[scope.$index].cover" style="border-radius:50%; " width="150" height="150"
                 alt="药品封面">
          </a>
        </template>
      </el-table-column>
      <el-table-column prop="drugName" label="药品名称" width="200">
      </el-table-column>
      <el-table-column prop="specif" label="药品规格" width="180">
      </el-table-column>
      <el-table-column prop="number" label="药品数量" width="180">
      </el-table-column>
      <el-table-column prop="price" label="药品价格" width="180" sortable>
      </el-table-column>
      <el-table-column label="操作" width="250">
        <template scope="scope">
          <el-tooltip content="关闭之后医生和用户无法查看该药品" placement="top">
          <el-switch
              v-model="0===scope.row.enable"
              on-text="启用"
              @change.native="switching(scope.row)"
              width="60"
              off-text="关闭">
          </el-switch>
          </el-tooltip>&nbsp;&nbsp;&nbsp;&nbsp;
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" :rules="editFormRules" ref="editForm">
        <el-form-item label="药品名称" prop="drugName">
          <el-input v-model="editForm.drugName" auto-complete="off" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="药品封面" prop="cover">
          <img :src="editForm.cover" style="border-radius:50%; " width="100" height="100"
               alt="用户头像">
          <el-upload
              action="http://localhost:8000/clinic/drug/upload"
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
        <el-form-item label="药品规格" prop="specification">
          <el-select v-model="editForm.specification" placeholder="请选择药品规格">
            <el-option
                v-for="item in sexList"
                :key="item.coding"
                :label="item.value"
                :value="item.coding">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="当前数量" prop="number">
          <el-input-number max="999" min="0" placeholder="选择当前数量" v-model="editForm.number" style="width: 200px"></el-input-number>
        </el-form-item>
        <el-form-item label="当前价格" prop="price">
          <el-input-number max="10000" min="1"  v-model="editForm.price" style="width: 200px"></el-input-number>
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
          description="上传表单格式务必按照模板，一次仅限上传一个文件"
          show-icon>
      </el-alert>
      <!--最多上传1个文件，点击之后不立即上传-->
      <el-upload
          class="upload-demo"
          ref="upload"
          drag
          :limit="1"
          action="http://localhost:8000/clinic/drug/uploadToServer"
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
      <el-form :model="addForm" label-width="80px" :rules="addFormRules" ref="addForm">
        <el-form-item label="药品名称" prop="drugname">
          <el-input v-model="addForm.drugname" auto-complete="off" style="width: 200px"></el-input>
        </el-form-item>
        <el-form-item label="药品封面" prop="cover">
          <el-upload
            action="http://localhost:8000/clinic/drug/upload"
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
        <el-form-item label="药品规格" prop="specification">
          <el-select v-model="addForm.specification" placeholder="请选择药品规格">
            <el-option
                v-for="item in sexList"
                :key="item.coding"
                :label="item.value"
                :value="item.coding">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="当前数量" prop="number">
          <el-input-number max="999" min="0" placeholder="选择当前数量" v-model="addForm.number" style="width: 200px"></el-input-number>
        </el-form-item>
        <el-form-item label="当前价格" prop="price">
          <el-input-number max="10000" min="1"  v-model="addForm.price" style="width: 200px"></el-input-number>
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
//import NProgress from 'nprogress'
import {
  addDoctorInformation, addDrug,
  addPatientInformation,
  analyseFileByDoctorInformation, analyseFileByDrug, analyseFileByPatientInformation,
  dataTemplateDownloadByDoctorInformation, dataTemplateDownloadByDrug,
  dataTemplateDownloadByPatientInformation,
  deleteByIdsInDoctor,
  deleteByIdsInPatient, deleteDrugByIds, drugSwitching,
  editDoctorInformation, editDrug,
  editPatientInformation,
  exportDataByDoctorInformation, exportDataByDrug, exportDataByPatientInformation,
  findAllByAllDuty,
  findAllDoctor, findAllDrug,
  findAllPatient,
  findDepartmentCoding, findDrugSpecification,
  findDutyTimeCoding,
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
        drugName: '',
        specification: ''
      },
      users: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],//列表选中列

      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      editFormRules: {
        drugName: [
          {required: true, message: '请输入药品名称', trigger: 'blur'}
        ],
        specification: [
          {required: true, message: '请输入药品规格', trigger: 'blur'}
        ]
      },
      //编辑界面数据
      editForm: {},
      addFormVisible: false,//新增界面是否显示
      addLoading: false,
      addFormRules: {
        drugName: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ], specification: [
          {required: true, message: '请选择性别', trigger: 'blur'}
        ]
      },
      //新增界面数据
      addForm: {
        drugname: '',
        cover: '',
        number: '',
        price: '',
        specification: ''
      }

    }
  },
  methods: {
    switching(row){
      let para={
        id:row.id,
        enable:row.enable
      }
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      drugSwitching(para,configs).then((res)=>{
        if(res.data.msgId=='C200'){
          this.$notify.success({
            title: '成功',
            message: '修改成功'
          });
          this.getUsers()
        }else{
          this.$message.error('修改失败，请稍后重试');
        }
      })
    },
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
        drugName: this.filters.drugName,
        specification: this.filters.specification,
      };
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      this.listLoading = true;
      findAllDrug(para,configs).then((res) => {
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
        let configs={
          headers: {
            token: sessionStorage.getItem('permissionToken')
          }
        };
        this.listLoading = true;
        //NProgress.start();
        let para = {"ids": [row.id]};
        deleteDrugByIds(para,configs).then((res) => {
          this.listLoading = false;
          if (res.data.msgId === "C402") {
            this.$message.error('删除失败！当前选中药品已存在订单中，不可删除');
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
        drugname: '',
        cover: '',
        number: '',
        price: '',
        specification: ''
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
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            editDrug(para,configs).then((res => {
              this.editLoading = false;
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
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
           addDrug(para,configs).then((res) => {
              this.addLoading = false;
              if (res.data.msgId === 'C404') {
                this.$message.error('错误：请上传药品封面');
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
        let configs={
          headers: {
            token: sessionStorage.getItem('permissionToken')
          }
        };
        this.listLoading = true;
        let para = {"ids": ids};
        deleteDrugByIds(para,configs).then((res) => {
          this.listLoading = false;
          if (res.data.msgId === "C402") {
            this.$message.error('删除失败！当前选中药品已在订单中，不可删除');
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
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        },
        responseType: 'blob'
      };
      dataTemplateDownloadByDrug(null,configs).then((res) => {
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
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        },
        responseType: 'blob'
      };
      exportDataByDrug(null,configs).then((res) => {
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
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      analyseFileByDrug(configs).then((res) => {
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
    let configs={
      headers: {
        token: sessionStorage.getItem('permissionToken')
      }
    };
    findDrugSpecification(configs).then((res) => {
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
