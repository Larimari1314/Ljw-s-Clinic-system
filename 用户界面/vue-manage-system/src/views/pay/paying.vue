<template>
  <section>
    <div style="font-size: 50px;text-align: center">
      <h1>支付</h1>
      <el-button type="primary" @click="pays">确认支付</el-button>
    </div>
  </section>
</template>
<script>

import {payAppointment, scanPayStatus} from "../../api/api";

export default {
  data() {
    return {}
  },
  methods: {
    pays() {
      let that=this
      let id = that.$route.params.id
      payAppointment(id).then((res) => {
        if (res.data.msgId == 'C404') {
          this.$notify.error({
            title: '错误',
            message: '订单超时！'
          });
          this.$router.go(-2);
        }else if(res.data.msgId=='C200'){
          this.$notify.success({
            title: '成功',
            message: '支付成功！'
          });
        }else if(res.data.msgId=='C403'){
          this.$notify.error({
            title: '失败',
            message: '支付成功，请勿重复支付！'
          });
        }
      })
    },

  },
  mounted() {
    let that=this
    let id = that.$route.params.id
    scanPayStatus(id)
  }
}

</script>
