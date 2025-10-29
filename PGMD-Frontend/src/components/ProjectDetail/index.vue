<template>
  <sider-nav
    v-if="isShowSiderNav"
    :navList="navList"
    :changeFlag="reloadSideNav"
  ></sider-nav>
  <div class="projectsBox">
    <div class="overViewBox">
      <div class="headerTitle">
        <a 
          target="_blank" 
          :href="choosingProject.link">
          Project {{ choosingProject.project }}
        </a>
      </div>
      <div class="overViewMain">
        <div
          class="item"
          v-for="(item, index) in overviewLabels"
          :key="index"
        >
          <span class="title">{{ item.label }}:</span>
          <span class="content" v-if="item.prop != 'phenotypes'">{{
            choosingProject[item.prop]
          }}</span>
          <div class="content" v-else>
            <div
              v-for="(value, index) in choosingProject[item.prop].split(
                ';'
              )"
              :key="index"
            >
              {{ value }}
            </div>
          </div>
        </div>
        <div class="item">
          <span class="title">Feed composition:</span>
          <a 
            class="content download-link"
            @click="downloadFeedComposition(choosingProject.project)"
            >
            click to download sheet
          </a>
        </div>
        This is a curated project, scroll to page bottom to see more information.
      </div>
    </div>
    <div class="nutrientBox">
      <div class="headerTitle">Nutritional Composition</div>
      <div class="nutrientBox_note">
            (Notes : DM: dry matter;CP: crude protein;CEE: crude ether extract;CF:crude fibre;
            NDF:neutral detergent fiber; ADF acid detergent fibre; CA: calcium; 
            TP: Total phosphorus; AP: available phosphorus; DE: digestible energy.)
          </div>
      <div class="nutrientMain">
        <el-table
          :data="nutritionData"
          border
          stripe
          max-height="516px"
          style="width: 100%"
          :cell-style="emptyHandler"
        >
          <el-table-column
            v-for="(item, index) in nutritionTableColumns"
            :key="index"
            :label="item.label"
            :width="110"
          >
            <template #header>
              <el-tooltip
                effect="customized"
                placement="top"
              >
                <template #content>
                  <div style="max-width: 160px">
                    {{ item.label }}
                  </div>
                </template>
                <span v-if="item.label && item.label.length <= 30">
                  {{ item.label }}
                </span>
                <span v-if="item.label && item.label.length > 30">
                  {{ item.label.substring(0, 30) + "..." }}
                </span>
              </el-tooltip>
            </template>
            <template #default="{ row }">
              <el-tooltip
                effect="customized"
                placement="top"
              >
                <template #content>
                  <div style="max-width: 160px">
                    {{ row[item.prop] }}
                  </div>
                </template>
                <span v-if="row[item.prop] && row[item.prop].length <= 30">
                  {{ row[item.prop] }}
                </span>
                <span v-if="row[item.prop] && row[item.prop].length > 30">
                  {{ row[item.prop].substring(0, 30) + "..." }}
                </span>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <div class="MicrobialBox" v-show="microbialShow">
          <el-radio-group class="radio" v-model="radio1">
                <el-radio @change="changeData" label="1" size="large">Species</el-radio>
                <el-radio @change="changeData" label="2" size="large">Genus</el-radio>
                <el-radio @change="changeData" label="3" size="large">Family</el-radio>
                <el-radio @change="changeData" label="4" size="large">Order</el-radio>
                <el-radio @change="changeData" label="5" size="large">Class</el-radio>
                <el-radio @change="changeData" label="6" size="large">Phylum</el-radio>
          </el-radio-group>
          <div id="stackedBar" style="width: 100%; height: 600px;">
          </div>
    </div>
    <div class="groupBox" v-show="statisticsShow" >
      <el-radio-group class="radio" v-model="radio2">
        <el-radio @change="changeGroupData" label="1" size="large">Genus</el-radio>
        <el-radio @change="changeGroupData" label="2" size="large">Phylum</el-radio>
      </el-radio-group>
      <div id="groupShow" style="width: 100%;height:500px ;"></div>
    </div>
    <div v-show="lefseShow">
      <div
          id="negativeBar"
          style="width: 100%; 
          height: 700px; 
          margin-top: 30px">
      </div>
      <div v-show="lefseShow">
          <div ref="radialTree" id="radialTree" style="width: 100%; height: 700px; margin-top: 30px"></div>
      </div>
      <div class="heatmap">
        <div v-show="heatmapShow" id="heatmap" style="width: 100%; height: 350px"></div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, defineProps, computed } from "vue";
