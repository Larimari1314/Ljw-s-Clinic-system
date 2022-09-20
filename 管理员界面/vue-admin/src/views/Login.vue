<template>
  <section class="bg">
    <el-row :gutter="20">
      <el-col :span="1">
        <div class="grid-contents bg-purple"></div>
      </el-col>
      <el-col :span="13">
        <div class="grid-contents bg-purple">
          <el-carousel indicator-position="outside" :height=" bannerHeight + 'px' ">
            <el-carousel-item v-for="items in item" :key="items.id">
              <a :href="items.ads">
                <img :src="items.imgurl" class="carousel_image_type">
              </a>
            </el-carousel-item>
          </el-carousel>
        </div>
      </el-col>
      <el-col :span="10">
        <!--        <div  style="text-align: right">
                  <el-button type="text">发药工作人员登录</el-button></div>-->
        <el-form :model="ruleForm2" :rules="rules2" ref="ruleForm2" label-position="left" label-width="0px"
                 class="demo-ruleForm login-container">
          <el-form-item>
            <template scope="scope">
              <div style="text-align: center">
                <h1>欢迎登录医院后台系统</h1>
              </div>
              <a href="http://localhost:8080/#/login">
                <p style="text-align: center">
                  <img :src="adminImgUrl" style="border-radius:50%; " width="100"
                       height="100"
                       alt="头像"></p></a>
            </template>
          </el-form-item>
          <el-form-item prop="account">
            <el-input type="text" v-model="ruleForm2.account" auto-complete="off" placeholder="用户名"
                      @blur="getIngUrl"></el-input>
          </el-form-item>
          <el-form-item prop="checkPass">
            <el-input type="password" v-model="ruleForm2.checkPass" auto-complete="off" placeholder="密码"
                      :disabled="disable"></el-input>
          </el-form-item>
          <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox>
          <el-form-item style="width:100%;">
            <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit2" :loading="logining">
              登录
            </el-button>
            <!--<el-button @click.native.prevent="handleReset2">重置</el-button>-->
          </el-form-item>
        </el-form>
      </el-col>
      <el-col :span="10"></el-col>
    </el-row>
  </section>
</template>

<script>
import {adminRequestLogin, getAdminImgUrl, GetAllAds} from '../api/api';
//import NProgress from 'nprogress'
export default {
  data() {
    return {
      adminLoginVisible:true,
      disable: true,
      adminImgUrl: ' http://localhost:8000/clinic/Img/default/default.jpg',
      bannerHeight: 400,//图片父容器的高度
      screenWidth: 0,//屏幕的宽度
      logining: false,
      item: [{
        //默认广告展示
        id: '1',
        ads: 'https://www.bilibili.com/video/BV1pb4y1Z75X?spm_id_from=333.999.0.0',
        imgurl: 'http://localhost:8000/clinic//Img//login//1.jpg'
      }],
      ruleForm2: {
        account: '',
        checkPass: ''
      },
      rules2: {
        account: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          //{ validator: validaePass }
        ],
        checkPass: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          //{ validator: validaePass2 }
        ]
      },
      checked: true
    };
  },
  methods: {
    getIngUrl() {
      if (this.ruleForm2.account != null || this.ruleForm2.account == '') {
        getAdminImgUrl(this.ruleForm2.account).then((res) => {
          if (res.data.msgId == 'C200') {
            this.adminImgUrl = res.data.result
            this.disable = false
            this.$notify({
              title: '成功',
              message: "欢迎登录",
              type: 'success'
            });
          } else if (res.data.msgId == 'C404') {
            this.adminImgUrl = 'http://localhost:8000/clinic/Img/default/default.jpg'
            this.disable = true
            this.$notify({
              title: '警告',
              message: '用户名不存在，请检查后重试',
              type: 'warning'
            });
          } else if (res.data.msgId == 'C201') {
            this.disable = false
            this.$notify({
              title: '成功',
              message: "当前用户名存在多个，头像无法正常显示",
              type: 'success'
            });
          }
        })
      } else {
        this.adminImgUrl = 'http://localhost:8000/clinic/Img/default/default.jpg'
      }
    },
    handleSubmit2(ev) {
      let _this = this;
      this.$refs.ruleForm2.validate((valid) => {
        if (valid) {
          _this.logining = true;
          let loginParams = {loginCredentials: this.ruleForm2.account, password: this.ruleForm2.checkPass};
          adminRequestLogin(loginParams).then((res) => {
            this.logining = false;
            sessionStorage.removeItem('permissionToken');
            if (res.data.msgId == 'C200') {
              //获取请求头中的信息permissionToken，将其保存到页面域中的permissionToken
              sessionStorage.setItem("permissionToken",res.headers['token'])
              // alert(res.headers['token'])
              sessionStorage.setItem('user', JSON.stringify(res.data.result));
              this.$router.push({path: '/HomeMain'});
            } else if (res.data.msgId == 'C404') {
              _this.$notify({
                title: '错误',
                message: '密码错误',
                type: 'error'
              });
            }
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    }
  }, created() {
    GetAllAds().then((res) => {
      this.item = res.data
    });
  }
}
</script>

<style lang="scss" scoped>
.bg {
  background: url("../assets/backage.jpg") no-repeat fixed center;
}

.login-container {
  /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
  -webkit-border-radius: 5px;
  border-radius: 5px;
  -moz-border-radius: 5px;
  background-clip: padding-box;
  margin: 80px auto;
  width: 400px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;

  .title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }

  .remember {
    margin: 0px 0px 35px 0px;
  }
}
</style>
<style>
.el-carousel__item h3 {
  transform: translateZ(0) !important;
  border: 1px solid #e5e5e5;
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: auto 100px;
}

el-row {
  margin-bottom: 20px;
}

el-col {
  border-radius: 4px;
}

.grid-contents {
  margin: 150px 20px;
  border-radius: 4px;
  min-height: 200px;
}
</style>
