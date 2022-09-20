<template>
  <section>
    <br/>
    <hr/>
    <br/>
    <!--列表-->
    <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column prop="id" label="挂号id" width="100">
      </el-table-column>
      <el-table-column prop="name" label="挂号级别" width="490">
      </el-table-column>
      <el-table-column prop="price" label="挂号级别金额" width="500">
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button size="small" @click="lookOrderFrom(scope.$index, scope.row)">编辑挂号级别</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="10" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <el-dialog title="查看详情" v-model="orderVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="95px" :rules="editFormRules" ref="editForm">
        <el-form-item label="挂号名称" prop="name" >
          <el-input v-model="editForm.name" auto-complete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="挂号金额" prop="price">
          <el-input v-model="editForm.price"></el-input>
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
  editGeneral, editRegistrationLevel,
  findAllByAllDuty, findAllOrderFrom, findAllRegistrationLevel,
  findDepartmentCoding, findOrderDrugList, findPayInformation, getAllGeneral, getTotalPrice
} from '../../api/api';

export default {
  data() {
    return {
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
      filters: {
        value:''
      },
      editFormRules: {
        coding: [
          {required: true, message: '请提交编码', trigger: 'blur'}
        ],
        value: [
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
      this.listLoading = true;
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      findAllRegistrationLevel(null,configs).then((res) => {
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
            editRegistrationLevel(this.editForm,configs).then((res)=>{
              if (res.data.msgId === 'C200') {
                this.orderVisible = false;
                this.getUsers();
                this.$notify.success({
                  title: '成功',
                  message: '修改成功'
                });
              }else  if (res.data.msgId === 'C403') {
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