import * as echarts from "echarts";
import {
  getStackedBarData,
  getNegativeBarData,
  getNutritionData,
  downloadTable,
  getPhenotypeData,
  getCountgroupData
} from "../../api/Browse/index";
import { View, parse } from "vega";
import { Handler } from "vega-tooltip";
import { ElMessage } from "element-plus";

const props = defineProps({
    choosingProject: {
        type: Object,
        require: true,
    },
    isShowSiderNav: {
        type: Boolean,
        require: false,
        default: true
    },
})
const choosingProject = computed(() => props.choosingProject);
const isShowSiderNav = ref(props.isShowSiderNav);

// 处理空白单元格
const emptyHandler = ({row,column}) => {
  row[column.property] = row[column.property] || ''
}

// 侧边导航栏
const navList = ref([
    {
        name: 'Overview',
        location: '.overViewBox'
    },
    {
        name: 'Nutritional Composition',
        location: '.nutrientBox'
    },
    // {
    //     name:'Stacked Bar',
    //     location:'.MicrobialBox'
    // },
    {
        name:'Relative abundance(samples)',
        location:'#stackedBar'
    },
    {
        name: 'Relative abundance(Experimental grouping)',
        location: '#groupShow'
    },
    {
        name:'LDA effect size',
        location:'#negativeBar'
    },
    {
        name:'Evolutionary branching map',
        location:'#radialTree'
    },
    {
        name: 'Cross-project analysis',
        location:'#heatmap'
    }
])

// 重新加载侧边导航栏
const reloadSideNav = ref(0)

// overview部分

// 营养表格
const nutritionData = ref([]);

// 营养成分表格标签
const nutritionTableColumns = ref([
    {prop: "nutrientId",label: "Nutrient ID",},
    {prop: "bioProject",label: "BioProject",},
    {prop: "dm",label: "DM",},
    {prop: "cp",label: "CP",},
    {prop: "ee",label: "EE",},
    {prop: "cf",label: "CF",},
    {prop: "nfe",label: "NFE",},
    {prop: "ash",label: "Ash",},
    {prop: "ndf",label: "NDF",},
    {prop: "adf",label: "ADF",},
    {prop: "starch",label: "Starch",},
    {prop: "ca",label: "Ca",},
    {prop: "tp",label: "TP",},
    {prop: "ep",label: "EP",},
    {prop: "descriptiveInfo",label: "Descriptive Info",},
    {prop: "linkDoi",label: "Link DOI",},
])

// overview属性名
const overviewLabels=ref([
    {
        label:'Project ID',
        prop:'project'
    },
    {
        label:'Project description',
        prop:'description'
    },
    {
        label:'Total number of runs',
        prop:'totalNumbersOfRuns'
    },
    {
        label:'Associated phenotypes',
        prop:'phenotypes'
    },
    {
        label:'Related publications',
        prop:'title',
    },
    {
        label:'Experimental Design',
        prop:'experimentalDesign'
    }
])


// 下载feed composition
const downloadFeedComposition=(projectName)=>{
  ElMessage.info('downloading...')
  downloadTable(projectName)
  .then(res=>{
    // 根据返回的文档流数据生成下载链接
    const blob = new Blob([res.data], { type: "application/vnd.ms-excel" });
    const downloadElement = document.createElement("a");
    const href = window.URL.createObjectURL(blob); //创建下载的链接
    downloadElement.href = href;
    downloadElement.download = `${projectName}_feed_composition.xlsx`; //下载后文件名
    document.body.appendChild(downloadElement);
    downloadElement.click(); //点击下载
    document.body.removeChild(downloadElement); //下载完成移除元素
    window.URL.revokeObjectURL(href); //释放掉blob对象
    ElMessage.success('download success!')
  }).catch(err=>{
    console.error('request error!',err)
    ElMessage.error('no feed composition table!')
  })
}

// 可视化部分

const testName = ref("PRJNA540380");
const testName2 = ref("PRJEB36359");
const stackedBarData = ref([]);
const groupedDataByRuns = ref([]);
const microbialNames = ref([]);
const microbialShow = ref(true);
const changedData = ref([]);
const radio1 = ref("1");

