<template>
	<el-row class="container">
		<el-col :span="24" class="header">
			<el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
				{{collapsed?'':sysName}}
			</el-col>
			<el-col :span="10">
				<div class="tools" @click.prevent="collapse">
					<i class="fa fa-align-justify"></i>
				</div>
			</el-col>
			<el-col :span="4" class="userinfo">
				<el-dropdown trigger="hover">
					<span class="el-dropdown-link userinfo-inner"><img :src="this.sysUserAvatar" /> {{sysUserName}}</span>
					<el-dropdown-menu slot="dropdown">
						<el-dropdown-item @click.native="adminInformation">设置</el-dropdown-item>
						<el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>
			</el-col>
		</el-col>
		<el-col :span="24" class="main">
			<aside :class="collapsed?'menu-collapsed':'menu-expanded'">
				<!--导航菜单-->
				<el-menu :default-active="$route.path" class="el-menu-vertical-demo" @open="handleopen" @close="handleclose" @select="handleselect"
					 unique-opened router v-show="!collapsed">
					<template v-for="(item,index) in $router.options.routes" v-if="!item.hidden">
						<el-submenu :index="index+''" v-if="!item.leaf">
							<template slot="title"><i :class="item.iconCls"></i>{{item.name}}</template>
							<el-menu-item v-for="child in item.children" :index="child.path" :key="child.path" v-if="!child.hidden">{{child.name}}</el-menu-item>
						</el-submenu>
						<el-menu-item v-if="item.leaf&&item.children.length>0" :index="item.children[0].path"><i :class="item.iconCls"></i>{{item.children[0].name}}</el-menu-item>
            <el-dialog title="修改个人信息" v-model="editFormVisible" :close-on-click-modal="false">
              <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane label="个人信息修改" name="first">
                  <el-form :model="editFormAdmin" label-width="80px" ref="editFormAdmin" :rules="editFormRules">
                    <el-form-item label="个人头像">
                      <img :src="editFormAdmin.avatar" style="border-radius:50%; " width="100" height="100" alt="管理员头像"/>
                      <el-upload
                          action="http://localhost:8000/clinic/loginAdmin/upload"
                          list-type="picture-card"
                          :on-preview="handlePictureCardPreview"
                          :on-remove="handleRemove">
                        <i class="el-icon-plus"></i>
                      </el-upload>
                      <el-dialog v-model="dialogVisible" size="tiny">
                        <img width="100%" :src="dialogImageUrl" alt="">
                      </el-dialog>
                    </el-form-item>
                    <el-form-item label="用户名" prop="name">
                      <el-input v-model="editFormAdmin.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="性别" prop="sex">
                      <el-select v-model="editFormAdmin.sex" clearable :style="{width: '100%'}">
                        <el-option
                            v-for="item in sexList"
                            :key="item.coding"
                            :label="item.value"
                            :value="item.coding">
                        </el-option>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="年龄" prop="age">
                      <el-input type="number" v-model="editFormAdmin.age" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="电话号码" prop="identitynumber">
                      <el-input v-model="editFormAdmin.identitynumber" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="家庭住址" prop="address">
                      <el-input type="textarea" v-model="editFormAdmin.address"></el-input>
                    </el-form-item>
                  </el-form>
                  <el-button @click.native="editFormVisible = false">取消</el-button>
                  <el-button type="primary" :loading="editLoading"  @click="editSubmit">提交</el-button>
                </el-tab-pane>
                <el-tab-pane label="登录信息修改" name="second">
                  <el-form :model="editFormAdminLogin" label-width="100px" ref="editFormAdmin" :rules="editFormLoginRules">
                    <el-form-item label="登录名" prop="loginName">
                      <el-input  v-model="editFormAdminLogin.loginName" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="登录电话号" prop="loginPhone">
                      <el-input type="number" v-model="editFormAdminLogin.loginPhone" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="登录邮箱" prop="loginEmail">
                      <el-input v-model="editFormAdminLogin.loginEmail" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password">
                      <el-input v-model="editFormAdminLogin.password" auto-complete="off"></el-input>
                    </el-form-item>
                  </el-form>
                  <el-button @click.native="editFormVisible = false">取消</el-button>
                  <el-button type="primary" :loading="editLoading"  @click="editLoginSubmit">提交</el-button>
                </el-tab-pane>
              </el-tabs>
            </el-dialog>
					</template>
				</el-menu>
				<!--导航菜单-折叠后-->
				<ul class="el-menu el-menu-vertical-demo collapsed" v-show="collapsed" ref="menuCollapsed">
					<li v-for="(item,index) in $router.options.routes" v-if="!item.hidden" class="el-submenu item">
						<template v-if="!item.leaf">
							<div class="el-submenu__title" style="padding-left: 20px;" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)"><i :class="item.iconCls"></i></div>
							<ul class="el-menu submenu" :class="'submenu-hook-'+index" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)">
								<li v-for="child in item.children" v-if="!child.hidden" :key="child.path" class="el-menu-item" style="padding-left: 40px;" :class="$route.path==child.path?'is-active':''" @click="$router.push(child.path)">{{child.name}}</li>
							</ul>
						</template>
						<template v-else>
							<li class="el-submenu">
								<div class="el-submenu__title el-menu-item" style="padding-left: 20px;height: 56px;line-height: 56px;padding: 0 20px;" :class="$route.path==item.children[0].path?'is-active':''" @click="$router.push(item.children[0].path)"><i :class="item.iconCls"></i></div>
							</li>
						</template>
					</li>
				</ul>
			</aside>
			<section class="content-container">
				<div class="grid-content bg-purple-light">
					<el-col :span="24" class="breadcrumb-container">
						<strong class="title">{{$route.name}}</strong>
						<el-breadcrumb separator="/" class="breadcrumb-inner">
							<el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
								{{ item.name }}
							</el-breadcrumb-item>
						</el-breadcrumb>
					</el-col>
					<el-col :span="24" class="content-wrapper">
						<transition name="fade" mode="out-in">
							<router-view></router-view>
						</transition>
					</el-col>
				</div>
			</section>
		</el-col>
	</el-row>
