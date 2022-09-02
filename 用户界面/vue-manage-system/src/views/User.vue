<template>
  <section>
    <div>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card shadow="hover">
            <template #header>
              <div class="clearfix">
                <span>登录信息</span>
              </div>
            </template>
            <div class="info">
              <el-form :model="formLogin" ref="vForm" :rules="rulesLogin" label-position="left" label-width="80px"
                       size="default" @submit.native.prevent>
                <div class="static-content-item">
                  <el-alert title="两次密码输入务必相同" type=error :closable="true" :center="true" :show-icon="false"
                            effect="light">
                  </el-alert>
                  <br/>
                </div>
                <el-form-item label="旧密码" prop="password" class="required label-center-align">
                  <el-input v-model="formLogin.password" type="password" :show-password="true" clearable></el-input>
                </el-form-item>
                <el-form-item label="新密码" prop="newpassword" class="required label-center-align">
                  <el-input v-model="formLogin.newpassword" type="password" :show-password="true" clearable></el-input>
                </el-form-item>
                <el-form-item label="重新输入" prop="newpassworda" class="required">
                  <el-input v-model="formLogin.newpassworda" type="password" :show-password="true" clearable></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button @click="formLogin=''">清空</el-button>
                  <el-button type="primary" @click="editPatientLogin">确定</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="hover">
            <template #header>
              <div class="clearfix">
                <span>账户编辑</span>
              </div>
            </template>
            <el-form :model="formData" ref="vForm" :rules="rules" label-position="left" label-width="80px" size="small"
                     @submit.native.prevent>
              <el-form-item label="头像" prop="avatar" class="label-center-align">
                <div class="info-image">
                  <img :src="avatarImg"/>
                </div>
                <el-upload :file-list="avatarFileList" :headers="avatarUploadHeaders" :data="avatarUploadData"
                           action="http://localhost:8000/clinic/patientInformationByPatient/upload"
                           list-type="picture-card"
                           show-file-list :limit="1">
                  <template #default><i class="el-icon-plus"></i></template>
                </el-upload>
              </el-form-item>
              <el-form-item label="名称" prop="name" class="required label-center-align">
                <el-input v-model="formData.name" type="text" clearable></el-input>
              </el-form-item>
              <el-form-item label="生日" prop="birthday" class="required label-center-align">
                <el-date-picker v-model="formData.birthday" type="date" class="full-width-input"
                                clearable></el-date-picker>
              </el-form-item>
              <el-form-item label="性别" prop="sex" class="required label-center-align">
                <el-select v-model="formData.sex" class="full-width-input" clearable>
                  <el-option v-for="(item, index) in sexOptions" :key="index" :label="item.value" :value="item.coding"
                             :disabled="item.disabled"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="身份证号" prop="identitynumber" class="required label-center-align">
                <el-input v-model="formData.identitynumber" type="text" clearable></el-input>
              </el-form-item>
              <el-form-item label="家庭住址" prop="address" class="required label-center-align">
                <el-input type="textarea" v-model="formData.address" rows="3"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="warning" @click="formData=patientInformation">清除</el-button>
                <el-button type="primary" @click="submitForm">上传</el-button>
              </el-form-item>
            </el-form>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </section>
</template>