// 处理microbial表数据
function microbialData() {
  const myChart = echarts.init(document.getElementById("stackedBar"));
  // 显示加载动画
  myChart.showLoading({   // 加载动画的文字
    color: '#007e37',       // 加载动画的圆环的颜色
    maskColor: 'rgba(255, 255, 255, 0.8)',  // 加载动画的背景颜色
  }); // 显示加载动画
  getStackedBarData(testName.value)
    .then((res) => {
      // console.log(testName.value);
      stackedBarData.value = res.data;
      if(res.data.length == 0)
      {
        microbialShow.value = false;
      }
      else {
        // 将获取的数据先按照level(Species、Genus、Family、Order、Class、Phylum)进行分组
        stackedBarData.value = stackedBarData.value.reduce((acc, cur) => {
          if (!acc[cur?.level]) {
            acc[cur?.level] = [];
          }
          acc[cur?.level].push(cur);
          return acc;
        }, {});
        // 将分组后的数据再按照样本号(runs)进行分组
        // groupedDataByRuns.value = stackedBarData.value.reduce((acc, cur) => {
        //   if (!acc[cur.runs]) {
        //     acc[cur.runs] = [];
        //   }
        //   acc[cur.runs].push(cur);
        //   return acc;
        // }, {});
        for (let key in stackedBarData.value) {
          groupedDataByRuns.value[key] = stackedBarData.value[key].reduce((acc, cur) => {
            if (!acc[cur.runs]) {
              acc[cur.runs] = [];
            }
            acc[cur.runs].push(cur);
            return acc;
          }, {});
        }
        // changedData.value = groupedDataByRuns.value.Species;
        changeDataOnly();
        microbialShow.value = true;
        setTimeout(() => {
            myChart.hideLoading(); // 隐藏加载动画
            // 展示微生物峰度图
            showStackedBar();
        }, 500)
      }
    })
    .catch((err) => {
      console.log(err);
    });
}

// 切换数据
function changeDataOnly() {
  if (radio1.value == "1") {
    changedData.value = groupedDataByRuns.value?.Species;
    microbialNames.value = [
        ...new Set(stackedBarData.value?.Species.map((data) => data?.specificTaxonomy)),
      ];
  } else if (radio1.value == "2") {
    changedData.value = groupedDataByRuns.value?.Genus;
    microbialNames.value = [
        ...new Set(stackedBarData.value?.Genus.map((data) => data?.specificTaxonomy)),
      ];
  } else if (radio1.value == "3") {
    changedData.value = groupedDataByRuns.value?.Family;
    microbialNames.value = [
        ...new Set(stackedBarData.value.Family?.map((data) => data?.specificTaxonomy)),
      ];
  } else if (radio1.value == "4") {
    changedData.value = groupedDataByRuns.value.Order;
    microbialNames.value = [
        ...new Set(stackedBarData.value?.Order.map((data) => data?.specificTaxonomy)),
      ];
  } else if (radio1.value == "5") {
    changedData.value = groupedDataByRuns.value?.Class;
    microbialNames.value = [
        ...new Set(stackedBarData.value?.Class.map((data) => data?.specificTaxonomy)),
      ];
  } else if (radio1.value == "6") {
    changedData.value = groupedDataByRuns.value.Phylum;
    microbialNames.value = [
        ...new Set(stackedBarData.value.Phylum.map((data) => data?.specificTaxonomy)),
      ];
  }
}

function changeData() {
  changeDataOnly();
  const myChart = echarts.init(document.getElementById("stackedBar"));
  // 显示加载动画
  myChart.showLoading({   // 加载动画的文字
        color: '#007e37',       // 加载动画的圆环的颜色
        maskColor: 'rgba(255, 255, 255, 0.8)',  // 加载动画的背景颜色
  }); // 显示加载动画
  setTimeout(() => {
    myChart.hideLoading(); // 隐藏加载动画
    // 展示微生物峰度图
    showStackedBar();
  }, 500)
}

