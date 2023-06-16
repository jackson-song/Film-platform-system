<template>
  <div>
      <el-row>
        <el-col :span="12">
          <div class="echart" id="mychart1" :style="myChartStyle"></div>
        </el-col>
        <el-col :span="12">
          <div class="echart" id="mychart2" :style="myChartStyle"></div>
        </el-col>
      </el-row>
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
          text: "书籍类型评论占比图示",
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
      this.myChart1 = echarts.init(document.getElementById("mychart1"));
      tableApi.getAllList().then(response=>{
        response.data.forEach(item=>{
          option.series[0].data.push({name:item.type,value:item.comments});
          option.legend.data.push(item.type);
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
          text: "书籍类型好评占比图示",
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
      tableApi.getGoodList().then(response=>{
        response.data.forEach(item=>{
          option.series[0].data.push({name:item.type,value:item.comments});
          option.legend.data.push(item.type);
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
                data: ['总评', '好评'],
                type: "plain",
                right: '2%',  //展示位置，具体可查看api
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {type: 'shadow'}
            },
            title: {
              text: "书籍类型评论对比图示",
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
                    // textStyle: {
                    //     color: "rgba(255,255,255,.6)",
                    //     fontSize: '12',
                    // },
                },
            }],
            yAxis: [{
                type: 'value',
                axisLabel: {
                    show: true,
                    // textStyle: {
                    //     color: "rgba(255,255,255,.6)",
                    //     fontSize: '12',
                    // },
                },
                axisTick: {
                    show: false,
                },
                axisLine: {
                    show: true,
                    // lineStyle: {
                    //     color: "rgba(255,255,255,.1	)",
                    //     width: 1,
                    //     type: "solid"
                    // },
                },
                splitLine: {
                    // lineStyle: {
                    //     color: "rgba(255,255,255,.1)",
                    // }
                }
            }],
            series: [
                {
 
                    type: 'bar',
                    name : "总评",
                    data: [],
                    barWidth: '40%', //柱子宽度
                    label: {
                      // 柱状图上方文本标签，默认展示数值信息
                      show: true,
                      position: "top"
                    }
                },
                {
                    type: 'bar',
                    name : "好评",
                    data: [],
                    barWidth: '40%', //柱子宽度
                    label: {
                      // 柱状图上方文本标签，默认展示数值信息
                      show: true,
                      position: "top"
                    }
                }
 
            ]
      };
      this.myChart3 = echarts.init(document.getElementById("mychart3"));
      tableApi.getAllList().then(response=>{
        response.data.forEach(item=>{
          option.xAxis[0].data.push(item.type);
          option.series[0].data.push(item.comments);
        });
        console.log(option);
        this.myChart3.setOption(option);
      });
      tableApi.getGoodList().then(response=>{
        response.data.forEach(item=>{
          option.series[1].data.push(item.comments);
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

