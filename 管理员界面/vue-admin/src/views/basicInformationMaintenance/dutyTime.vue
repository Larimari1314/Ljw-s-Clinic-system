<template>
  <section>
    <!--工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="医生名称" style="width: 150px"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.department" placeholder="请选择科室类别" style="width: 150px">
            <el-option
                v-for="item in departments"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="filters.registered" placeholder="请选择挂号级别" style="width: 150px">
            <el-option
                v-for="item in registeres"
                :key="item.id"
                :label="item.value"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getUsers">查询</el-button>
        </el-form-item>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-form-item style="float: right">
          <el-button type="success" v-on:click="SetShiftTime">查看排班时间</el-button>
          <el-button type="success" v-on:click="setDoctorTime">统一排班规则</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--列表-->
    <el-table :data="users" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" label="id" width="55">
      </el-table-column>
      <el-table-column prop="avatar" label="头像" width="120">
        <template scope="scope">
          <a href="http://localhost:8080/#/dutyTime">
            <img :src="users[scope.$index].avatar" style="border-radius:50%; " width="100" height="100"
                 alt="用户头像">
          </a>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="医生姓名" width="120">
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="120" sortable>
      </el-table-column>
      <el-table-column prop="departmentName" label="所在科室" width="100">
      </el-table-column>
      <el-table-column prop="schedules" label="班次" width="100" >
      </el-table-column>
      <el-table-column prop="regis" label="挂号级别" width="100">
      </el-table-column>
      <el-table-column prop="address" label="所在地址" min-width="180" >
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--工具条-->
    <el-col :span="24" class="toolbar">
      <el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total"
                     style="float:right;">
      </el-pagination>
    </el-col>

    <!--编辑界面-->
    <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false">
      <el-form :model="editForm" label-width="80px" ref="editForm">
        <el-form-item label="医生姓名" prop="name" :rules="[
                      { required: true, message: '姓名不得为空'}]">
          <el-input v-model="editForm.name" auto-complete="off" disabled style="width: 225px"></el-input>
        </el-form-item>
        <el-form-item label="班次" prop="scheduledCoding" :rules="[
                      { required: true, message: '请选择班次'}]">
          <el-select v-model="editForm.scheduledCoding" placeholder="请选择">
            <el-option
                v-for="item in schedules"
                :key="item.id"
                :label="item.value"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在科室" prop="did" :rules="[
                      { required: true, message: '请选择科室'}]">
          <el-select v-model="editForm.did" placeholder="请选择" disabled>
            <el-option
                v-for="item in departments"
                :key="item.id"
                :label="item.name"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
      </div>
    </el-dialog>

    <!--新增界面-->
    <el-dialog title="查看排班时间" v-model="shiftTime" :close-on-click-modal="false">
      <el-table :data="scheduleList" highlight-current-row v-loading="shiftTimeLoaing" style="width: 100%;">
        <el-table-column prop="id" label="排班id" width="180">
        </el-table-column>
        <el-table-column prop="value" label="班次名称" width="180" sortable>
        </el-table-column>
        <el-table-column prop="startTime" label="开始时间" width="180">
        </el-table-column>
        <el-table-column prop="endTime" label="结束时间" width="180">
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="shiftTime = false">取消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="统一设置时间表" v-model="doctorTime" :close-on-click-modal="false">
      <el-form :inline="true" :model="doctorTimes" label-width="80px" ref="doctorTimes">
        <el-form-item label="科室类别">
          <el-select v-model="doctorTimes.department" placeholder="请选择">
              <el-option
                  v-for="item in departments"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
              </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="挂号级别">
          <el-select v-model="doctorTimes.registered" placeholder="请选择">
            <el-option
                v-for="item in registeres"
                :key="item.id"
                :label="item.value"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="doctorTimes.sex" placeholder="请选择">
            <el-option
                v-for="item in Genders"
                :key="item.coding"
                :label="item.value"
                :value="item.coding">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="年龄区间" prop="startAge"
                      :rules="{min: 0, max: this.doctorTimes.endAge, message: '大小要在0到'+this.doctorTimes.endAge+'之间', trigger: 'blur' }">
          <el-input v-model="doctorTimes.startAge" type="number" placeholder="起始" style="width: 80px"
                    @blur="numberSaze"></el-input>
          ——
        </el-form-item>
        <el-form-item>
          <el-input v-model="doctorTimes.endAge" type="number" placeholder="终止" style="width: 80px"
                    @blur="numberSaze"></el-input>
        </el-form-item>
        <el-form-item label="班次设定" prop="scheduleRule" :rules="[
                      { required: true, message: '班次不得为空'}]">
          <el-select v-model="doctorTimes.scheduleRule" placeholder="请选择">
            <el-option
                v-for="item in schedules"
                :key="item.id"
                :label="item.value"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="doctorTime = false">取消</el-button>
        <el-button type="primary" @click.native="timeSubmit" :loading="timeLoading">提交</el-button>
      </div>
    </el-dialog>
  </section>