// 绘制微生物峰度图（堆叠条形柱状图）
function showStackedBar() {
    const echartDom = document.getElementById("stackedBar");
    echartDom?.removeAttribute("_echarts_instance_");
    const myChart = echarts.init(echartDom);
    myChart.clear();
    const option = {
        toolbox: {
          show: true,
          feature: {
              dataView: { readOnly: false },
              saveAsImage: {}
          }
        },
        tooltip: {
        trigger: "item",
        formatter: function (params) {
            return `
        runs: ${params.name}<br/>
        microbialName: ${params.seriesName}<br/>
        level: ${params.data.level}<br/>
        abundance: ${params.value}%`;
        },
        },
      grid: {
        width:'60%',
        top: 20,
        bottom: 90,
      },
        legend: {
          top: 10,
          type: "scroll",
          orient: "vertical",
          left:'70%',
          textStyle: {
            width:300,
            overflow: 'breakAll',
            fontStyle:'normal',
            // 文字靠左
            align: 'left',
          },
        },
        xAxis: [
        {
            type: "category",
            data: Object.keys(changedData.value),
            interval: 0,
            axisTick: {
            show: true,
            alignWithLabel: true,
            },
            axisLabel: {
            interval: 0,
            rotate: 90,
            fontSize: 10,
            },
            name: "SampleID",
            nameLocation: "center",
            nameGap: 70,
        },
        ],
        yAxis: {
        type: "value",
        min: 0,
        max: 100,
        axisLabel: {
            formatter: "{value}%",
        },
        name: "Relative abundance",
        nameLocation: "middle",
        nameGap: 40,
        splitNumber: 10,
        splitLine: {
            show: false,
        },
      },
        dataZoom:[
          {
            xAxisIndex: 0,
            show: true,
            type: 'slider',
            brushSelect: false,
            moveHandleSize: 0,
            startValue: 0,
            endValue: 19,
            bottom: 0,
            zoomLock: true,
            handleSize: 0,
            borderColor: '#1c683d',
            fillerColor: '#1c683d',
            height: 10,
            maxValueSpan: 19,
            minValueSpan: 19,
            throttle: 0,
          },
          {
            type: 'inside',
            xAxisIndex: 0,
            moveOnMouseMove: true,
          },
        ],
      series:
          microbialNames.value.map((microbialName) => ({
            name: microbialName, // 微生物名
            type: "bar",
            stack: "Microbial Abundance",
            emphasis: {
              focus: "series", 
            },
            data: Object.values(changedData.value).map((sampleData) => {
              const microbeData = sampleData.find(
                (data) => data.specificTaxonomy == microbialName
              );
              return microbeData
                ? { value: microbeData.abundance, level: microbeData.level }
                : { value: 0, level: "" };
            }),
          })),
          large: true,
      };
    myChart.setOption(option);
    myChart.resize();
}

const lefseData = ref([]);
const processLefseData = ref([]);
const groups = ref([]);
const treeData = ref([]);
const lefseShow = ref(true);

// 根据项目名称获取lefse数据
function LefseData() {
  const myChart = echarts.init(document.getElementById("negativeBar"));
  // 显示加载动画
    myChart.showLoading({   // 加载动画的文字
        color: '#007e37',       // 加载动画的圆环的颜色
        maskColor: 'rgba(255, 255, 255, 0.8)',  // 加载动画的背景颜色
    }); // 显示加载动画
  getNegativeBarData(testName2.value)
    .then((res) => {
      lefseData.value = res.data;
      lefseData.value = lefseData.value.map((item) => {
        if (item.group == null) {
          item.group = "NULL";
        }
        return item;
      });
      processLefseData.value = lefseData.value?.map((item) => {
        let nameSegments = item?.microbialName?.split(".");
        let name = nameSegments[nameSegments.length - 1];
        return {
          name: name,
          value: item.ldaScore,
          group: item.group,
        };
      });
      processLefseData.value.sort((a, b) => {
        if (a.group < b.group) {
          return -1;
        }
        if (a.group > b.group) {
          return 1;
        }
        return b.value - a.value;
      });
      groups.value = [
        ...new Set(processLefseData.value.map((item) => item.group)),
      ];
      lefseData.value.forEach((item) => {
        let microbialNames = ['root'];
        item.microbialName.split('.').forEach((name) => {
          microbialNames.push(name+'.'+item.group);
        });
        microbialNames.forEach((name, index) => {
          let id = index == 0 ? 'root' : name;
          // 检查id是否已经存在
          if (!treeData.value.some((node) => node.id == id)) {
            let node = {};
            node.id = id;
            node.parent = index == 0 ? "" : microbialNames[index - 1];
            node.group = index == 0 ? '' : item.group;
            // name的‘_'前面的部分为node.name
            let nameSegments = name.split('.');
            node.name = index == 0 ? 'root' : nameSegments[nameSegments.length - 2];
            treeData.value.push(node);
          }
        });
      });
      if(processLefseData.value.length == 0){
        lefseShow.value = false;
      }
      else{
        lefseShow.value = true;
        setTimeout(() => {
            myChart.hideLoading(); // 隐藏加载动画
            // 展示正负条形图
            showNegativeBar();
            // 展示径向树图
            showRadialTree();
        }, 500)
      }
    })
    .catch((err) => {
      console.log(err);
    });
}

