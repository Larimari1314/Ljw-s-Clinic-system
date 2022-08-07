<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="searchDrug">
        <el-form-item>
          <el-input v-model="searchDrug.name" placeholder="药品名称"></el-input>
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
    <el-dialog title="上传表单" v-model="uploadServerVisibleDrug" :close-on-click-modal="false">
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
          action="http://localhost:8080/drug/uploadToServer"
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
    <el-table :data="DrugInformation" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column type="index" label="序号" width="100">
      </el-table-column>
      <el-table-column prop="drugImgUrl" width="168" label="药品封面" scope="scope">
        <template scope="scope">
          <img :src="DrugInformation[scope.$index].drugImgUrl" style="border-radius:10%; " width="100" height="100"
               alt="药品封面">
        </template>
      </el-table-column>
      <el-table-column prop="drugName" label="药品名称" width="100">
      </el-table-column>
      <el-table-column prop="effect" label="药品功效" width="260">
      </el-table-column>
      <el-table-column prop="originalPrice" label="药品原价/￥" width="140" sortable>
      </el-table-column>
      <el-table-column prop="currentPrice" label="药品现价/￥" width="140" sortable>
      </el-table-column>
      <el-table-column prop="quantity" label="现存数量" width="140" sortable>
      </el-table-column>
      <el-table-column label="操作" width="200" scope="scope">
        <template scope="scope">
          <el-tooltip content="关闭后用户、医生无法查看药品" placement="top">
            <el-switch
                v-model="DrugInformation[scope.$index].exhibit"
                on-text=""
                off-text=""
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-value="100"
                inactive-value="0"
                @change=changeSwing($event,scope.row)
            >
            </el-switch>
          </el-tooltip>&nbsp;&nbsp;&nbsp;
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)" type="info">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="3" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormDrugVisible" :close-on-click-modal="false">
      <el-form :model="editFormDrug" label-width="80px" :rules="editFormDrugRules" ref="editFormDrug">
        <el-form-item label="药品名称" prop="drugName">
          <el-input v-model="editFormDrug.drugName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="药品现价" prop="currentPrice">
          <el-input v-model="editFormDrug.currentPrice" auto-complete="off" type="number"></el-input>
        </el-form-item>
        <el-form-item label="现存数量" prop="quantity">
          <el-input v-model="editFormDrug.quantity" auto-complete="off" type="number"></el-input>
        </el-form-item>
        <el-form-item label="药品功效">
          <el-input type="textarea" v-model="editFormDrug.effect"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormDrugVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--    新增界面-->
    <el-dialog title="新增" v-model="addFormdrugVisible" :close-on-click-modal="false">
      <el-form :model="addFormDrug" label-width="80px" :rules="addFormDrugRules" ref="addFormDrug">
        <el-form-item label="药品名称" prop="drugName" :rules="[
                      { required: true, message: '药品名称不能为空'}
                      ]">
          <el-input v-model="addFormDrug.drugName" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="药品封面" prop="imageUrl">
          <el-upload
              accept="image/jpg"
              class="upload-demo"
              action="http://localhost:8080/drug/uploadToServer/"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemoveImg"
              :limit="1"
              :on-exceed="handleExceed"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible" >
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </el-form-item>
        <el-form-item label="药品原价" prop="originalPrice" :rules="[
                      { required: true, message: '药品原价不能为空'},
                       {pattern: /^([1-9]\d*(\.\d*[1-9][0-9])?)|(0\.\d*[1-9][0-9])|(0\.\d*[1-9])$/,message: '金额不能为0'}
                      ]">
          <el-input v-model.number="addFormDrug.originalPrice" type="number" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="药品现价" prop="currentPrice" :rules="[
                      { required: true, message: '药品现价不能为空'},
                      {pattern: /^([1-9]\d*(\.\d*[1-9][0-9])?)|(0\.\d*[1-9][0-9])|(0\.\d*[1-9])$/,message: '金额不能为0'}
                      ]">
          <el-input v-model.number="addFormDrug.currentPrice" type="number" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否启用" :rules="[
                      { required: true, message: '请选择启用或者禁用'}]" prop="exhibit">
          <el-radio-group v-model.number="addFormDrug.exhibit">
            <el-radio class="radio" :label=1>启用</el-radio>
            <el-radio class="radio" :label=0>禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="现存数量" :rules="[
                      { required: true, message: '药品现存数量不能为空'}]" prop="quantity">
          <el-input type="number" v-model.number="addFormDrug.quantity"></el-input>
        </el-form-item>
        <el-form-item label="药品功效" :rules="[
                      { required: true, message: '药品功效不能为空'}]" prop="effect">
          <el-input type="textarea" v-model="addFormDrug.effect"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormdrugVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import util from '../../common/js/util'
//import NProgress from 'nprogress'
import {
  addDrug, analyseFileDrug,
  drugFindByName,
  editDrug,
  exitDrugExhibit,
  exportDataDrug,
  getDrugListPage,
  templateDownloadDrug
} from '../../api/api';