</template>

<script>
import {editDoctorDuty, editUniteDoctorDutyTime, findAllByAllDuty,findAllDoctorDuty,findDepartmentCoding, findDutyTime, findDutyTimeCoding,
   findSexCoding
} from '../../api/api';

export default {
  data() {
    return {
      timeLoading: false,
      Genders: '',
      doctorTimes: {
        sex: '',
        department: '',
        registered: '',
        scheduleRule: '',
        startAge: 0,
        endAge: 100
      },
      doctorTime: false,
      scheduleList: '',
      schedules: '',
      departments: '',
      registeres: '',
      filters: {
        name: '',
        department: '',
        registered: ''
      },
      users: [],
      total: 0,
      page: 1,
      listLoading: false,
      sels: [],//列表选中列

      editFormVisible: false,//编辑界面是否显示
      editLoading: false,
      //编辑界面数据
      editForm: {},

      shiftTime: false,//新增界面是否显示
      shiftTimeLoaing: false,
      //新增界面数据
    }
  },
  methods: {
    handleCurrentChange(val) {
      this.page = val;
      this.getUsers();
    },
    //获取用户列表
    getUsers() {
      let para = {
        page: this.page,
        name: this.filters.name,
        did: this.filters.department,
        registereId: this.filters.registered
      };
      this.listLoading = true;
      //NProgress.start();
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      findAllDoctorDuty(para,configs).then((res) => {
        if (res.data.msgId == 'C200') {
          this.listLoading = false;
          this.users = res.data.result.list
          this.total = res.data.result.total
        }

      });
    },
    //显示编辑界面
    handleEdit: function (index, row) {
      this.editFormVisible = true;
      this.editForm = Object.assign({}, row);
    },
    //编辑
    editSubmit: function () {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.editForm);
            let configs={
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            editDoctorDuty(para,configs).then((res) => {
              if (res.data.msgId == 'C200') {
                this.$notify.success({
                  title: '成功',
                  message: '修改成功'
                });
                this.editLoading = false;
                this.$refs['editForm'].resetFields();
                this.editFormVisible = false;
                this.getUsers();
              } else {
                this.$notify.error({
                  title: '失败',
                  message: '修改失败'
                });
              }
            })
          });
        }
      });
    },
    selsChange: function (sels) {
      this.sels = sels;
    },
    //批量删除
    Obtain() {
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      findDepartmentCoding(configs).then((res) => {
        this.departments = res.data.result
      });
      findDutyTimeCoding(configs).then((res) => {
        this.registeres = res.data.result
      });
      findAllByAllDuty(configs).then((res)=>{
        this.schedules = res.data.result
      })
      findDutyTime(null,configs).then((res)=>{
        this.scheduleList = res.data.result
      })
      findSexCoding(configs).then((res)=>{
        this.Genders = res.data.result
      })
    },
    SetShiftTime() {
      this.shiftTime = true;
    },
    setDoctorTime() {
      this.doctorTime = true
    },
    timeSubmit() {
      // alert(JSON.stringify(this.doctorTimes))
      this.$confirm('确认提交吗？', '提示', {
        type: 'warning'
      }).then(() => {
        let configs={
          headers: {
            token: sessionStorage.getItem('permissionToken')
          }
        };
        editUniteDoctorDutyTime(this.doctorTimes,configs).then((res) => {
          if (res.data.msgId == 'C200') {
            this.doctorTime = false;
            this.$notify.success({
              title: '成功',
              message: '修改成功'
            });
            this.getUsers()
            this.doctorTimes = {
              sex: '',
              department: '',
              registered: '',
              scheduleRule: '',
              startAge: 0,
              endAge: 100
            }
          } else {
            this.$notify.error({
              title: '失败',
              message: '修改失败'
            });
          }
        })
      })
    },
    numberSaze() {
      if (this.doctorTimes.startAge > this.doctorTimes.endAge) {
        alert("年龄区间错误")
      }
    }
  },
  mounted() {
    this.getUsers();
    this.Obtain();
  }
}

</script>

<style scoped>

</style>