<script>
import "cropperjs/dist/cropper.css";
import {
  editDoctorInformation,
  editDoctorLogin,
  editPatientInformation,
  editPatientLogin,
  findSexCoding
} from "../api/api";
import {ElMessage, ElMessageBox} from "element-plus";
import router from "../router";

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.formLogin.newpassword) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      formLogin: {
        password: "",
        newpassword: "",
        newpassworda: "",
      },
      rulesLogin: {
        password: [{
          required: true,
          message: '原密码不可为空',
        }],
        newpassword: [{
          required: true,
          message: '新密码不可为空',
        }, {validator: validatePass, trigger: "blur", required: true}],
        newpassworda: [{
          required: true,
          message: '新密码不可为空',
        },
          {validator: validatePass2, trigger: "blur", required: true},]
      },
      patientInformation: '',
      avatarImg: JSON.parse(localStorage.getItem("patient")).avatar,
      imgSrc: '',
      cropImg: '',
      dialogVisible: false,
      cropper: null,
      name: JSON.parse(localStorage.getItem("patient")).name,
      components: {},
      props: {},
      formData: {
        avatar: null,
        name: "",
        birthday: null,
        sex: "",
        identitynumber: "",
        registereid: "",
        address: "",
      },
      rules: {
        name: [{
          required: true,
          message: '字段值不可为空',
        }],
        birthday: [{
          required: true,
          message: '字段值不可为空',
        }],
        sex: [{
          required: true,
          message: '字段值不可为空',
        }],
        identitynumber: [
          {
            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/,
            message: '请填写正确的身份证号码',
            trigger: 'blur'
          }, {
            required: true,
            message: '字段值不可为空',
          }],
        registereid: [{
          required: true,
          message: '字段值不可为空',
        }],
        address: [{
          required: true,
          message: '字段值不可为空',
        }],
      },
      sexOptions: [],
      avatarFileList: [],
      avatarUploadHeaders: {},
      avatarUploadData: {},
    }
  },
  methods: {
    editPatientLogin() {
      ElMessageBox.confirm(
          '确定修改登录密码吗',
          '修改密码',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'success',
          }
      )
          .then(() => {
            let para = {
              id: this.patientInformation.id,
              password: this.formLogin.password,
              newPassword: this.formLogin.newpassword
            }
            editPatientLogin(para).then((res) => {
              if (res.data.msgId == 'C405') {
                ElMessage({
                  type: 'error',
                  message: '旧密码错误，请检查后重新输入',
                })
              }  else if (res.data.msgId == 'C200') {
                localStorage.removeItem("patient");
                router.push("/login");
                ElMessage({
                  type: 'success',
                  message: '修改成功',
                })
              }
            })
          })
    },
    submitForm() {
      this.$refs['vForm'].validate(valid => {
        ElMessageBox.confirm(
            '确定修改个人信息吗',
            '修改个人信息',
            {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'success',
            }
        )
            .then(() => {
             editPatientInformation(this.formData).then((res) => {
                if (res.data.msgId == 'C403') {
                  ElMessage({
                    type: 'error',
                    message: '存在相同名字，请在后面添加1、2 进行区分',
                  })
                } else if (res.data.msgId == 'C405') {
                  ElMessage({
                    type: 'error',
                    message: '更新失败，请稍后重试',
                  })
                } else if (res.data.msgId == 'C200') {
                  ElMessage({
                    type: 'success',
                    message: '更新成功',
                  })
                  localStorage.removeItem("patient");
                  router.push("/login");
                }
              })
            })
      })
    },
    resetForm() {
      this.$refs['vForm'].resetFields()
    },
    setImage(e) {
      const file = e.target.files[0];
      if (!file.type.includes("image/")) {
        return;
      }
      const reader = new FileReader();
      reader.onload = (event) => {
        this.dialogVisible = true;
        this.imgSrc = event.target.result;
        this.cropper && this.cropper.replace(event.target.result);
      };
      reader.readAsDataURL(file);
    },
    cropImage() {
      this.cropImg = this.cropper.getCroppedCanvas().toDataURL();
    },
    saveAvatar() {
      this.avatarImg = this.cropImg;
      this.dialogVisible = false;
    }
  }, mounted() {
    this.patientInformation = JSON.parse(localStorage.getItem("patient"));
    this.formData = this.patientInformation;
    findSexCoding().then((res) => {
      if (res.data.msgId == 'C200') {
        this.sexOptions = res.data.result
      }
    })
  }
};
</script>

<style scoped>
.info {
  text-align: center;
  padding: 35px 0;
}

.info-image {
  position: relative;
  margin: auto;
  width: 100px;
  height: 100px;
  background: #f8f8f8;
  border: 1px solid #eee;
  border-radius: 50px;
  overflow: hidden;
}

.info-image img {
  width: 100%;
  height: 100%;
}

.info-edit {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.info-edit i {
  color: #eee;
  font-size: 25px;
}

.info-image:hover .info-edit {
  opacity: 1;
}

.info-name {
  margin: 15px 0 10px;
  font-size: 24px;
  font-weight: 500;
  color: #262626;
}

.crop-demo-btn {
  position: relative;
}

.crop-input {
  position: absolute;
  width: 100px;
  height: 40px;
  left: 0;
  top: 0;
  opacity: 0;
  cursor: pointer;
}
</style>
