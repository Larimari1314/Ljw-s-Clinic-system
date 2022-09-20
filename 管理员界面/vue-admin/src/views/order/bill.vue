<template>
  <section>
    <el-button type="primary" @click.native="handleDownload" :disabled="sameOrder" style="width: 150px;height: 50px;margin: auto">下载发票</el-button>
      <div ref="imageWrapper">
        <div class="invoicMain">
          <div class="invoiceHeader">
            <div class="headerLeft">
              <div>
                <p class="c-red">{{orderInformation.id}}</p>
              </div>
            </div>
            <div class="headerMiddle">
              <h1>增值税专用发票</h1>
              <div class="line"></div>
            </div>
            <div class="headerRight">
              <p><strong>№</strong><span class="c-red">{{orderInformation.id}}</span></p>
              <p>3700194130</p>
              <p>35891172</p>
              <p><label>开票日期：</label><span>{{ dateNow }}</span></p>
            </div>
          </div>
          <div class="invoiceBody">
            <div class="userInfo">
              <div class="buy">购买方</div>
              <ul>
                <li>
                  <label>患&nbsp;&nbsp;者&nbsp;&nbsp;名&nbsp;&nbsp;称：</label><span>{{orderInformation.patientName}}</span>
                </li>
                <li>
                  <label>身份证号：</label><span class="f-small">{{orderInformation.identityNumber}}</span>
                </li>
                <li>
                  <label>预约时间：</label><span class="f-small">{{orderInformation.reserveTime}}</span>
                </li> <li>
                  <label>开票科室：</label><span class="f-small">{{orderInformation.departName}}</span>
                </li>
              </ul>
              <div class="password">密码区</div>
              <div class="pwdInfo"><span class="f-big">0357+39177127583965+0*13>3>0<><22908-+<+03*<-84>40><3<64-076746-2+2082*9112+057188/07-228<135701+4/>039*1630-817</span>></div>
            </div>
            <div>
              <table class="GoodsTable" cellpadding="0" cellspacing="0">
                <thead>
                <tr>
                  <td style="width: 24%">药品名称</td>
                  <td style="width: 10%">规格型号</td>
                  <td style="width: 7%">单位</td>
                  <td style="width: 10%">数 量</td>
                  <td style="width: 10%">单 价</td>
                  <td style="width: 16%">金 额</td>
                </tr>
                </thead>
                <tbody class="invoice-list" v-for="drug in drugList" style="height: 126px;">
                <tr>
                  <td><span>{{ drug.drugName }}</span></td>
                  <td><span>{{drug.value}}</span></td>
                  <td><span>盒</span></td>
                  <td><span>{{drug.number}}</span></td>
                  <td><span>{{drug.drugPrice}}</span></td>
                  <td><span>{{drug.totalPrice}}</span></td>
                </tr>
                </tbody>
                <tfoot>
                <tr class="total">
                  <td>合 &nbsp;&nbsp;&nbsp;计</td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td><span class="rmb">￥</span>{{totalPrice}}</td>
                  <td><span class="rmb">￥</span>{{totalPrice}}</td>
                </tr>
                <tr class="GoodsTotal">
                  <td>价税合计(大写)</td>
                  <td colspan="7">
                    <div style="width: 100%;display:flex">
                      <div type="text" style="width: 60%"><span class='c-black'>ⓧ{{capitalTotalPrice}}</span></div>

                      <div type="text" style="width: 30%"> (小写) <span class='c-black'><span class="rmb">￥</span>{{totalPrice}}</span></div>
                    </div>

                  </td>
                </tr>
                </tfoot>
              </table>
              <div class="userInfo">
                <div class="buy">销售方</div>
                <ul>
                  <li>
                    <label>医&nbsp;&nbsp;院&nbsp;&nbsp;名&nbsp;&nbsp;称：</label><span>仁城诊所</span>
                  </li>
                  <li>
                    <label>纳税人识别号：</label><span class="f-big">913564523596023930</span>
                  </li>
                  <li>
                    <label>地址、&nbsp;电话：</label><span class="f-small2">辽宁省锦州市柳荫区十三路888888号岐山公寓五号楼三单元305号 8888-88888888</span>
                  </li>
                  <li>
                    <label>开户行及账号：</label><span class="f-small">中国银行股份有限公司济南大学科技园支行 6225885410839945</span>
                  </li>
                </ul>
                <div class="password">备注</div>
                <div class="pwdInfo"></div>
              </div>
            </div>

          </div>
          <!-- invoice body start -->
          <ul class="invoicetFooter">
            <li>
              <label>收款人：</label><span>{{orderInformation.doctorName}}</span>
            </li>
            <li>
              <label>复核：</label><span>{{orderInformation.doctorName}}</span>
            </li>
            <li>
              <label>开票人：</label><span>{{orderInformation.doctorName}}</span>
            </li>
            <li>
              <label>销售方：（章）</label>
            </li>
          </ul>
        </div>
      </div>
  </section>
