<template>
  <section class="paybg">
    <h1 style="text-align: center;font-size: 30px;font-family: 华文行楷;color: red;margin: 0px">支付界面</h1>
    <h2 style="color: black">此次支付编号为：{{ payId }}&emsp;&emsp;&emsp;支付倒计时：{{ totalTime }}</h2>
    <div class="table-container">
      <table id="msg" class="msg">
        <tr>
          <td style="text-align: left">订单编号:&nbsp;&nbsp;{{ payList.id }}</td>
          <td style="text-align: left">预约科室:&nbsp;&nbsp;{{ payList.departmentName }}</td>
        </tr>
        <tr>
          <td style="text-align: left">患者编号:&nbsp;&nbsp;{{ payList.patientId }}</td>
          <td style="text-align: left">订单状态:&nbsp;&nbsp; {{ payList.orderState }}</td>
        </tr>
        <tr>
          <td style="text-align: left">预约患者:&nbsp;&nbsp;{{ payList.patientName }}</td>
          <td style="text-align: left">预约医生:&nbsp;&nbsp; {{ payList.doctorName }}</td>
        </tr>
        <tr>
          <td style="text-align: left">预约时间:&nbsp;&nbsp;{{ payList.reserveTime }}</td>
          <td style="text-align: left">患者身份证号:&nbsp;&nbsp;{{ payList.identityNumber }}</td>
        </tr>
      </table>
      <h1>总金额：{{payList.totalPrice}}</h1>
    </div>
    <div :hidden="hiddens">
      <h1 style="color: red">请扫描下方二维码链接支付</h1>
      <div>
        <img  :src="'data:image/png;base64,' + pauImg" alt="支付图片">
      </div>
    </div>
    <div :hidden="payingState" style="color: #13ce66;font-size: 30px">
      <h1>用户已扫描，请点击支付按钮支付</h1>
    </div>
    <div :hidden="successPaying" style="color: #13ce66;font-size: 30px">
      用户支付成功,正在跳转到发药界面
    </div>
  </section>
</template>

<script>
import {
  getOrderInformationPaying,
  getPayAppointmentInformation,
  getQRCode,
  getQRCodeOrder,
  paymentStatus
} from "../../api/api";

export default {
  data() {
    return {
      successPaying:true,
      payingState:true,
      hiddens:false,
      payList:[],
      payId: '',
      pauImg:'',
      pprice:0,
      totalTime: 300,
      canClick: false
    }
  },
  methods: {
    GetQRCode(){
      getQRCodeOrder(this.payId).then((res)=>{
        if(res.data.msgId=='C200'){
          this.pauImg= res.data.result
        }
      })
    },
    //获取当前支付状态
    paymentStatus(){
      let that=this;
      paymentStatus(this.payId+this.payList.patientId).then((res)=>{
        if(res.data.msgId=='C201'){
          this.hiddens=true
          this.payingState=false
          this.successPaying=true
        }else if (res.data.msgId=='C404'){
          this.hiddens=false
          this.payingState=true
          this.successPaying=true
        }else if(res.data.msgId=='C200'){
          this.hiddens=true
          this.payingState=true
          this.successPaying=false
          this.$message({
            message: '支付成功，正在跳转发药界面',
            type: 'success'
          });
          that.$router.push('/unpaidOrder')
        }
      })
    },
    sendMsg() {
      if (this.canClick) return
      this.canClick = true
      this.content = this.totalTime
      let clock = window.setInterval(() => {
        this.totalTime--
        this.content = this.totalTime
        if (this.totalTime < 0) {
          window.clearInterval(clock)
          this.totalTime = 10
          this.canClick = false
        }
      }, 1000)
    }
  },
  mounted() {
    let that=this
    this.payId = that.$route.params.id
    getOrderInformationPaying(this.payId).then((res)=>{
      if(res.data.msgId=='C404'){
        /*订单超时或不存在*/
        this.$notify.error({
          title: '错误',
          message: '订单超时！'
        });
        that.$router.go(-1);
      }else if(res.data.msgId=='C403'){
        this.$notify.error({
          title: '错误',
          message: '获取基础信息出错！'
        });
        that.$router.go(-1);
      }else if (res.data.msgId=='C200'){
        this.payList=res.data.result.informationOrder
        this.GetQRCode();
      }
    })
    this.sendMsg();
    setInterval(this.paymentStatus, 2000);
  }
}
</script>

<style scoped>
.paybg {
  height: 100%;
  background: linear-gradient(125deg, #ECFCFF 0%, #ECFCFF 40%, #B2FCFF calc(40% + 1px), #B2FCFF 60%, #5EDFFF calc(60% + 1px), #5EDFFF 72%, #3E64FF calc(72% + 1px), #3E64FF 100%);
  margin: 0 auto;
}
.demo-date-picker .block:last-child {
  border-right: none;
}
.demo-date-picker .demonstration {
  display: block;
  color: var(--el-text-color-secondary);
  font-size: 14px;
  margin-bottom: 20px;
}
.confirm > span {
  flex: 1;
  height: 50px;
  line-height: 50px;
  font-size: 16px;
  text-align: center;
}

.confirm > span:nth-child(1) {
  color: red;
}

.confirm > span:nth-child(2) {
  border-left: 1px solid #eee;
}


.title > p {
  height: 30px;
  line-height: 40px;
  text-align: center;
  font-size: 18px;
  font-weight: bold;

}


.content > table {
  padding: 0 10px 10px 10px;
  margin: 0 auto;
  width: 100%;

}

.msg {
  width: 100%;
  height: 150px;
  text-align: center;
  table-layout: fixed
}

</style>
