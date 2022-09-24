<template>
  <section>
    <div class="work-wrapper">
      <div class="logo">
        <img src="../../assets/img/log.png" alt="photo">
      </div>
      <div class="work-header"><h1>{{ information.name }}分析诊断报告单</h1></div>
      <div class="line"></div>
      <div class="work-content">
        <div class="content-up">
          <el-row :gutter="20">
            <el-col :span="4">姓名：{{ information.patientName }}</el-col>
            <el-col :span="4">年龄：{{ information.patientAge }}</el-col>
            <el-col :span="4">性别：{{ information.patientSex }}</el-col>
            <el-col :span="4">标本类型：{{ information.technologyname }}</el-col>
            <el-col :span="8" class="identityNumber" >身份证号:{{ information.identityNumber }}</el-col>
            <br/><br/>
            <el-col :span="6">送检医生：{{ information.doctorName }}</el-col>
            <el-col :span="18" class="inspection_unit">送检单位：锦州医科大学附属医院</el-col>
          </el-row>
        </div>
        <div class="line"></div>
        <div class="content-center">
          <el-row :gutter="20">
            <el-col :span="4">检测项目:</el-col>
            <el-col :span="18">通过观察血细胞的数量变化及形态分布从而判断血液状况及疾病的检查</el-col>
            <el-col :span="4">诊断依据:</el-col>
            <el-col :span="18">《人类细胞基因组学国际命名体系》ISCN 2020版</el-col>
          </el-row>
          <div class="line"></div>
          <el-row :gutter="20">
            <el-col :span="4">检查部位:</el-col>
            <el-col :span="18">
              <el-input v-model="tableData.checkPartsData" :rows="1" type="textarea" placeholder="请输入检查部位"/>
            </el-col>
            <div class="line"></div>
            <el-col :span="4">描述:</el-col>
            <el-col :span="18">
              <el-input v-model="tableData.inspectionReport" :rows="6" type="textarea" placeholder="请输入相关描述"/>
            </el-col>
          </el-row>
        </div>
        <div class="line"></div>
        <div class="content-foot">
          <el-row :gutter="20">
            <el-col :span="8">校验者：{{ information.doctorName }}</el-col>
            <el-col :span="8">审核医师：{{ information.doctorName }}</el-col>
            <el-col :span="8">批准人：{{ information.doctorName }}</el-col>
          </el-row>
        </div>
      </div>
      <div class="tools">
        <el-button type="primary" plain @click="cacheData">缓存数据</el-button>
        <el-button type="success" plain @click="endVisit">结束就诊</el-button>
      </div>
    </div>
  </section>
</template>

<script>
import {ElMessage, ElMessageBox} from "element-plus";
import {endOfVisit, findDiagnoseReportData} from "../../api/api";

export default {
  data() {
    return {
      information: {},
      tableData: {
        checkPartsData: '',
        inspectionReport: ''
      }
    }
  }, methods: {
    endVisit(){
      ElMessageBox.confirm(
          '确定结束此条就诊记录吗?注意：结束之后无法修改',
          '注意',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
          }
      )
          .then(() => {
            let that = this
            let configs = {
              headers: {
                token: sessionStorage.getItem('permissionToken')
              }
            };
            let para={
              id:that.$route.params.id,
              checkParts:this.tableData.checkPartsData,
              inspectionReport:this.tableData.inspectionReport
            }
            /*let that=this
            if(res.data.msgId==='C200'){
              that.$router.replace({path:'/payMedicalTechnology/'+res.data.result})
            }*/
            endOfVisit(para,configs).then((res)=>{
              if(res.data.msgId==='C404'){
                ElMessage.error('预约不存在！')
              }else  if(res.data.msgId==='C403'){
                ElMessage.error('当前预约已完成！')
              }else  if(res.data.msgId==='C405'){
                ElMessage.error('结束预约失败！')
              }else  if(res.data.msgId==='C200'){
                that.$router.replace({path:'/medicalTechnologyCurrentAppointment'})
                localStorage.removeItem(that.$route.params.id)
                ElMessage.success('结束预约成功！')
              }
            })
          })
    },
    cacheData() {
      ElMessageBox.confirm(
          '注意缓存的数据很容易丢失，若不存在问题，请尽快结束就诊?',
          '注意',
          {
            confirmButtonText: '确定缓存',
            cancelButtonText: '取消缓存',
            type: 'warning',
          }
      )
          .then(() => {
            if (this.tableData.checkPartsData === '') {
              ElMessage.error('请填写检查部位！')
              return;
            }
            if (this.tableData.inspectionReport === '') {
              ElMessage.error('请填写报告！')
              return;
            }
            let that = this
            let orderId = that.$route.params.id
            localStorage.setItem(orderId, JSON.stringify(this.tableData))
          })
    }
  }, mounted() {
    let that = this
    let orderId = that.$route.params.id
    //  查询本地中是否缓存数据
    if (JSON.parse(localStorage.getItem(orderId)) != null) {
      this.tableData = JSON.parse(localStorage.getItem(orderId))
    }
    let configs = {
      headers: {
        token: sessionStorage.getItem('permissionToken')
      }
    };
    findDiagnoseReportData(orderId, configs).then((res) => {
      if (res.data.msgId === 'C200') {
        this.information = res.data.result
      } else {
        ElMessage.error('订单不存在，请稍后重试！')
        window.open("http://localhost:3000/#/medicalTechnologyCurrentAppointment", "_self");
      }
    })
  }
}
</script>

<style scoped>
.work-wrapper {
  width: 900px;
  height: 900px;
  margin: 0px auto;
}

.work-content {
  width: 900px;
  margin: 0 auto;
  height: 430px;
}

.content-up {
  width: 900px;
  height: 80px;
}

.line {
  margin: 10px auto;
  width: 910px;
  height: 2px;
  background-color: #8c8888;
  text-align: center;
  font-size: 18px;
  color: black;
}

.content-foot {
  text-align: center;
}

.inspection_unit {
  text-align: right;
}
.identityNumber{
  text-align: right;
}

.tools {
  text-align: center;
}

h1 {
  text-align: center;
  margin: 0px auto;
  font-family: 宋体;
  font-weight: bold;
}

.logo {
  width: 80px;
  height: 80px;
  position: relative;
  border-radius: 80% 90% 100% 20%;
}

.logo:after {
  position: absolute;
  content: '';
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  /*box-shadow:0 0 50px 30px #fff9f9 inset;*/
}

img {
  width: 80px;
  height: 80px;
  display: block;
  margin-bottom: 20px;
  border-radius: 80% 90% 100% 20%;
}

</style>