// 正负条形图
function showNegativeBar() {
    const echartDom = document.getElementById("negativeBar");
    // 根据processLefseData.value的长度设置高度
    echartDom.style.height = processLefseData.value.length * 50 + "px";
    echartDom?.removeAttribute("_echarts_instance_");
    const myChart = echarts.init(echartDom);
    myChart.clear();
    const option = {
        toolbox: {
          show: true,
          feature: {
              dataView: { readOnly: false },
              saveAsImage: {}
          }
        },
        grid: {
          top: 100,
        },
        title: {
          text: "LDA SCORE(log10)",
          textAlign: "center",
          left: "50%",
        },
        tooltip: {
          trigger: "item",
          axisPointer: {
              type: "none",
          },
        },
      legend: {
          type: "scroll",
          top: 30,
          right: 0,
          data: groups.value,
        },
        xAxis: {
          position: "top",
          type: "value",
          splitLine: {
              lineStyle: {
                type: "dashed",
              },
          },
        },
        yAxis: {
          type: "category",
          axisLabel: { show: false },
          axisTick: {
              show: false,
          },
          data: processLefseData.value.map((item) => item.name),
        },
        series: groups.value.map((group) => {
        return {
            name: group,
            stack: 'stack',
            colorBy: "series",
            type: "bar",
            label: {
              show: true,
              position: 'left',
              formatter: function (params) {
                return params.value == 0 ? "" : params.name;
              },
              color: "#3c3c3c",
            },
            data: processLefseData.value.map((item) => {
              return item.group == group ? item.value : 0;
            }),
        };
        }),
    };
    myChart.setOption(option);
    myChart.resize();
}

const radialTree = ref(null);
// 径向树图
const spec = {
  $schema: "https://vega.github.io/schema/vega/v5.json",
  width: 920,
  height: 920,
  padding: 0,
  autosize: "none",

  signals: [
    {
      name: "labels",
      value: false,
    },
    {
      name: "radius",
      value: 280,
    },
    {
      name: "extent",
      value: 360,
    },
    {
      name: "rotate",
      value: 0,
    },
    {
      name: "layout",
      value: "cluster",
    },
    {
      name: "links",
      value: "orthogonal",
    },
    { name: "originX", update: "width / 2" },
    { name: "originY", update: "height / 2" },
  ],

  data: [
    {
      name: "tree",
      values: treeData.value,
      transform: [
        {
          type: "stratify",
          key: "id",
          parentKey: "parent",
        },
        {
          type: "tree",
          method: { signal: "layout" },
          size: [1, { signal: "radius" }],
          as: ["alpha", "radius", "depth", "children"],
        },
        {
          type: "formula",
          expr: "(rotate + extent * datum.alpha + 270) % 360",
          as: "angle",
        },
        {
          type: "formula",
          expr: "PI * datum.angle / 180",
          as: "radians",
        },
        {
          type: "formula",
          expr: "inrange(datum.angle, [90, 270])",
          as: "leftside",
        },
        {
          type: "formula",
          expr: "originX + datum.radius * cos(datum.radians)",
          as: "x",
        },
        {
          type: "formula",
          expr: "originY + datum.radius * sin(datum.radians)",
          as: "y",
        },
        {
          type: "formula",
          expr: "datum.children ? false : true",
          as: "isLeaf",
        },
      ],
    },
    {
      name: "links",
      source: "tree",
      transform: [
        { type: "treelinks" },
        {
          type: "linkpath",
          shape: { signal: "links" },
          orient: "radial",
          sourceX: "source.radians",
          sourceY: "source.radius",
          targetX: "target.radians",
          targetY: "target.radius",
        },
      ],
    },
  ],

  scales: [
    {
      name: "color",
      type: "ordinal",
      domain: { data: "tree", field: "group" },
      range: { scheme: "category10" },
    },
  ],
  legends: [
    {
      fill: "color",
      orient: "top-left",
      symbolType: "square",
      offset:160,
    },
  ],
  marks: [
    {
      type: "path",
      from: { data: "links" },
      encode: {
        update: {
          x: { signal: "originX", offset: 100 },
          y: { signal: "originY" },
          path: { field: "path" },
          stroke: { value: "#ccc" },
        },
      },
    },
    {
      type: "symbol",
      from: { data: "tree" },
      encode: {
        enter: {
          size: { value: 100 },
          stroke: { value: "#fff" },
        },
        update: {
          x: { field: "x", offset: 100 },
          y: { field: "y" },
          fill: [
            {
              test: 'datum.isLeaf && datum.group !== "root"',
              scale: 'color',
              field:'group',
            },
            {
              value: '#1f77b4',
            }
          ],
          tooltip: { signal: "{title: datum.name, 'Group': datum.group}" },
        },
      },
    },
    {
      type: "text",
      from: { data: "tree" },
      encode: {
        enter: {
          text: { field: "name" },
          fontSize: { value: 9 },
          baseline: { value: "middle" },
        },
        update: {
          x: { field: "x" },
          y: { field: "y" },
          dx: { signal: "(datum.leftside ? -1 : 1) * 6" },
          angle: { signal: "datum.leftside ? datum.angle - 180 : datum.angle" },
          align: { signal: "datum.leftside ? 'right' : 'left'" },
          opacity: { signal: "labels ? 1 : 0" },
        },
      },
    },
  ],
};

