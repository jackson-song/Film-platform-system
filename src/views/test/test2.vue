<template>
  <div>
      <div class="echart" id="mychart1" :style="myChartStyle"></div>
      <div class="echart" id="mychart2" :style="myChartStyle"></div>
      <div class="echart" id="mychart3" :style="myChartStyle"></div>
  </div>
  
</template>

<script>
import * as echarts from "echarts";
import tableApi from "@/api/table";
export default {
  data() {
    return {
      myChart1: {},
      myChart2: {},
      myChart3: {},
      myChartStyle: { float: "left", width: "100%", height: "500px" } //图表样式
    };
  },
  mounted() {
    this.initEcharts1();
    this.initEcharts2();
    this.initEcharts3();
  },
  methods: {
    initEcharts1() {
      const option = {
            legend: {
                // data: '次数',
                type: "plain",
                right: '2%',  //展示位置，具体可查看api
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {type: 'shadow'}
            },
            title: {
              text: "近一周内每一天的登录人数（活跃人数）",
              top: "0%",
              left: "center"
            },
            grid: {
                left: '10%',
                top: '30px',
                right: '10%',
                bottom: '20%',
                containLabel: true
            },
            xAxis: [{
                type: 'category',
                data: [],
                axisLine: {
                    show: true,
                },
                axisTick: {
                    show: false,
                },
                axisLabel: {
                    interval: 0,
                    show: true,
                    splitNumber: 15,
                },
            }],
            yAxis: [{
                type: 'value',
                axisLabel: {
                    show: true,
                },
                axisTick: {
                    show: false,
                },
                axisLine: {
                    show: true,
                }
            }],
            series: [
                {
                    type: 'line',
                    name : "次数",                    
                    data: [],
                    label: {
                      show: true,
                      position: "top",
                      rotate: 0 	// 旋转角度
                    },
                },
            ]
      };
      this.myChart1 = echarts.init(document.getElementById("mychart1"));
      tableApi.getWeekList().then(response=>{
        response.data.forEach(item=>{
          option.xAxis[0].data.push(item.logintime);
          option.series[0].data.push(item.times);
        });
        this.myChart1.setOption(option);
      });
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        this.myChart1.resize();
      });
    },
    initEcharts2() {
      // 饼图
      const option = {
        legend: {
          // 图例
          data: [],
          right: "10%",
          top: "20%",
          orient: "vertical"
        },
        title: {
          // 设置饼图标题，位置设为顶部居中
          text: "活跃度最高的前5个用户",
          top: "0%",
          left: "center"
        },
        series: [
          {
            type: "pie",
            label: {
              show: true,
              formatter: "{b} : {c} ({d}%)" // b代表名称，c代表对应值，d代表百分比
            },
            radius: "50%", //饼图半径
            data: []
          }
        ]
      };
      this.myChart2 = echarts.init(document.getElementById("mychart2"));
      tableApi.getMostList().then(response=>{
        response.data.forEach(item=>{
          option.series[0].data.push({name:item.username,value:item.times});
          option.legend.data.push(item.username);
        });
        this.myChart2.setOption(option);
      });
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        this.myChart2.resize();
      });
    },
    initEcharts3() {
      const option = {
            legend: {
                // data: '次数',
                type: "plain",
                right: '2%',  //展示位置，具体可查看api
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {type: 'shadow'}
            },
            title: {
              text: "近一年每月的登录人数",
              top: "0%",
              left: "center"
            },
            grid: {
                left: '10%',
                top: '30px',
                right: '10%',
                bottom: '20%',
                containLabel: true
            },
            xAxis: [{
                type: 'category',
                data: [],
                axisLine: {
                    show: true,
                },
                axisTick: {
                    show: false,
                },
                axisLabel: {
                    interval: 0,
                    show: true,
                    splitNumber: 15,
                },
            }],
            yAxis: [{
                type: 'value',
                axisLabel: {
                    show: true,
                },
                axisTick: {
                    show: false,
                },
                axisLine: {
                    show: true,
                }
            }],
            series: [
                {
                    type: 'bar',
                    name : "次数",                    
                    data: [],
                    label: {
                      show: true,
                      position: "top",
                    },
                },
            ]
      };
      this.myChart3 = echarts.init(document.getElementById("mychart3"));
      tableApi.getMonthList().then(response=>{
        response.data.forEach(item=>{
          option.xAxis[0].data.push(item.time);
          option.series[0].data.push(item.times);
        });
        console.log(option);
        this.myChart3.setOption(option);
      });
      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        this.myChart3.resize();
      });
    }
  }
};
</script>

