<template>
  <section>
    <div class="all">
    <div ref="imageWrapper">
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
            <el-col :span="8" class="identityNumber">身份证号：{{ information.identityNumber }}</el-col>
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
              {{ information.checkParts }}
            </el-col>
            <div class="line"></div>
            <el-col :span="4">描述:</el-col>
            <el-col :span="18">
              {{ information.inspectionReport }}
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
    </div>
    </div>
    <div class="tools">
      <el-button type="primary" @click="downloadImg">确定下载</el-button>
    </div>
    </div>
  </section>
</template>

<script>
import {ElMessage, ElMessageBox} from "element-plus";
import { viewTheReport} from "../../api/api";
import html2canvas from "html2canvas";
export default {
  data() {
    return {
      information: {},
    }
  }, methods: {
    downloadImg(){
      let that = this
      html2canvas(this.$refs.imageWrapper, {
        height: 700,
        useCORS: true // 解决文件跨域问题
      }).then(canvas => {
        let dataURL = canvas.toDataURL("image/png");
        if (dataURL !== "") {
          let alink = document.createElement("a");
          alink.href = dataURL;
          alink.download = that.$route.params.id+".png";
          alink.click();
        }
      });
    }
  }, mounted() {
    let that = this
    let orderId = that.$route.params.id
    //  查询本地中是否缓存数据
    let configs = {
      headers: {
        token: sessionStorage.getItem('permissionToken')
      }
    };
    viewTheReport(orderId, configs).then((res) => {
      if (res.data.msgId === 'C200') {
        this.information = res.data.result
      } else {
        ElMessage.error('订单不存在，请稍后重试！')
        window.open("http://localhost:3000/#/medicalTechnologyOrder", "_self");
      }
    })
  }
}
</script>

<style scoped>
.work-wrapper {
  width: 900px;
  height: 420px;
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

.all{
  position: relative;
  height: 900px;
  width:1200px;
  margin: 0px auto;

}
.tools {
 position: absolute;
  left: 550px;
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
