<template>
  <div class="login-wrap">
    <div class="ms-login">
      <div class="ms-title">后台管理系统</div>
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
        <div class="login-btn">
          <el-button type="primary" @click="submitForm()">登录</el-button>
        </div>
        <p class="login-tips">Tips :请输入医生账号进行登录。</p>
      </el-form>
    </div>
  </div>
</template>

<script>
import {ElMessage} from "element-plus";
import router from "../router";
import {findSexCoding, loginDoctor} from "../api/api";

export default {
  data() {
    return {
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
    submitForm() {
      this.$refs.param.validate((valid) => {
        if (valid) {
          loginDoctor(this.param).then((res)=>{
            if(res.data.msgId==='C200'){
              ElMessage.success("登录成功");
              localStorage.setItem("doctor", JSON.stringify(res.data.result));
              localStorage.setItem("loginTime",new Date().getFullYear() + "-" + (new Date().getMonth() + 1) +'-'+new Date().getDate() +'  '+new Date().getHours()+':'+new Date().getMinutes()+':'+new Date().getSeconds())
              window.location.href = 'http://localhost:3000/#/dashboard';
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
  }
};
</script>

<style scoped>
.login-wrap {
  position: relative;
  width: 100%;
  height: 100%;
  background-image: url(../assets/img/login-bg.jpg);
  background-size: 100%;
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
  color: #fff;
}
</style>
