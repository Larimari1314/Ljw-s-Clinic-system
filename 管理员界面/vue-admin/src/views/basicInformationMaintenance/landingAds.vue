<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item style="width: 200px">
          <el-input v-model="filters.name" placeholder="广告网址"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="addLandingAds">添加广告</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="id" label="id" width="100">
      </el-table-column>
      <el-table-column prop="imgurl" label="广告图片" width="400">
        <template scope="scope">
          <a href="http://localhost:8080/#/landingAds">
            <img :src="users[scope.$index].imgurl" width="350" height="150"
                 alt="用户头像">
          </a>
        </template>
      </el-table-column>
      <el-table-column prop="ads" label="广告链接" width="500">
        <template scope="scope">
          <iframe style="width:80%" :src="users[scope.$index].ads" ref="main_page_iframe" id="iframeId"
                  sandbox="allow-scripts allow-same-origin allow-popups"></iframe>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="190">
        <template scope="scope">
          <el-button size="small" @click="lookOrderFrom(scope.$index, scope.row)">编辑广告</el-button>
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
    <el-dialog title="添加广告" v-model="addLandingAdsVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="120px" :rules="addFormRules" ref="addForm">
        <el-form-item label="广告封面" prop="imgurl">
          <el-upload
              action="http://localhost:8000/clinic/LandingAds/upload"
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
        <el-form-item label="广告链接地址" prop="ads">
          <el-input v-model="addForm.ads" auto-complete="off" style="width: 200px"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addLandingAdsVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
    <el-dialog title="查看详情" v-model="orderVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="95px" :rules="editFormRules" ref="editForm">
        <el-form-item label="广告图片" prop="imgurl">
          <img :src="editForm.imgurl" width="350" height="150"
               alt="用户头像">
          <el-upload
              action="http://localhost:8000/clinic/LandingAds/upload"
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
        <el-form-item label="广告地址" prop="ads">
          <el-input v-model="editForm.ads"></el-input>
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
import {addLandingAds, deleteLandingAds, editLandingAds, findAllLandingAds} from '../../api/api';

export default {
  data() {
    return {
      addLoading: false,
      addFormRules: {
        ads: [
          {
            pattern: /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/,
            message: '请填写正确的链接地址',
            trigger: 'blur'
          },
          {required: true, message: '请输入链接地址', trigger: 'blur'}
        ]
      },
      addLandingAdsVisible: false,
      editForm: '',
      bill: false,
      sameOrder: true,
      totalPrice: '',
      orderList: '',
      orderFrom: [],
      orderVisible: false,
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: false,
      filters: {
        name: ''
      },
      editFormRules: {
        ads: [
          {
            pattern: /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/,
            message: '请填写正确的链接地址',
            trigger: 'blur'
          },
          {required: true, message: '请输入链接地址', trigger: 'blur'}
        ]
      },
      addForm: [],
      users: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],//列表选中列
    }
  },
  methods: {
    addSubmit() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.addLoading = true;
            let para = Object.assign({}, this.addForm);
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            addLandingAds(para,configs).then((res)=>{
              if (res.data.msgId === "C405") {
                this.$message.error('添加失败！请稍后重试');
              } else if (res.data.msgId === "C200") {
                this.addLoading = false;
                this.addLandingAdsVisible=false;
                this.$notify.success({
                  title: '成功',
                  message: '添加成功'
                });
                this.getUsers();
              }else if (res.data.msgId === "C404") {
                this.$message.error('请上传封面');
              }
            })
          })
        }
      })
    },
    addLandingAds() {
      this.addLandingAdsVisible = true;
    },
    handleDel(index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        //NProgress.start();
        let para = {"ids": [row.id]};
        let configs={
          headers: {
            token: sessionStorage.getItem('permissionToken')
          }
        };
        deleteLandingAds(para,configs).then((res) => {
          this.listLoading = false;
          if (res.data.msgId === "C405") {
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
    selsChange: function (sels) {
      this.sels = sels;
    },
    batchRemove: function () {
      var ids = this.sels.map(item => item.id);
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        let para = {"ids": ids};
        let configs={
          headers: {
            token: sessionStorage.getItem('permissionToken')
          }
        };
        deleteLandingAds(para,configs).then((res) => {
          this.listLoading = false;
          if (res.data.msgId === "C405") {
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
      });
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
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    //获取用户列表
    getUsers() {
      let para = {
        page: this.page,
        name: this.filters.name
      };
      this.listLoading = true;
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      findAllLandingAds(para,configs).then((res) => {
        if (res.data.msgId === "C200") {
          this.total = res.data.result.total;
          this.users = res.data.result.list;
        } else {
          this.$message.error('数据获取失败，请稍后重试');
        }
        this.listLoading = false;
      });
    },
    //查看订单情况
    lookOrderFrom: function (index, row) {
      this.orderVisible = true;
      this.editForm = Object.assign({}, row);
    },
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            editLandingAds(this.editForm,configs).then((res) => {
              if (res.data.msgId === 'C200') {
                this.orderVisible = false;
                this.getUsers();
                this.$notify.success({
                  title: '成功',
                  message: '修改成功'
                });
              } else if (res.data.msgId === 'C405') {
                this.$message.error('错误,请稍后重试');
              }
            })
          })
        }
      })
    },
  },
  mounted() {
    this.getUsers();
    window.onresize = () => {
      let that = this
      let ifdoc = that.$refs.main_page_iframe.contentDocument
      let cHeight = Math.max(ifdoc.body.clientHeight, ifdoc.documentElement.clientHeight)
      let sHeight = Math.max(ifdoc.body.scrollHeight, ifdoc.documentElement.scrollHeight)
      that.$refs.main_page_iframe.style.height = Math.max(cHeight, sHeight) + 'px'
    }
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
