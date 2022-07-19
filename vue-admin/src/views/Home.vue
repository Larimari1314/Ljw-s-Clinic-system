<template>
  <el-row class="container">
    <el-col :span="24" class="header">
      <el-col :class="collapsed?'logo-collapse-width':'logo-width'" :span="10" class="logo">
        <a href="http://localhost:9678/#/PatientInformationManagement" style="text-decoration:none; color:white;">
       <span class="userinfo-inner">
         {{ collapsed ? '' : sysName }}
       </span>
        </a>
      </el-col>
      <el-col :span="10">
        <div class="tools" @click.prevent="collapse">
          <i class="fa fa-align-justify"></i>
        </div>
      </el-col>
      <el-col :span="4" class="userinfo">
        <el-dropdown trigger="hover">
          <span class="el-dropdown-link userinfo-inner">
            <a href="http://localhost:9678/#/PatientInformationManagement">
            <img :src="this.sysUserAvatar"/> </a>{{ sysUserName }}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item @click.native="adminInformation">设置个人信息</el-dropdown-item>
            <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-col>

    <el-col :span="24" class="main">
      <aside :class="collapsed?'menu-collapsed':'menu-expanded'">
        <!--导航菜单-->
        <el-menu v-show="!collapsed" :default-active="$route.path" class="el-menu-vertical-demo" router
                 unique-opened
                 @close="handleclose" @open="handleopen" @select="handleselect">
          <template v-for="(item,index) in $router.options.routes" v-if="!item.hidden">
            <el-submenu v-if="!item.leaf" :index="index+''">
              <template slot="title"><i :class="item.iconCls"></i>{{ item.name }}</template>
              <el-menu-item v-for="child in item.children" v-if="!child.hidden" :key="child.path" :index="child.path">
                {{ child.name }}
              </el-menu-item>
            </el-submenu>
            <el-menu-item v-if="item.leaf&&item.children.length>0" :index="item.children[0].path"><i
                :class="item.iconCls"></i>{{ item.children[0].name }}
            </el-menu-item>
            <el-dialog title="修改个人信息" v-model="editFormVisible" :close-on-click-modal="false">
                <el-tabs v-model="activeName" @tab-click="handleClick">
                  <el-tab-pane label="个人信息修改" name="first">
                    <el-form :model="editFormAdmin" label-width="80px"  ref="editFormAdmin">
                      <el-form-item label="个人头像">
                        <img :src="editFormAdmin.avatarUrl" style="border-radius:50%; " width="100" height="100" alt="管理员头像"/>
                    <el-upload
                        action="http://localhost:8080/adminInformation/uploadToServer"
                        list-type="picture-card"
                        :on-preview="handlePictureCardPreview"
                        :on-remove="handleRemove">
                      <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog v-model="dialogVisible" size="tiny">
                      <img width="100%" :src="dialogImageUrl" alt="">
                    </el-dialog>
                      </el-form-item>
                      <el-form-item label="用户名" prop="adminName">
                        <el-input v-model="editFormAdmin.adminName" auto-complete="off"></el-input>
                      </el-form-item>
                      <el-form-item label="性别" prop="adminSex">
                        <el-select v-model="editFormAdmin.adminSex" :disabled='true' clearable :style="{width: '100%'}">
                          <el-option v-for="(item, index) in adminSexOptions" :key="index" :label="item.label"
                                     :value="item.value" :disabled="item.disabled"></el-option>
                        </el-select>
                      </el-form-item>
                      <el-form-item label="年龄" prop="adminAge">
                        <el-input type="number" v-model="editFormAdmin.adminAge" auto-complete="off"></el-input>
                      </el-form-item>
                      <el-form-item label="电话号码" prop="administratorPhone" :rules="[
                      { pattern:  /^1[3456789]\d{9}$/,
                        message: '请填写正确的电话号码',
                        trigger: 'blur'}
                      ]">
                        <el-input v-model="editFormAdmin.administratorPhone" auto-complete="off"></el-input>
                      </el-form-item>
                      <el-form-item label="家庭住址" prop="adminAddr">
                        <el-input type="textarea" v-model="editFormAdmin.adminAddr"></el-input>
                      </el-form-item>
                    </el-form>
                    <el-button @click.native="editFormVisible = false">取消</el-button>
                    <el-button type="primary" :loading="editLoading"  @click="editSubmit">提交</el-button>
                  </el-tab-pane>
                  <el-tab-pane label="登录信息修改" name="second">
                    <el-col :span="15">
                    <el-form :model="editAdminLogin" label-width="80px"  ref="editAdminLogin" >
                    <el-form-item label="登录名" prop="adminUserName" :rules="[
                      { required: true, message: '登录名不能为空'}]
                      ">
                        <el-input v-model="editAdminLogin.adminUserName" auto-complete="off" ></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password" :rules="[
                      { required: true, message: '密码不能为空'} ,{ pattern:  /^[a-z0-9_-]{6,20}$/,
                        message: '密码长度需在6-20以内',
                        trigger: 'blur'}
                      ]">
                      <el-input v-model="editAdminLogin.password" auto-complete="off"></el-input>
                    </el-form-item>
                    </el-form>
                    </el-col>
                    <el-col>
                    <el-button @click.native="editFormVisible = false">取消</el-button>
                    <el-button type="primary" :loading="editLoading" @click="editSubmitLogin">提交</el-button>
                    </el-col>
                  </el-tab-pane>
                </el-tabs>
            </el-dialog>
          </template>

        </el-menu>
        <!--导航菜单-折叠后-->
        <ul v-show="collapsed" ref="menuCollapsed" class="el-menu el-menu-vertical-demo collapsed">
          <li v-for="(item,index) in $router.options.routes" v-if="!item.hidden" class="el-submenu item">
            <template v-if="!item.leaf">
              <div class="el-submenu__title" style="padding-left: 20px;" @mouseout="showMenu(index,false)"
                   @mouseover="showMenu(index,true)"><i :class="item.iconCls"></i></div>
              <ul :class="'submenu-hook-'+index" class="el-menu submenu" @mouseout="showMenu(index,false)"
                  @mouseover="showMenu(index,true)">
                <li v-for="child in item.children" v-if="!child.hidden" :key="child.path"
                    :class="$route.path==child.path?'is-active':''"
                    class="el-menu-item" style="padding-left: 40px;"
                    @click="$router.push(child.path)">{{ child.name }}
                </li>
              </ul>
            </template>
            <template v-else>
          <li class="el-submenu">
            <div :class="$route.path==item.children[0].path?'is-active':''"
                 class="el-submenu__title el-menu-item"
                 style="padding-left: 20px;height: 56px;line-height: 56px;padding: 0 20px;"
                 @click="$router.push(item.children[0].path)"><i :class="item.iconCls"></i></div>
          </li>