// 绘制树图
function showRadialTree() {
    const dom=document.querySelector('#radialTree')
    const width=dom.clientWidth
  if(treeData.value.length != 0){
    new View(parse(spec))
        .renderer("canvas") // 设置渲染器为canvas
        .initialize(radialTree.value) // 初始化Vega视图
        .width(width) // 设置视图的宽度
        .height(720) // 设置视图的高度
        .tooltip(new Handler().call) // 启用工具提示
        .run(); // 运行Vega视图
    }
}

// 处理热力图数据
const heatmapShow = ref(true)
const heatProject = ref();
const bioProject = ref([]);
const microbialName = ref([]);
const ldaScore = ref([]);
function handleHeatMapData() {
  const myChart = echarts.init(document.getElementById("heatmap"));
  // 显示加载动画
    myChart.showLoading({   // 加载动画的文字
        color: '#007e37',       // 加载动画的圆环的颜色
        maskColor: 'rgba(255, 255, 255, 0.8)',  // 加载动画的背景颜色
    }); // 显示加载动画
  getPhenotypeData(heatProject.value)
    .then((res) => {
      let tempbioProject = [];
      let tempmicrobialName = [];
      let templdaScore = [];
      res.data.forEach((item) => {
        if (!tempbioProject.includes(item.bioProject)) {
          tempbioProject.push(item.bioProject);
        }
        if (!tempmicrobialName.includes(item.microbialName)) {
          tempmicrobialName.push(item.microbialName);
        }
        templdaScore.push([tempmicrobialName.indexOf(item.microbialName), tempbioProject.indexOf(item.bioProject), item.ldaScore]);
      });
      bioProject.value = tempbioProject;
      microbialName.value = tempmicrobialName;
      ldaScore.value = templdaScore;
      // 如果这三个数据都为空，则不显示热力图
      if(bioProject.value.length == 0 || microbialName.value.length == 0 || ldaScore.value.length == 0){
        heatmapShow.value = false;
      }
      else{
        heatmapShow.value = true;
        setTimeout(() => {
            myChart.hideLoading(); // 隐藏加载动画
            // 展示热力图
            showHeatMap();
        }, 500)
      }
    })
    .catch((err) => {
      console.log(err);
    });
}

// 绘制热力图
function showHeatMap() {
  const echartDom = document.getElementById("heatmap");
  echartDom.style.width = microbialName.value.length * 23 + 140 + "px";
  echartDom?.removeAttribute("_echarts_instance_");
  const myChart = echarts.init(echartDom);
  const option = {
    toolbox: {
      left: 0,
      show: true,
      feature: {
          dataView: { readOnly: false },
          saveAsImage: {}
      }
    },
    visualMap: {
      type: "continuous",
      min: 4,
      max: 8,
      inRange: {
        color: [
          "#2e6e12",
          "#57c84d",
          "#83d475",
          "#c5e8b7",
          "#f6bdc0",
          "#f1959b",
          "#ea4c46",
          "#781426",
        ],
      },
      orient: "horizontal",
      itemHeight: 200,
      itemWidth: 15,
      padding: 0,
      dimension: 2,
      left: "5%",
      bottom: "25%",
    },
    legend: {},
    tooltip: {
      show: true,
      formatter: function (params) {
        let project = bioProject.value[params.data[1]];
        let microbialname = microbialName.value[params.data[0]];
        let LDA = params.data[2];
        microbialname = microbialname.split(".").join("<br>" + "&nbsp;".repeat(26) + ".");
        return (
          "bioProject: " +
          project +
          "<br>" +
          "microbialName: " +
          microbialname +
          "<br>" +
          "ldaScore: " +
          LDA
        );
      },
      textStyle: {
        align: "left",
      },
      confine: true,
    },
    grid: {
      // left: 138,
      height: bioProject.value.length * 17.5,
    },
    xAxis: {
      type: "category",
      data: microbialName.value.map((item) => item.split(".").pop()),
      axisLabel: {
        interval: 0,
        rotate: 45,
      },
      axisLine: {
        show: false,
      },
      axisTick: {
        alignWithLabel: true,
      },
      scale: true,
    },
    yAxis: {
      type: "category",
      data: bioProject.value,
      inverse: true,
      axisLabels: {
        interval: 0,
      },
      axisLine: {
        show: false,
      },
      axisTick: {
        alignWithLabel: true,
      },
    },
    series: [
      {
        type: "custom",
        renderItem: function (params, api) {
          var xValue = api.value(0);
          var yValue = api.value(1);
          var point = api.coord([xValue, yValue]);

          return {
            type: "rect",
            shape: {
              x: point[0] - 7.0,
              y: point[1] - 7.6,
              width: 15,
              height: 15,
              r: 3.75,
            },
            style: api.style({}),
          };
        },
        data: ldaScore.value,
      },
    ],
  };
  myChart.setOption(option);
}

