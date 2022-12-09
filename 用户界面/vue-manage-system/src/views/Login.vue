<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">患者诊所登录系统端</div>
      <el-form :model="param" :rules="rules" ref="param" label-width="0px" class="ms-content">
        <el-form-item prop="loginCredentials">
          <el-input v-model="param.loginCredentials" placeholder="账号">
            <template #prepend>
              <el-button icon="el-icon-user"></el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="密码" v-model="param.password"
                    @keyup.enter="submitForm()">
            <template #prepend>
              <el-button icon="el-icon-lock"></el-button>
            </template>
          </el-input>
        </el-form-item>
          <el-button type="info" @click="registerAccount()">注册账号</el-button>
          <el-button type="primary" @click="submitForm()" style="text-align: right">登录</el-button>
        <p class="login-tips" >注意 :请输入患者账号进行登录。</p>
      </el-form>
    </div>
    <el-drawer v-model="drawer" title="注册账号" :with-header="true" size="40%" >
      <el-form :model="formData" ref="vForm" :rules="rulesAdd" label-position="left" label-width="80px"
               size="default" @submit.native.prevent>
        <el-form-item label="登录名" prop="loginname" class="required">
          <el-input v-model="formData.loginname" type="text" clearable></el-input>
        </el-form-item>
        <el-form-item label="登录邮件" prop="loginemail" class="required">
          <el-input v-model="formData.loginemail" type="text" clearable></el-input>
        </el-form-item>
        <el-form-item label="登录电话" prop="loginphone" class="required">
          <el-input v-model="formData.loginphone" type="text" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" class="required">
          <el-input v-model="formData.password" type="password" clearable></el-input>
        </el-form-item>
        <div class="static-content-item">
          <el-divider direction="horizontal">个人信息</el-divider>
        </div>
        <el-form-item label="用户名" prop="name" class="required">
          <el-input v-model="formData.name" type="text" clearable></el-input>
        </el-form-item>
        <el-form-item label="生日" prop="birthday" class="required">
          <el-date-picker v-model="formData.birthday" type="date" class="full-width-input" format="YYYY-MM-DD"
                          value-format="YYYY-MM-DD" clearable></el-date-picker>
        </el-form-item>
        <el-form-item label="性别" prop="sex" class="required">
            <el-radio-group v-model="formData.sex" class="ml-4">
              <el-radio label="GS001" size="large">男</el-radio>
              <el-radio label="GS002" size="large">女</el-radio>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="身份证号" prop="identitynumber" class="required">
          <el-input v-model="formData.identitynumber" type="text" clearable></el-input>
        </el-form-item>
        <el-form-item label="家庭住址" prop="address" class="required">
          <el-input type="textarea" v-model="formData.address" rows="1"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="drawer=false;formData=''">取消</el-button>
          <el-button type="primary" @click="submitAddForm()">提交</el-button>
        </el-form-item>
      </el-form>
    </el-drawer>

  <div class="square">
    <ul>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
  </div>
  <div class="circle">
    <ul>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
      <li></li>
    </ul>
  </div>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";
import router from "../router";
import {findSexCoding, loginPatient, registerAccountByPatient} from "../api/api";

