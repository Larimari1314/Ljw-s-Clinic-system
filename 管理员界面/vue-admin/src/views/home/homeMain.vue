<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card
            shadow="hover"
            class="mgb20"
            style="height:252px;"
        >
          <div class="user-info">
            <img
                :src="userInfo.avatar"
                class="user-avator"
                alt
            >
            <div class="user-info-cont">
              <div class="user-info-name">
                {{ userInfo.name }}
              </div>
              <div>{{ role }}</div>
            </div>
          </div>
          <div class="user-info-list">
            用户名称：
            <span>{{ userInfo.name }}</span>
          </div>
          <div class="user-info-list">
            身份证号：
            <span>{{ userInfo.identitynumber }}</span>
          </div>
        </el-card>
        <el-card
            shadow="hover"
            style="height:252px;"
        >
          <template #header>
            <div id="numbers" :style="{width: '400px', height: '270px'}"></div>
          </template>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-row
            :gutter="20"
            class="mgb20"
        >
          <el-col :span="8">
            <el-card
                shadow="hover"
                :body-style="{ padding: '0px' }"
            >
              <div class="grid-content grid-con-1">
                <i class="el-icon-user-solid grid-con-icon" />
                <div class="grid-cont-right">
                  <div class="grid-num">
                    <div class="fa fa-users">
                      {{ number.patientNumber }}
                    </div>
                  </div>
                  <div>用户数量</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card
                shadow="hover"
                :body-style="{ padding: '0px' }"
            >
              <div class="grid-content grid-con-2">
                <i class="el-icon-message-solid grid-con-icon" />
                <div class="grid-cont-right">
                  <div class="grid-num">
                    <div class="fa fa-user-plus">
                      {{number.doctorNumber}}
                    </div>
                  </div>
                  <div>医生数量</div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card
                shadow="hover"
                :body-style="{ padding: '0px' }"
            >
              <div class="grid-content grid-con-3">
                <i class="el-icon-s-goods grid-con-icon" />
                <div class="grid-cont-right">
                  <div class="grid-num">
                    <div class="fa fa-plus-square">
                      {{number.drugNumber}}
                    </div>
                  </div>
                  <div>药品数量</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
        <el-card
            shadow="hover"
            style="height:403px;"
        >
          <div id="daysNumber" :style="{width: '840px', height: '400px'}"></div>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <div id="doctorRegis" :style="{width: '600px', height: '500px'}"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <div id="stateStatistics" :style="{width: '600px', height: '500px'}"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {getAllValues, getDayNumber, getRegisDoctor, getStateStatistics, mainGetNumber} from "../../api/api";

export default {
  data() {
    return {
      numberList:[],
      numberSize:[],
      dayList:[],
      numberDay:[],
      number:[],
      msg: 'Welcome to kalacloud.com',
      chartLine: null,
    };
  },
  computed: {
    userInfo() {
      let userInfo = JSON.parse(sessionStorage.getItem('user'));
      return userInfo
    },
    role() {
      return this.userInfo.name.slice(0,5) === 'admin' ? '超级管理员' : '普通用户';
    },
  },
  mounted() {
    this.$nextTick(function() {
      this.drawLine()
    })
  },
  methods: {
    drawLine(){
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      // alert(JSON.stringify(configs))
      getAllValues(configs).then((res)=>{
        this.numberList=res.data[0]
        this.numberSize=res.data[1]
        let numbers = this.$echarts.init(document.getElementById('numbers'))
        numbers.setOption({
          color:[
            '#ffd900'
          ],
          title: { text: '诊所人数' },
          tooltip: {},
          xAxis: {
            data: this.numberList
          },
          yAxis: {},
          series: [{
            name: '数量',
            type: 'bar',
            data: this.numberSize,
            itemStyle: {
              normal:{
                color: function (params){
                  var colorList = ['rgb(237,0,41)','rgb(80,158,1)','rgb(99,135,213)','rgb(250,219,71)'];
                  return colorList[params.dataIndex];
                }
              },
              //鼠标悬停时：
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },
          }]
        });
      })
      getDayNumber(configs).then((res)=>{
        this.dayList=res.data[0]
        this.numberDay=res.data[1]
        let myChart = this.$echarts.init(document.getElementById('daysNumber'))
        myChart.setOption({
          title: { text: '就诊人数-天数表' },
          tooltip: {},
          xAxis: {
            type: 'category',
            data: this.dayList
          },
          yAxis: {
            type: 'value'
          },
          series: [{
            name: '数量',
            type: 'line',
            smooth: true,
            data: this.numberDay
          }]
        });
      })
      getStateStatistics(configs).then((res)=>{
        this.dayList=res.data[0]
        this.numberDay=res.data[1]
        let myChart = this.$echarts.init(document.getElementById('stateStatistics'))
        myChart.setOption({
          title: {
            text: '诊所订单情况',
            left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: '诊所订单情况',
              type: 'pie',
              radius: '50%',
              data:res.data,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              }
            }
          ]
        })
      })
      getRegisDoctor(configs).then((res)=>{
        this.dayList=res.data[0]
        this.numberDay=res.data[1]
        let myChart = this.$echarts.init(document.getElementById('doctorRegis'))
        myChart.setOption({
          tooltip: {
            trigger: '挂号级别医生人数'
          },
          legend: {
            top: '5%',
            left: 'center'
          },
          series: [
            {
              name: 'Access From',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2
              },
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: '40',
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: res.data
            }
          ]
        });
      })

    },
    getUserNumber(){
      let configs={
        headers: {
          token: sessionStorage.getItem('permissionToken')
        }
      };
      mainGetNumber(configs).then((res)=>{
        this.number=res.data.result[0]
      })
    }
  },created() {
    this.getUserNumber()
  }
};
</script>

<style scoped lang="scss">
.el-row {
  margin-bottom: 20px;
}

.grid-content {
  display: flex;
  align-items: center;
  height: 100px;
}

.grid-cont-right {
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: #999;
}

.grid-num {
  font-size: 30px;
  font-weight: bold;
}

.grid-con-icon {
  font-size: 50px;
  width: 100px;
  height: 100px;
  text-align: center;
  line-height: 100px;
  color: #fff;
}

.grid-con-1 .grid-con-icon {
  background: rgb(45, 140, 240);
}

.grid-con-1 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-2 .grid-con-icon {
  background: rgb(100, 213, 114);
}

.grid-con-2 .grid-num {
  color: rgb(45, 140, 240);
}

.grid-con-3 .grid-con-icon {
  background: rgb(242, 94, 67);
}

.grid-con-3 .grid-num {
  color: rgb(242, 94, 67);
}

.user-info {
  display: flex;
  align-items: center;
  padding-bottom: 20px;
  border-bottom: 2px solid #ccc;
  margin-bottom: 20px;
}

.user-avator {
  width: 120px;
  height: 120px;
  border-radius: 50%;
}

.user-info-cont {
  padding-left: 50px;
  flex: 1;
  font-size: 14px;
  color: #999;
}

.user-info-cont div:first-child {
  font-size: 30px;
  color: #222;
}

.user-info-list {
  font-size: 14px;
  color: #999;
  line-height: 25px;
}

.user-info-list span {
  margin-left: 70px;
}

.mgb20 {
  margin-bottom: 20px;
}

.todo-item {
  font-size: 14px;
}

.todo-item-del {
  text-decoration: line-through;
  color: #999;
}

.schart {
  width: 100%;
  height: 300px;
}
.table_operate{
  text-align: right;
  i{
    margin-left: 15px;
  }
}
.line-wrap{
  width:50%;
  height:400px;
}
</style>