// 处理嵌套对象中的null和数字
const handleNumber = (obj) => {
  for (let key in obj) {
    if (obj[key] == null) {
      obj[key] = "";
    } else if (typeof obj[key] == "object") {
      handleNumber(obj[key]);
    }
    if(typeof(obj[key])=='number' && obj[key]){
        obj[key]=obj[key].toString()
    }
  }
};

// 微生物分组可视化
const statisticsShow = ref(true);
const groupsData = ref([]);
const radio2 = ref("1");
const changedData2 = ref([]);
const getStatisticalData = () => {
  const myChart = echarts.init(document.getElementById("groupShow"));
  // 显示加载动画
  myChart.showLoading({
    // 加载动画的文字:请等待1-2分钟
    text: "Please wait 1-2 minutes",
    color: '#007e37',
    maskColor: 'rgba(255, 255, 255, 0.8)',
  })
  getCountgroupData(testName.value).then(res => {
    if (res.data.length == 0) {
      statisticsShow.value = false;
    } else {
      statisticsShow.value = true;
      groupsData.value = res.data;
      console.log('1111',res.data)
      // 将获取的数据先按照level(Genus、Phylum)进行分组
      groupsData.value = groupsData.value.reduce((acc, cur) => {
        if (!acc[cur?.level]) {
          acc[cur?.level] = [];
        }
        acc[cur?.level].push(cur);
        return acc;
      }, {});
      console.log('1111',groupsData.value)
      changeGroupDataOnly();
      setTimeout(() => {
        myChart.hideLoading(); // 隐藏加载动画
        // 展示分组统计图
        showGroupChart();
      }, 500)
    }
  }).catch(err => {
    console.log(err)
  })
}

// 切换数据
function changeGroupDataOnly() {
  if (radio2.value == '1') {
    changedData2.value = groupsData.value?.Genus;
      console.log('11111',changedData2.value)
  } else if (radio2.value == '2') {
    changedData2.value = groupsData.value?.Phylum;
  }
}

function changeGroupData() {
  changeGroupDataOnly();
  const myChart = echarts.init(document.getElementById("groupShow"));
  // 显示加载动画
  myChart.showLoading({   // 加载动画的文字
    color: '#007e37',       // 加载动画的圆环的颜色
    maskColor: 'rgba(255, 255, 255, 0.8)',  // 加载动画的背景颜色
  }); // 显示加载动画
  setTimeout(() => {
    myChart.hideLoading(); // 隐藏加载动画
    // 展示分组统计图
    showGroupChart();
  }, 500)
}