export default {
  data() {
    return {
      formData: {
        loginname: "",
        loginemail: "",
        loginphone: "",
        password: "",
        name: "",
        birthday: null,
        sex: "",
        identitynumber: "",
        address: "",
      },
      rulesAdd: {
        loginname: [{
          required: true,
          message: '登录名不可为空',
        }],
        loginemail: [{
          required: true,
          message: '登录邮件不可为空',
        }, {
          pattern: /^([-_A-Za-z0-9.]+)@([_A-Za-z0-9]+\.)+[A-Za-z0-9]{2,3}$/,
          trigger: ['blur', 'change'],
          message: '邮箱格式错误'
        }],
        loginphone: [{
          required: true,
          message: '登录电话不可为空',
        }, {
          pattern: /^[1][3-9][0-9]{9}$/,
          trigger: ['blur', 'change'],
          message: '手机号码格式错误'
        }],
        password: [{
          required: true,
          message: '密码不可为空',
        }],
        name: [{
          required: true,
          message: '用户名不可为空',
        }],
        birthday: [{
          required: true,
          message: '生日不可为空',
        }],
        sex: [
            {
          required: true,
          message: '性别不可为空',
        }],
        identitynumber: [
            {
          pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/,
          message: '请填写正确的身份证号码',
          trigger: 'blur'
        },{
          required: true,
          message: '身份证号不可为空',
        }],
        address: [{
          required: true,
          message: '地址不可为空',
        }],
      },
      sexOptions: [],
      drawer:false,
      ruleForm2: {
        account: '',
        checkPass: ''
      },
      param: {
        loginCredentials: "",
        password: "",
      },
      rules: {
        loginCredentials: [
          {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
          },
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
        ],
      }
    }
  },
  methods: {
    submitAddForm() {
      this.$refs['vForm'].validate(valid => {
        if (valid) {
          // alert(JSON.stringify(this.formData))
          registerAccountByPatient(this.formData,null).then((res)=>{
           if(res.data.msgId=='C200'){
             this.drawer=false
             ElMessage.success("注册成功");
             this.formData={
               loginname: "",
               loginemail: "",
               loginphone: "",
               password: "",
               name: "",
               birthday: null,
               sex: "",
               identitynumber: "",
               address: "",
             };
           }else if(res.data.msgId=='C403-1'){
             ElMessage.error("登录名重复");
             this.formData.loginname='';
           }else if(res.data.msgId=='C403-2'){
             ElMessage.error("登录邮箱重复");
             this.formData.loginemail='';
           }else if(res.data.msgId=='C403-3'){
             ElMessage.error("登录电话重复");
             this.formData.loginphone='';
           }else if(res.data.msgId=='C405-1'){
             ElMessage.error("登录信息添加失败，请稍后重试");
           }else if(res.data.msgId=='C405-2'){
             ElMessage.error("用户基本信息添加失败，请稍后重试");
           }else if(res.data.msgId=='C403-4'){
             ElMessage.error("用户名重复，如重名请在名字后面填写1、2 以区分");
             this.formData.name='';
           }
          })
        }
      })
    },
    resetForm() {
      this.$refs['vForm'].resetFields()
    },
    registerAccount(){
      this.drawer=true
    },
    submitForm() {
      this.$refs.param.validate((valid) => {
        if (valid) {
          loginPatient(this.param).then((res)=>{
            if(res.data.msgId==='C200'){
              ElMessage.success("登录成功");
              let token = res.headers['token'];
              sessionStorage.setItem('permissionToken',token);
              localStorage.setItem("patient", JSON.stringify(res.data.result));
              localStorage.setItem("loginTime",new Date().getFullYear() + "-" + (new Date().getMonth() + 1) +'-'+new Date().getDate() +'  '+new Date().getHours()+':'+new Date().getMinutes()+':'+new Date().getSeconds())
              window.location.href = window.location.host+'/#/appointmentDoctor';
            }else if(res.data.msgId==='C404'){
              ElMessage.error("账号不存在或密码错误请检查后输入");
            }
          })
        } else {
          ElMessage.error("登录失败");
          return false;
        }
      });
    }
  },mounted() {
    findSexCoding().then((res)=>{
      this.sexOptions=res.data.result
    })
  }
};
</script>