</template>

<script>
import {editAdminInformation, editAdminLogin, findSexCoding, logoutAdmin} from "../api/api";

  export default {
		data() {
			return {
        editFormAdminLogin:{
          id:'',
          loginName:'',
          loginPhone:'',
          loginEmail:'',
          password:''
        },
        dialogImageUrl: '',
        dialogVisible: false,
        editFormLoginRules:{
          LoginName:[
            {required: true, message: '请输入登录名', trigger: 'blur'}
          ],
          LoginPhone:[
            {required: true, message: '请输入电话号', trigger: 'blur'},
            {
              pattern:/^1[3456789]\d{9}$/,
              message: '请填写正确的电话号',
              trigger: 'blur'
            },
          ],
          LoginEmail:[
            {required: true, message: '请输入邮件', trigger: 'blur'},
            {
              pattern: /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,
              message: '请填写正确的电子邮件',
              trigger: 'blur'
            },
          ],
          password:[
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        },
        editFormRules: {
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'}
          ],
          address: [
            {required: true, message: '请输入地址', trigger: 'blur'}
          ], age: [
            {required: true, message: '请输入年龄', trigger: 'blur'}
          ],
          identitynumber: [
            {
              pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}$)/,
              message: '请填写正确的身份证号码',
              trigger: 'blur'
            },
            {required: true, message: '请输入身份证号', trigger: 'blur'}
          ],
          sex: [
            {required: true, message: '请选择性别', trigger: 'blur'}
          ]
        },
        sexList:[],
        editFormAdmin:[],
        activeName: 'first',
        editLoading: false,
        editFormVisible:false,
				sysName:'仁城诊所后台系统',
				collapsed:false,
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
      editLoginSubmit(){
        this.$confirm('确认修改吗?', '提示', {
          type: 'success'
        }).then(() => {
          this.editFormAdminLogin.id=JSON.parse(sessionStorage.getItem('user')).id;
          editAdminLogin(this.editFormAdminLogin).then((res)=>{
            if(res.data.msgId=='C200'){
              logoutAdmin().then()
              sessionStorage.removeItem('user');
              this.$router.push('/login');
              this.$notify.success({
                title: '成功',
                message: '修改成功'
              });
            }else{
              this.$message.error('修改失败!');
            }
          })
        })
        },
      editSubmit(){
        this.$confirm('确认修改吗?', '提示', {
          type: 'success'
        }).then(() => {
          editAdminInformation(this.editFormAdmin).then((res)=>{
            if(res.data.msgId=='C200'){
              logoutAdmin().then()
              sessionStorage.removeItem('user');
              this.$router.push('/login');
              this.$notify.success({
                title: '成功',
                message: '修改成功'
              });
            }else{
              this.$message.error('修改失败!');
            }
          })
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
      handleClick(tab, event) {
        console.log(tab, event);
      },
			onSubmit() {
				console.log('submit!');
			},
      adminInformation(){
        this.editFormAdmin=JSON.parse(sessionStorage.getItem('user'));
        this.editFormVisible=true;
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
          logoutAdmin().then()
					sessionStorage.removeItem('user');
					_this.$router.push('/login');
				}).catch(() => {

				});
			},
			//折叠导航栏
			collapse:function(){
				this.collapsed=!this.collapsed;
			},
			showMenu(i,status){
				this.$refs.menuCollapsed.getElementsByClassName('submenu-hook-'+i)[0].style.display=status?'block':'none';
			}
		},
		mounted() {
			var user = sessionStorage.getItem('user');
			if (user) {
				user = JSON.parse(user);
				this.sysUserName = user.name || '';
				this.sysUserAvatar = user.avatar || '';
			}
      findSexCoding().then((res)=>{
        this.sexList=res.data.result
      })
		}
	}

</script>

<style scoped lang="scss">
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
			color:#fff;
			.userinfo {
				text-align: right;
				padding-right: 35px;
				float: right;
				.userinfo-inner {
					cursor: pointer;
					color:#fff;
					img {
						width: 40px;
						height: 40px;
						border-radius: 20px;
						margin: 10px 0px 10px 10px;
						float: right;
					}
				}
			}
			.logo {
				//width:230px;
				height:60px;
				font-size: 22px;
				padding-left:20px;
				padding-right:20px;
				border-color: rgba(238,241,146,0.3);
				border-right-width: 1px;
				border-right-style: solid;
				img {
					width: 40px;
					float: left;
					margin: 10px 10px 10px 18px;
				}
				.txt {
					color:#fff;
				}
			}
			.logo-width{
				width:230px;
			}
			.logo-collapse-width{
				width:60px
			}
			.tools{
				padding: 0px 23px;
				width:14px;
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
				flex:0 0 230px;
				width: 230px;
				// position: absolute;
				// top: 0px;
				// bottom: 0px;
				.el-menu{
					height: 100%;
				}
				.collapsed{
					width:60px;
					.item{
						position: relative;
					}
					.submenu{
						position:absolute;
						top:0px;
						left:60px;
						z-index:99999;
						height:auto;
						display:none;
					}

				}
			}
			.menu-collapsed{
				flex:0 0 60px;
				width: 60px;
			}
			.menu-expanded{
				flex:0 0 230px;
				width: 230px;
			}
			.content-container {
				// background: #f1f2f7;
				flex:1;
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