</template>
</li>
</ul>
</aside>
<section class="content-container">
<div class="grid-content bg-purple-light">
  <el-col :span="24" class="breadcrumb-container">
    <strong class="title">{{ $route.name }}</strong>
    <el-breadcrumb class="breadcrumb-inner" separator="/">
      <el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
        {{ item.name }}
      </el-breadcrumb-item>
    </el-breadcrumb>
  </el-col>
  <el-col :span="24" class="content-wrapper">
    <transition mode="out-in" name="fade">
      <router-view></router-view>
    </transition>
  </el-col>
</div>
</section>
</el-col>
</el-row>
</template>

<script>
import {editAdminLogin, exitAdminInformation, loginOut} from "../api/api";

export default {
  data() {
    return {
      editAdminLogin:[],
      dialogImageUrl: '',
      dialogVisible: false,
      adminSexOptions: [{
        "label": "男",
        "value": 1
      }, {
        "label": "女",
        "value": 2
      }],
      editFormAdmin:[],
      activeName: 'first',
      editLoading: false,
      editFormVisible:false,
      sysName: '仁城诊所管理系统',
      collapsed: false,
      sysUserName: '',
      sysUserAvatar: '',
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      }
    }
  },
  methods: {
    editSubmitLogin:function () {
      this.$refs.editAdminLogin[0].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            let para = {id:this.editFormAdmin.id,adminUserName:this.editAdminLogin.adminUserName,password:this.editAdminLogin.password}
            editAdminLogin(para).then((res) => {
              if(res.data.code=='200'){
                //NProgress.done();
                this.$message({
                  message: res.data.message,
                  type: 'success'
                });
                this.editLoading = false;
                this.editFormVisible = false;
                loginOut()
                let et = new Date()
                sessionStorage.removeItem('user');
                this.$router.push('/login');
              }else{
                this.$message({
                  message: res.data.message,
                  type: 'error'})
              }
            });
          });
        }
      });
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    editSubmit: function () {
      this.$refs.editFormAdmin[0].validate((valid) => {
        if (valid) {
          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            this.editLoading = true;
            //NProgress.start();
            let para = Object.assign({}, this.editFormAdmin);
            exitAdminInformation(para).then((res) => {
              if(res.data.code=='200'){
                //NProgress.done();
                this.$message({
                  message: res.data.message,
                  type: 'success'
                });
                this.editLoading = false;
                this.editFormVisible = false;
                loginOut()
                let et = new Date()
                sessionStorage.removeItem('user');
                this.$router.push('/login');
              }else{
                this.$message({
                  message: res.data.message,
                  type: 'error'
                });
              }
            });
          });
        }
      });
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    adminInformation:function(){
      this.editFormVisible=true
    },
    onSubmit() {
      console.log('submit!');
    },
    handleopen() {
      //console.log('handleopen');
    },
    handleclose() {
      //console.log('handleclose');
    },
    handleselect: function (a, b) {
    },
    //退出登录
    logout: function () {
      var _this = this;
      this.$confirm('确认退出吗?', '提示', {
        //type: 'warning'
      }).then(() => {
        loginOut()
        let et = new Date()
        sessionStorage.removeItem('user');
        _this.$router.push('/login');
      }).catch(() => {

      });
    },
    //折叠导航栏
    collapse: function () {
      this.collapsed = !this.collapsed;
    },
    showMenu(i, status) {
      this.$refs.menuCollapsed.getElementsByClassName('submenu-hook-' + i)[0].style.display = status ? 'block' : 'none';
    }
  },
  mounted() {
    var user = sessionStorage.getItem('user');
    if (user) {
      user = JSON.parse(user);
      this.sysUserName = user.adminName;
      this.sysUserAvatar = user.avatarUrl;
      this.editFormAdmin=user
    }
  }
}