const showGroupChart = () => {
  const echartDom = document.getElementById('groupShow');
  echartDom?.removeAttribute('_echarts_instance_');
  const myChart = echarts.init(echartDom);

  // 将数据按照subGroup进行分组
  const groupedData = changedData2.value.reduce((acc, cur) => {
      const { subGroup, specific_taxonomy, value } = cur;
      if (!acc[subGroup]) {
          acc[subGroup] = { totalValue:0 };
      }
      acc[subGroup][specific_taxonomy] =
          (acc[subGroup][specific_taxonomy] || 0) + value;
      acc[subGroup].totalValue += value;
      return acc;
  }, {});

  const microbialNames = [
      ...new Set(changedData2.value.map((item) => item.specific_taxonomy)),
  ];

  const option = {
    toolbox: {
      show: true,
      feature: {
          dataView: { readOnly: false },
          saveAsImage: {}
      }
    },
    tooltip: {
        trigger: "item",
        formatter: function (params) {
            return `
        subGroup: ${params.name}<br/>
        microbialName:${params.seriesName}<br/>
        level:${params.data.level}<br/>
        abundance: ${(params.value).toFixed(2)}%`;
        },
    },
    legend: {
        top: 10,
        type: "scroll",
        orient: "vertical",
        left: "70%",
        textStyle: {
            width: 300,
            overflow: "breakAll",
            fontStyle: "normal",
            // 文字靠左
            align: "left",
        },
    },
    grid: {
        width: "60%",
        bottom: 90,
        top: 20,
    },
    xAxis: [
        {
            type: "category",
            data: Object.keys(groupedData),
            interval: 0,
            axisTick: {
                show: true,
                alignWithLabel: true,
            },
            axisLabel: {
                interval: 0,
                fontSize: 12,
            },
            name: "Sub_Group",
            nameLocation: "center",
            nameGap: 40,
        },
    ],
    yAxis: {
        type: "value",
        min: 0,
        max: 100,
        axisLabel: {
            formatter: "{value}%",
        },
        name: "Abundance",
        nameLocation: "middle",
        nameGap: 40,
        splitNumber: 10,
        splitLine: {
            show: false,
        },
    },
    dataZoom: [
        {
            xAxisIndex: 0,
            show: true,
            type: "slider",
            brushSelect: false,
            moveHandleSize: 0,
            startValue: 0,
            endValue: 19,
            bottom: 0,
            zoomLock: true,
            handleSize: 0,
            borderColor: "#1c683d",
            fillerColor: "#1c683d",
            height: 10,
            maxValueSpan: 19,
            minValueSpan: 19,
            throttle: 0,
        },
        {
            type: "inside",
            xAxisIndex: 0,
            moveOnMouseMove: true,
        },
    ],
    series: microbialNames.map((microbialName) => ({
        name: microbialName, // 微生物名
        type: "bar",
        stack: "Microbial Abundance",
        emphasis: {
            focus: "series",
        },
        data: Object.entries(groupedData).map(([subGroup, data]) => {
            const microbeData = data[microbialName];
            return microbeData
                ? { value: microbeData * (100/data.totalValue), level: "" }
                : { value: 0, level: "" };
        }),
    })),
    large: true,
  };
  myChart.setOption(option);
}


// 监视choosingProject变化
watch(()=>props.choosingProject, (newVal) => {
  // 重新挂载siderNav
  reloadSideNav.value++
  if (JSON.stringify(newVal) == "{}") return;

  testName.value = newVal.project;
  testName2.value = newVal.project;
  heatProject.value = newVal.project;
  // 查询营养成分
  getNutritionData(newVal.project)
    .then(res=>{
        if(res?.code != 200) return ElMessage.error('search error!')
        nutritionData.value=res.data
        handleNumber(nutritionData.value)
        console.log(nutritionData.value)
    }).catch(err=>{
        console.error('request error!',err)
    })

  setTimeout(() => {
    getStatisticalData();
    microbialData();
    LefseData();
    handleHeatMapData();
  }, 500);
}, { immediate: true, deep: true });

</script>

<style scoped lang="less">
.overViewBox {
  margin-top: 50px;

  .headerTitle {
    width: 100%;
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: rgba(0, 0, 0, 0.8);
    a{
        color: rgba(0, 0, 0, 0.8);
        text-decoration: none;
    }
  }
  .download-link{
    color:rgba(0, 0, 0, 0.6);
    text-decoration:underline;
    cursor: pointer;
  }
}

.nutrientBox{
    margin: 50px 0 50px 0;
    .headerTitle {
    width: 100%;
    text-align: center;
    margin-bottom: 50px;
    font-size: 24px;
    font-weight: 600;
    color: rgba(0, 0, 0, 0.8);
  };
  .nutrientBox_note{
    font-size: 14px;
  }
  .description{
    font-size: 13px;
  }
}

.projectsBox {
  width: 80%;
  margin-left: 15%;
  .MicrobialBox{
    display: flex;
    flex-direction: column;
    align-items: center;
    .radio{
      margin-top: 50px;
      display: flex;
      align-items: center;
    }
  }
  .groupBox{
    display: flex;
    flex-direction: column;
    align-items: center;
    .radio{
      margin-top: 50px;
      display: flex;
      align-items: center;
    }
  }
  .heatmap{
    overflow: scroll;
    overflow-y: hidden;
    scrollbar-color: #1c683d #fff;
    margin-left: 10%;
    // scrollbar-width: thin;
  }
}

.overViewMain {
  width: 100%;
  margin-top: 30px;
  padding: 20px 30px;
  background-color: #f7f7f7;
  border-radius: 50px;

  .item {
    letter-spacing: 0;
    line-height: 25px;

    .title {
      font-size: 16px;

      font-weight: 600;
      color: rgba(0, 0, 0, 0.8);
    }
  }
}

.nutrientMain{
  margin-top: 30px;
}

</style>