<style scoped>
.login-wrap {
  /* 100%窗口高度 */
  height: 100vh;
  /* 弹性布局 居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  /* 渐变背景 */
  background: linear-gradient(200deg,#e3c5eb,#a9c1ed);
  /* 溢出隐藏 */
  overflow: hidden;
}
.ms-title {
  width: 100%;
  line-height: 50px;
  text-align: center;
  font-size: 20px;
  color: #fff;
  border-bottom: 1px solid #ddd;
}

.ms-login {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 350px;
  margin: -190px 0 0 -175px;
  border-radius: 5px;
  background: rgba(255, 255, 255, 0.3);
  overflow: hidden;
}

.ms-content {
  padding: 30px 30px;
}

.login-btn {
  text-align: center;
}

.login-btn button {
  width: 100%;
  height: 36px;
  margin-bottom: 10px;
}

.login-tips {
  font-size: 12px;
  line-height: 30px;
  color: #0800e6;
}
/*1.显示滚动条：当内容超出容器的时候，可以拖动：*/
.el-drawer__body {
  overflow: auto;
}
/*2.隐藏滚动条，太丑了*/
.el-drawer__container ::-webkit-scrollbar{
  display: none;
}
.container{
  /* 相对定位 */
  position: relative;
  z-index: 1;
  background-color: #fff;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 350px;
  height: 500px;
  /* 阴影 */
  box-shadow: 0 5px 20px rgba(0,0,0,0.1);
}
.container .tit{
  font-size: 26px;
  margin: 65px auto 70px auto;
}
.container input{
  width: 280px;
  height: 30px;
  text-indent: 8px;
  border: none;
  border-bottom: 1px solid #ddd;
  margin: 12px auto;
  outline: none;
}
.container button{
  width: 280px;
  height: 40px;
  margin: 35px auto 40px auto;
  border: none;
  background: linear-gradient(-200deg,#fac0e7,#aac2ee);
  color: #fff;
  letter-spacing: 8px;
  border-radius: 10px;
  font-weight: bold;
  cursor: pointer;
  /* 动画过渡 */
  transition: 0.5s;
}
.container button:hover{
  background: linear-gradient(-200deg,#aac2ee,#fac0e7);
  background-position-x: -280px;
}
.container span{
  font-size: 14px;
}
.container a{
  color: plum;
  text-decoration: none;
}
ul li{
  /* 绝对定位 */
  position: absolute;
  border: 1px solid #fff;
  background-color: #fff;
  width: 30px;
  height: 30px;
  list-style: none;
  opacity: 0;
}
.square li{
  top: 40vh;
  left: 60vw;
  /* 执行动画：动画名 时长 线性的 无限次播放 */
  animation: square 10s linear infinite;
}
.square li:nth-child(2){
  top: 80vh;
  left: 10vw;
  /* 设置动画延迟时间 */
  animation-delay: 2s;
}
.square li:nth-child(3){
  top: 80vh;
  left: 85vw;
  animation-delay: 4s;
}
.square li:nth-child(4){
  top: 10vh;
  left: 70vw;
  animation-delay: 6s;
}
.square li:nth-child(5){
  top: 10vh;
  left: 10vw;
  animation-delay: 8s;
}
.circle li{
  bottom: 0;
  left: 15vw;
  /* 执行动画：动画名 时长 线性的 无限次播放 */
  animation: circle 10s linear infinite;
}
.circle li:nth-child(2){
  left: 35vw;
  /* 设置动画延迟时间 */
  animation-delay: 2s;
}
.circle li:nth-child(3){
  left: 55vw;
  animation-delay: 6s;
}
.circle li:nth-child(4){
  left: 75vw;
  animation-delay: 4s;
}
.circle li:nth-child(5){
  left: 90vw;
  animation-delay: 8s;
}

/* 定义动画 */
@keyframes square {
  0%{
    transform: scale(0) rotateY(0deg);
    opacity: 1;
  }
  100%{
    transform: scale(5) rotateY(1000deg);
    opacity: 0;
  }
}
@keyframes circle {
  0%{
    transform: scale(0) rotate(0deg);
    opacity: 1;
    bottom: 0;
    border-radius: 0;
  }
  100%{
    transform: scale(5) rotate(1000deg);
    opacity: 0;
    bottom: 90vh;
    border-radius: 50%;
  }
}
</style>