export default {
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      value: '',
      fileListDoctor: [],
      searchDrug: {
        name: ''
      },
      DrugInformation: [{
        id: '',
        drugName: '',
        effect: '',
        originalPrice: undefined,
        drugImgUrl: '',
        currentPrice: undefined,
        quantity: undefined,
        exhibit: 1
      }],
      total: '',
      page: 1,
      uploadServerVisibleDrug: false,
      listLoading: false,
      sels: [],//列表选中列

      editFormDrugVisible: false,//编辑界面是否显示
      editLoading: false,
      editFormDrugRules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ]
      },

      //编辑界面数据
      editFormDrug: {
        id: '',
        drugName: '',
        effect: '',
        originalPrice: undefined,
        drugImgUrl: '',
        currentPrice: undefined,
        quantity: undefined,
        exhibit: 1
      },

      addFormdrugVisible: false,//新增界面是否显示
      addLoading: false,
      addFormDrugRules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ]
      },
      //新增界面数据
      addFormDrug: {
        id: '',
        drugName: '',
        effect: '',
        originalPrice: undefined,
        drugImgUrl: '',
        currentPrice: undefined,
        quantity: undefined,
        exhibit: 1
      }

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
    //性别显示转换
    handleCurrentChange(val) {
      this.page = val;
      this.getDrug();
    },
    changeSwing:function (text,row) {
      let params;
      if(text){
        params = {id: row.id,exhibit:1};
      }else {
        params = {id: row.id,exhibit:0};
      }
      let _this=this
      exitDrugExhibit(params).then((res)=>{
        if(res.data.code==200){
          _this.getDrug()
          _this.$notify({
            title: '成功',
            message: res.data.message,
            type: 'success'
          });
        }else {
          _this.$notify.error({
            title: '错误',
            message: res.data.message
          });
        }
      })
    },
    //获取用户列表
    getDrug() {
      let para = {
        page: this.page
      };
      this.listLoading = true;
      //NProgress.start();
      getDrugListPage(para).then((res) => {
        // let data = JSON.parse(res.data);
        // alert(data)
        if(res.data.code==200){
          this.total = res.data.data.total;
          let drugInformation = res.data.data.list;
          drugInformation.forEach((item,index, arr) => {
            if (item.exhibit == 1) {
              item.exhibit = true
            } else {
              item.exhibit = false
            }
          });
          this.DrugInformation = drugInformation
          this.listLoading = false;
        }else{
          this.$notify.error({
            title: '错误',
            message: res.data.message
          });

        }
        //NProgress.done();
      });
    },
    findByName() {
      let para = {
        page: this.page,
        name: this.searchDrug.name
      };
      let _this = this;
      drugFindByName(para).then(function (res) {
            if(res.data.code==200){
              _this.total = res.data.data.total;
              let drugInformation = res.data.data.list;
              drugInformation.forEach((item,index, arr) => {
                if (item.exhibit == 1) {
                  item.exhibit = true
                } else {
                  item.exhibit = false
                }})
              _this.DrugInformation = drugInformation
              _this.listLoading = false;
              }else{
              this.$notify.error({
                title: '错误',
                message: res.data.message
              });
            }})
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormDrugVisible = true;
      this.editFormDrug = Object.assign({}, row);
    },
    uploadServer: function () {
      this.uploadServerVisibleDrug = true;
    },
    //显示新增界面
    handleAdd: function () {
      this.addFormdrugVisible = true;
      this.addFormDrug = {
        id: '',
        drugName: '',
        effect: '',
        originalPrice: '',
        drugImgUrl: '',
        currentPrice: '',
        quantity: '',
        exhibit: 1
      }
    },
    //编辑
    editSubmit: function () {
      this.$refs.editFormDrug.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            //NProgress.start();
            if(this.editFormDrug.exhibit){
              this.editFormDrug.exhibit=1
            }else {
              this.editFormDrug.exhibit=0
            }
            let para = Object.assign({}, this.editFormDrug);
            let _this=this
            editDrug(para).then((res) => {
              this.editLoading = false;
              _this.editFormDrugVisible = false;
              _this.getDrug();
              //NProgress.done();
              this.$notify({
                title: '成功',
                message: res.data.message,
                type: 'success'
              });
            });
          });
        }
      });
    },
    //新增
    addSubmit: function () {
      this.$refs.addFormDrug.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addLoading = true;
            let _this = this
            let para = Object.assign({}, this.addFormDrug);
            addDrug(para).then((res) => {
              _this.addLoading = false;
              //NProgress.done();
              _this.$message({
                message: '提交成功',
                type: 'success'
              });
              this.$refs['addFormDrug'].resetFields();
              this.addFormdrugVisible = false;
              this.getDrug();
            });
          });
        }
      });
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
     exportData() {
      exportDataDrug().then((res) => {
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
    }, dataTemplateDownload() {
      templateDownloadDrug().then((res) => {
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
      analyseFileDrug().then((res) => {
        if (res.data.message === "error") {
          this.$message.error(res.data.message);
        } else if (res.data.message === "success") {
          this.$message({
            message: res.data.message,
            type: 'success'
          });
        }
      })
    }, handleRemoveImg(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    }
  },
  created() {
    this.getDrug();
  }
}

</script>

<style scoped>

</style>