</template>

<script>
import {numToCny } from "../../vuex/tools"
import html2canvas from "html2canvas"
import {invoiceService} from "../../api/api";
export default {
  data(){
    return{
      sameOrder:false,
      drugList:[],
      orderInformation:'',
      totalPrice:0,
      capitalTotalPrice:'',
      dateNow:''
    }
  },
  methods:{
    dateFormat:function(time) {
      var date=new Date(time);
      var year=date.getFullYear();
      var month= date.getMonth()+1<10 ? "0"+(date.getMonth()+1) : date.getMonth()+1;
      var day=date.getDate()<10 ? "0"+date.getDate() : date.getDate();
      var hours=date.getHours()<10 ? "0"+date.getHours() : date.getHours();
      var minutes=date.getMinutes()<10 ? "0"+date.getMinutes() : date.getMinutes();
      var seconds=date.getSeconds()<10 ? "0"+date.getSeconds() : date.getSeconds();
      return year+"年"+month+"月"+day+"日 "+hours+":"+minutes+":"+seconds;
    },
    handleDownload(){
      html2canvas(this.$refs.imageWrapper).then(canvas => {
        let dataURL = canvas.toDataURL("image/png");
        if (dataURL !== "") {
          let alink = document.createElement("a");
          alink.href = dataURL;
          alink.download = this.orderInformation.id+".png";
          alink.click();
        }
      });
    }
  },
  mounted() {
    let id = this.$route.params.id;
    let configs={
      headers: {
        token: sessionStorage.getItem('permissionToken')
      }
    };
    invoiceService(id,configs).then((res)=>{
      if(res.data.msgId=='C200'){
        this.drugList=res.data.result.drugList
        this.orderInformation=res.data.result.orderListById
        this.totalPrice=res.data.result.orderTotalPrice
        this.capitalTotalPrice=numToCny( this.totalPrice)
        this.dateNow=this.dateFormat(new Date())
      }else if(res.data.msgId=='C404'){
        window.location.href="#/orderViewed"
      }
    })
  }
}
</script>

<style scoped>

* {
  padding: 0;
  margin: 0;
}

ul, ul li {
  list-style: none;
  margin: 0;
  padding: 0;
}

body {
  font-family: 'simsun';
}

label {
  color: #008000;
}

.rmb {
  font-family: Arial, Helvetica, sans-serif;
}

.c-red {
  color: #ff3133;
}

.c-black {
  color: #000;
}

.f-big {
  font-size: 20px;
}

.f-small {
  font-size: 12px;
}

.f-small2 {
  font-size: 10px;
}

.invoicMain {
  width: 920px;
  margin: 0 auto;
  font-size: 14px;
  color: #000;
  padding: 20px;
  border: 1px dotted #000;
}

.toptip {
  text-align: center;
  padding-top: 4px;
  height: 20px;
}

.invoiceHeader {
  height: 126px;
  display: flex;
  /* justify-content: space-between; */
  align-items: center;
}

.headerLeft {
  width: 300px;
  display: flex;
}

.headerLeft div:nth-child(1) {
  width: 85px;
  line-height: 26px;
}

.headerLeft div p {
  line-height: 26px;
}

.headerLeft div:nth-child(2) p.c-red {
  width: 170px;
  height: 46px;
  text-align: center;
  line-height: 42px;
  font-size: 24px;
  letter-spacing: 2px;
}

.headerLeft div p.c span {
  font-size: 18px;
  letter-spacing: 1px;
}

.headerRight {
  padding-left: 28px;
  width: 316px;
}

.headerRight p:nth-child(1) {
  font-size: 24px;
}