</script>

<style lang="scss" scoped>
@import '~scss_vars';

.container {
  position: absolute;
  top: 0px;
  bottom: 0px;
  width: 100%;

  .header {
    height: 60px;
    line-height: 60px;
    background: $color-primary;
    color: #fff;

    .userinfo {
      text-align: right;
      padding-right: 35px;
      float: right;

      .userinfo-inner {
        cursor: pointer;
        color: #fff;

        img {
          width: 40px;
          height: 40px;
          border-radius: 20px;
          margin: 10px 0px 10px 10px;
          float: right;
        }
      }
    }

    .userinfo-inner {
      img {
        width: 100px;
        height: 50px;
        border-radius: 0px;
        margin: 0px 0px 0px 0px;
        float: left;
      }
    }

    .logo {
      //width:230px;
      height: 60px;
      font-size: 22px;
      padding-left: 20px;
      padding-right: 20px;
      border-color: rgba(238, 241, 146, 0.3);
      border-right-width: 1px;
      border-right-style: solid;

      img {
        width: 40px;
        float: left;
        margin: 10px 10px 10px 18px;
      }

      .txt {
        color: #fff;
      }
    }

    .logo-width {
      width: 230px;
    }

    .logo-collapse-width {
      width: 60px
    }

    .tools {
      padding: 0px 23px;
      width: 14px;
      height: 60px;
      line-height: 60px;
      cursor: pointer;
    }
  }

  .main {
    display: flex;
    // background: #324057;
    position: absolute;
    top: 60px;
    bottom: 0px;
    overflow: hidden;

    aside {
      flex: 0 0 230px;
      width: 230px;
      // position: absolute;
      // top: 0px;
      // bottom: 0px;
      .el-menu {
        height: 100%;
      }

      .collapsed {
        width: 60px;

        .item {
          position: relative;
        }

        .submenu {
          position: absolute;
          top: 0px;
          left: 60px;
          z-index: 99999;
          height: auto;
          display: none;
        }

      }
    }

    .menu-collapsed {
      flex: 0 0 60px;
      width: 60px;
    }

    .menu-expanded {
      flex: 0 0 230px;
      width: 230px;
    }

    .content-container {
      // background: #f1f2f7;
      flex: 1;
      // position: absolute;
      // right: 0px;
      // top: 0px;
      // bottom: 0px;
      // left: 230px;
      overflow-y: scroll;
      padding: 20px;

      .breadcrumb-container {
        //margin-bottom: 15px;
        .title {
          width: 200px;
          float: left;
          color: #475669;
        }

        .breadcrumb-inner {
          float: right;
        }
      }

      .content-wrapper {
        background-color: #fff;
        box-sizing: border-box;
      }
    }
  }
}
</style>