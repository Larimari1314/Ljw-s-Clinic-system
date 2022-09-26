# 诊所管理系统
[top]

## 功能介绍：
诊所管理系统功能包括：患者预约、购药、订单查看、订单管理等，医生处理预约、发药、预约管理、订单处理等，管理员管理全部基本信息

#### V-1.1 更新说明
修改系统登录权限验证，医生端添加角色医技医师，医技医师可为患者编写报告等，普通医师可查看患者当前绑定医技报告和历史医技报告等，用户端添加医技预约和历史医技预约查看、下载医技报告等，管理员端对医技、医技订单处理等。

### 本系统完全由本人开发，现发布github作为技术交流使用，严禁向公众出租、发售该系统。如若违规，本人依法追究民事责任。

## 软件架构
后端：后端使用Spring boot搭建环境，后台需开启redis
前端：使用vue、element plus、axios等
前后端分离项目

## 运行
1、下载前端和后端文件
2、前端运行前需要 npm i --legacy-peer-deps 命令
3、后端使用redis和mysql，修改application.yml中的参数文件，修改mysql地址和密码

## 以下仅展示部分功能
### 网站图片

### 患者登录端
![image](https://user-images.githubusercontent.com/87918586/188300980-dc36acf1-eefd-4ee1-b3ba-74c380cac86a.png)

### 医生登录端
![image](https://user-images.githubusercontent.com/87918586/188300988-04e9136e-6a05-44c4-87e9-3d8818699a45.png)

### 管理员登录端
![image](https://user-images.githubusercontent.com/87918586/188300993-fd23c01a-f05f-4def-8d4d-de3b635e17fd.png)

### 患者主页
![image](https://user-images.githubusercontent.com/87918586/188301018-cfbd2d16-9a72-4439-8054-1375bdbcd0e2.png)

### 医生主页
![image](https://user-images.githubusercontent.com/87918586/188301031-fbde4d26-7f09-4afd-b75f-77d7c7519fcc.png)

### 管理员主页
![image](https://user-images.githubusercontent.com/87918586/188301043-1a0d171f-6c0a-482f-9e9c-f31e654cc6c1.png)

### 患者预约
![image](https://user-images.githubusercontent.com/87918586/188301062-bb411243-2125-4e49-b066-4f3db45dd296.png)

### 预约支付
![image](https://user-images.githubusercontent.com/87918586/188301095-2dd87140-0ddd-486d-aee2-524138ebbc78.png)

### 医生处理预约
![image](https://user-images.githubusercontent.com/87918586/188301111-d1c35927-cf76-4ff1-b124-be05d9aa9039.png)
![image](https://user-images.githubusercontent.com/87918586/188301112-81230a30-d321-403b-8bb3-5c1a6c7884a1.png)

### 患者支付订单
![image](https://user-images.githubusercontent.com/87918586/188301129-1500a969-26ac-41a6-8acd-5e2b30578a23.png)
![image](https://user-images.githubusercontent.com/87918586/188301139-ca630642-a0ee-4cb5-847b-28b15b7dc648.png)
![image](https://user-images.githubusercontent.com/87918586/188301148-781e9b2e-31a2-41ec-b12d-e5f1ed706999.png)
![image](https://user-images.githubusercontent.com/87918586/188301168-84fa162a-96a3-42b7-9b6f-cb290fecd07c.png)

### 医生处理发药
![image](https://user-images.githubusercontent.com/87918586/188301180-86a35ea6-e086-47d0-b040-1b9dee2cc743.png)

### 患者订单查看
![image](https://user-images.githubusercontent.com/87918586/188301218-94a40faf-25f7-4cd1-83ff-6d5b4b3eeab5.png)

### 医生预约查看
![image](https://user-images.githubusercontent.com/87918586/188301245-c053a398-76c2-4a69-8212-2baf65cadbce.png)
### 医生个人信息修改
![image](https://user-images.githubusercontent.com/87918586/188301260-fa6acddc-3e4d-479f-8213-97757380bee7.png)

### 管理员端界面过多只显示部分功能
### 医生信息查看修改
![image](https://user-images.githubusercontent.com/87918586/188301291-062ac44f-4527-4d8f-b491-2e3018b69cae.png)
### 医生信息导出导入
![image](https://user-images.githubusercontent.com/87918586/188301309-1b9f9d0b-b1f8-43cc-9198-ff9bda870f04.png)
![image](https://user-images.githubusercontent.com/87918586/188301318-c9b386ba-867f-4ad2-b0cf-370a1113f01c.png)
### 广告管理
![image](https://user-images.githubusercontent.com/87918586/188301330-e2f72b6c-9948-44b9-904c-757ae4b5188c.png)
### 数据恢复删除
![image](https://user-images.githubusercontent.com/87918586/188301344-347d03a1-5dde-41a5-a9f4-04a942832df2.png)

## v1.1更新界面截图
医生医技角色主页
![image](https://user-images.githubusercontent.com/87918586/192399128-ec27ebab-65bb-41bb-bfc5-ff06fc448b7d.png)

### 普通医生角色主页
![image](https://user-images.githubusercontent.com/87918586/192399195-d010861a-02b2-4822-b7b1-79df681fde22.png)

### 患者预约医技
![image](https://user-images.githubusercontent.com/87918586/192399258-ac94578c-82a3-4a25-87f9-4d58bb70bb5b.png)
![image](https://user-images.githubusercontent.com/87918586/192399276-76288965-8c78-40d8-afe3-e482062a8d3b.png)
![image](https://user-images.githubusercontent.com/87918586/192399321-a4c803eb-ad67-4613-b1ff-598a240a568b.png)

### 普通医生可查看患者历史报告
![image](https://user-images.githubusercontent.com/87918586/192399419-e87a9679-404b-4918-ae20-a661dc891d89.png)

### 医技医生为患者填写报告
![image](https://user-images.githubusercontent.com/87918586/192399782-a41594f3-9057-418a-ad61-53f007a33845.png)

### 患者查看并下载报告
![image](https://user-images.githubusercontent.com/87918586/192399846-4a34ea24-5c86-4d38-b5b5-903cffbe97f5.png)

### 管理员主页
![image](https://user-images.githubusercontent.com/87918586/192399889-50deb03f-2264-4b54-a6c2-3476080dff89.png)
管理员部分页面重复度偏高不再展示