.headerRight p:nth-child(1) strong {
  font-family: 'LiSu';
  font-size: 30px;
  padding-right: 20px;
}

.headerRight p:nth-child(2) {
  text-align: right;
  margin-top: -5px;
}

.headerRight p:nth-child(3) {
  text-align: right;
  font-size: 18px;
  letter-spacing: 1px;
}

.headerRight p:nth-child(4) {
  text-align: center;
  letter-spacing: 1px;
}

.headerMiddle {
  text-align: center;
  width: 300px;
}

.headerMiddle h1 {
  font-size: 32px;
  color: #008000;
  padding-bottom: 22px;
}

.total .rmb {
  font-size: 16px;
}

.line {
  height: 2px;
  border-top: #008000 1px solid;
  border-bottom: #008000 1px solid;
  margin-bottom: 30px;
}

.headerRight li {
  line-height: 24px;
}

.invoiceBody {
  border: 1px solid #008000;
}

.userInfo {
  width: 100%;
  display: flex;
  align-items: center;
  height: 96px;
  border-bottom: 1px solid #008000;
}

.userInfo ul {
  width: 50%;
  margin: 0 5px;
  padding: 0;
}

.userInfo ul li {
  line-height: 24px;
}

.userInfo ul li:nth-child(2) .f-big {
  padding-left: 24px;
  letter-spacing: 2px;
}

.userInfo ul li:nth-child(3) {
  position: relative;
}

.userInfo ul li .f-small2 {
  position: absolute;
  width: 120%;
  transform: scale(0.8);
  left: 44px;
}

.buy {
  width: 20px;
  border-right: 1px solid #008000;
  padding: 0 10px;
  text-align: center;
  height: 100%;
  display: flex;
  align-items: center;
  color: #008000;
  line-height: 20px;
}

.password {
  width: 20px;
  padding: 0 10px;
  border-right: 1px solid #008000;
  border-left: 1px solid #008000;
  text-align: center;
  height: 100%;
  display: flex;
  align-items: center;
  color: #008000;
  line-height: 20px;
}

.pwdInfo {
  flex: 1;
  padding: 0 15px;
  height: 88px;
  overflow: hidden;
  word-break: break-all;
  letter-spacing: 2px;
}

.goodsInfo {
  height: 210px;
  margin: 0;
  padding: 0;
}

.goodsInfo li {
  display: flex;
  color: #008000;
  text-align: center;
}

.name {
  width: 260px;
  border-right: 1px solid #008000;
}

.spec {
  width: 140px;
  border-right: 1px solid #008000;
}

.qty {
  width: 108px;
  border-right: 1px solid #008000;
}

.unit, .taxRate {
  width: 65px;
  border-right: 1px solid #008000;
}

.qty, .price {
  width: 160px;
  border-right: 1px solid #008000;
}

.money {
  flex: 1;
  border-right: 1px solid #008000;
}

.taxAmount {
  flex: 1;
}

.GoodsTable {
  height: 210px;
  width: 100%;
  border-collapse: collapse;
  table-layout: fixed
}

.GoodsTable td {
  border-right: 1px solid #008000;
  color: #008000;
  padding: 0 4px;
}

.invoice-list td {
  color: #000;
  vertical-align: middle;
}

.invoice-list td:nth-child(5), .invoice-list td:nth-child(6), .invoice-list td:nth-child(7), .invoice-list td:nth-child(8), .total td:nth-child(5), .total td:nth-child(6), .total td:nth-child(7), .total td:nth-child(8) {
  text-align: right;
}

.invoice-list {
  height: 126px;
  overflow: hidden;
}

.invoice-list tr td {
  height: 21px;
}

.GoodsTable thead tr {
  height: 24px;
  text-align: center;
}

.GoodsTotal {
  height: 34px;
}

.GoodsTotal {
  border-top: 1px solid #008000;
  border-bottom: 1px solid #008000;
}

.total td {
  color: #000;
}

.total td:nth-child(1) {
  text-align: center;
  color: #008000;
}

.total td:nth-child(6), .total td:nth-child(8) {
  font-size: 18px;
}

.invoicetFooter {
  padding-top: 2px;
  display: flex;
  justify-content: space-between;
}

.invoicetFooter li {
  width: 25%;
}

.invoiceHeader-left {
  width: 67%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
</style>
