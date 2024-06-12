<template>
    <div class="outBox">
        <sider-nav :navList="navList"></sider-nav>
        <div class="mainBox">
            <el-radio-group v-model="radio1">
                <el-radio @change="getChartsData" label="1" size="large">Summary </el-radio>
                <el-radio @change="getMapData" label="2" size="large">Map</el-radio>
            </el-radio-group>
            <div class="chartsBox" v-show="radio1 == 1">
                <div id="category" style="width:50%;height:50%"></div>
                <div id="country" style="width:50%;height:50%"></div>
                <div id="isolation" style="width:50%;height:50%"></div>
                <div id="level" style="width:50%;height:50%"></div>
            </div>
            <div class="chartsBox" v-show="radio1 == 2">
                <div id="worldMap" style="width: 100%;height: 100%"></div>
            </div>
            <div class="uploadBox">
                <div class="title">Upload</div>
                <div class="leftBox">
                    <el-form class="infoBox" :model="form" :rules="rules" label-position="left" label-width="140px">
                        <el-form-item label="Email" prop="email">
                            <el-input v-model="form.email" placeholder="Enter your email"></el-input>
                        </el-form-item>
                        <el-form-item label="Type" prop="type">
                            <el-radio-group v-model="form.type">
                                <el-radio :label="0">16s amplicon</el-radio>
                                <el-radio :label="1">Metagenomics</el-radio>
                                <el-radio :label="2">ITS</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item label="Paper Title" prop="paperTitle">
                            <el-input v-model="form.paperTitle" placeholder="Enter your paper title"></el-input>
                        </el-form-item>
                        <el-form-item label="Project ID" prop="projectId">
                            <el-input v-model="form.projectId" placeholder="Enter your project id"></el-input>
                        </el-form-item>
                        <el-form-item label="Introduction" prop="introduction">
                            <el-input v-model="form.introduction" placeholder="Enter your introduction"></el-input>
                        </el-form-item>
                        <el-form-item label="Analysis Result" prop="analysisResult">
                            <el-radio-group v-model="form.analysisResult">
                                <el-radio :label="0">Taxonomy classification</el-radio>
                                <el-radio :label="1">LEfSe</el-radio>
                                <el-radio :label="2">Picrust2</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-form>
                    <div class="uploadFileBox">
                        <div class="uploadItem" v-for="(item, index) in fileList" :key="index">
                            <el-upload :file-list="item.file" :limit="1" accept=".xlsx,.xls,.csv,.zip"
                                action="http://localhost:3000" :http-request="uploadFile">
                                <el-button @click="checkIsLogin" style="width: 120px;">

                                    <el-icon class="el-icon-left">
                                        <Link />
                                    </el-icon>
                                    <span v-if="!isWrap(item.name)">
                                        {{ item.name }}
                                    </span>
                                    <span v-else style="font-size: 12px;">
                                        {{ item.name.slice(0, 8) }}<br>
                                        {{ item.name.slice(8) }}
                                    </span>
                                </el-button>
                            </el-upload>
                            <el-button icon="UploadFilled" @click="downloadTemplateFile(item.templateFileName)">example</el-button>
                        </div>
                    </div>
                </div>
                <div class="rightBox">
                    <div class="title">Feedback</div>
                    <el-input type="textarea" 
                    placeholder="If you have some brilliant proposals, you can submit them here or contact us via the e-mail address on the 'Help' page." 
                    v-model="form.feedBack"
                        :rows="20">
                    </el-input>
                </div>
                <div class="submit-buttons">
                    <el-button class="submit" type="primary" @click="submitForm">Submit</el-button>
                    <el-button class="clear" type="info" plain @click="clearFileInput">clear</el-button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { useLoginDialogStore } from '../../stores/modules/login';
import * as echarts from 'echarts'
import {
    countCategory,
    countCountry,
    countLevel,
    countIsolation,
    countRuns,
    uploadProjectFileAPI,
    downloadTemplateFileAPI
} from '../../api/Data'
import worldData from '../../data/world.json'
import cityData from '../../data/cities.geojson.json'

//路由实例
const router = useRouter()

//登录弹窗实例
const loginDialog = useLoginDialogStore();

//控制按钮标签换行
const isWrap = (label) => {
    return label.length > 8 ? true : false
}
//上传实例
const upload = ref(null)

//自定义上传文件
const uploadFile = (file) => {
    console.log(file)
}

//上传信息表单
const form = ref({
    email: '',
    type: '',
    paperTitle: '',
    projectId: '',
    introduction: '',
    analysisResult: '',
    feedBack: ''
})

//表单验证规则
const rules = ref({
    email: [
        { required: false, message: 'Please enter your email', trigger: 'blur' },
        { type: 'email', message: 'Please enter the correct email address', trigger: ['blur', 'change'] }
    ],
    type: [
        { required: false, message: 'Please select the type', trigger: 'change' }
    ],
    paperTitle: [
        { required: false, message: 'Please enter the paper title', trigger: 'blur' }
    ],
    projectId: [
        { required: false, message: 'Please enter the project ID', trigger: 'blur' }
    ],
    introduction: [
        { required: false, message: 'Please enter the introduction', trigger: 'blur' }
    ],
    analysisResult: [
        { required: false, message: 'Please select the analysis result', trigger: 'change' }
    ],
    feedBack: [
        { required: false, message: 'Please enter your feedback', trigger: 'blur' }
    ],
})


const fileList = ref([
    {
        templateFileName: 'metaDataFile',
        file: [],
        name: 'MetaData',
    },
    {
        templateFileName: 'classificationFile',
        file: [],
        name: 'TaxonomyClassification',
    },
    {
        templateFileName: 'lefseFile',
        file: [],
        name: 'LEfSe',
    },
    {
        templateFileName: 'picrust2File',
        file: [],
        name: 'Picrust2',
    }
])

//检查是否登录
const checkIsLogin = () => {
    //已登录
    if (loginDialog.isLogin) return
    //未登录
    ElMessage.warning('please login first')
    //打开登录弹窗
    loginDialog.openLoginDialog()
}

const test = () => loginDialog.isLogin

//禁止文件上传
const disableFileInput = () => {
    const fileInputArr = document.querySelectorAll('input[type=file]')
    fileInputArr?.forEach((item) => {
        item.disabled = !loginDialog.isLogin
    })
}

// 清空文件上传表单
const clearFileInput = () => {
    fileList.value.forEach((item) => {
        item.file = []
    })
    form.value = {
        email: '',
        type: undefined,
        paperTitle: '',
        projectId: '',
        introduction: '',
        analysisResult: undefined,
        feedBack: ''
    }
}

// 文件名与接口字段名的映射
const fileMap = {
    metaData: 'metaDataFile',
    TaxonomyClassification: 'classificationFile',
    LEfSe: 'lefseFile',
    Picrust2: 'picrust2File',
}

// 提交表单
const submitForm = () => {
    // 检查文件是否为空
    let emptyFileNum = 0
    fileList.value.forEach((item) => {
        if (item.file.length === 0) {
            emptyFileNum++
        }
    })
    if (emptyFileNum === 4) {
        ElMessage.warning('Please upload at least one file')
        return
    }
    // 创建表单对象
    const formData = new FormData()
    // 将表单数据添加到formData中
    for (let key in form.value) {
        formData.append(key, form.value[key])
    }
    // 将文件添加到formData中
    fileList.value.forEach((item) => {
        if (item.file.length !== 0) {
            formData.append(fileMap[item.name], item.file[0].raw)
        }
    })   
    // 上传文件
    ElMessage.info('Uploading,please wait a moment...')
    uploadProjectFileAPI(formData)
        // 提示需要等待一会
        .then((res) => {
            console.log(res)
            ElMessage.success('Upload successfully')
            clearFileInput()
        })
        .catch((err) => {
            console.log(err)
            ElMessage.error('Upload failed')
        })
}

// 下载模板文件
const downloadTemplateFile = (name) => {
    ElMessage.info('Downloading...')
    downloadTemplateFileAPI(name)
        .then((res) => {
            const blob = new Blob([res], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
            const url = window.URL.createObjectURL(blob)
            const a = document.createElement('a')
            document.body.appendChild(a);
            a.href = url
            if(name === 'metaDataFile') {
                a.download = `${name}.xlsx`
            }else if(name==='classificationFile'){
                a.download = `${name}.csv`
            }else{
                a.download = `${name}.zip`
            }
            a.click()
            window.URL.revokeObjectURL(url)
            document.body.removeChild(a)
            ElMessage.success('Download successfully')
        })
        .catch((err) => {
            console.error(err)
            ElMessage.error('Download failed')
        })
}


watch(test, disableFileInput)

onMounted(() => {
    disableFileInput()

    // 统计Category
    getCategory()
    // 统计Country
    getCountry()
    // 统计Isolation
    getIsolation()
    // 统计Level
    getLevel()
})

function getChartsData() {
    // 统计Category
    getCategory()
    // 统计Country
    getCountry()
    // 统计Isolation
    getIsolation()
    // 统计Level
    getLevel()
}

//侧边导航栏
const navList = ref([
    {
        name: 'Statistics',
        location: '.chartsBox',
    },
    {
        name: 'Upload',
        location: '.uploadBox',
    }
])

const countCategoryData = ref([])
const countCountryData = ref([])
const countIsolationData = ref([])
const levelCounts = ref([])
const levels = ref([])

// 统计Category
function getCategory() {
    const myChart = echarts.init(document.getElementById("category"));
    // 显示加载动画
    myChart.showLoading({   // 加载动画的文字
        color: '#007e37',       // 加载动画的圆环的颜色
        maskColor: 'rgba(247, 247, 247, 0.8)',  // 加载动画的背景颜色
    }); // 显示加载动画
    countCategory()
        .then((res) => {
            countCategoryData.value = res.data
                .filter(item => item.category !== 'NA')
                .map(item => ({
                    value: item.countProject,
                    name: item.category,
            }));
            setTimeout(() => {
                myChart.hideLoading();
                Category();
            }, 500);
        })
        .catch((err) => {
            console.log(err)
        })
}
// Category图表
function Category() {
    const echartDom = document.getElementById("category");
    echartDom?.removeAttribute("_echarts_instance_");
    const myChart = echarts.init(echartDom);

    // 定义排序顺序
    const order = ["Boar", "Sow", "Piglets", "Growing-finishing Pigs"];
    countCategoryData.value.sort((a, b) => order.indexOf(a.name) - order.indexOf(b.name));

    const option = {
        toolbox: {
        show: true,
        feature: {
            dataView: { readOnly: false },
            saveAsImage: {}
        }
        },
        title: {
            text: 'Distribution of samples classified\n according to Boars, sows, Piglets and Growing fattening pigs.',
            left: 'center',
            textStyle: {
                fontSize: 12,
            },
            bottom: 0,
        },
        tooltip: {
            trigger: 'item',
        },
        color:{
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [{
                    offset: 0, color:'#6ED2CA' // 0% 处的颜色
                }, {
                    offset: 1, color: '#560980' // 100% 处的颜色
                }],
                global: false // 缺省为 false
            },
        xAxis: {
            data: countCategoryData.value.map(item => item.name),
            axisLabel: {
                show:true,
                interval: 0,
            }
        },
        yAxis: {},
        series: {
            label: {
                show: true,
            },
            type: 'bar',
            data: countCategoryData.value.map(item => item.value),
            itemStyle: {
                color: function (params) {
                    const colorList = ['#ee6666', '#5470c6', '#91cc75', '#fac858'];
                    return colorList[params.dataIndex];
                }
            }
        },
        emphasis: {
            itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    };
    myChart.setOption(option);
}

// 统计Country
function getCountry() {
    const myChart = echarts.init(document.getElementById("country"));
    // 显示加载动画
    myChart.showLoading({   // 加载动画的文字
        color: '#007e37',       // 加载动画的圆环的颜色
        maskColor: 'rgba(247, 247, 247, 0.8)',  // 加载动画的背景颜色
    }); // 显示加载动画
    countCountry()
        .then((res) => {
            countCountryData.value = res.data.map(item => ({
                value: item.countProject,
                name: item.country,
            }));
            setTimeout(() => {
                myChart.hideLoading();
                Country();
            }, 500);
        })
        .catch((err) => {
            console.log(err)
        })
}
// Country图表
function Country() {
    const echartDom = document.getElementById("country");
    echartDom?.removeAttribute("_echarts_instance_");
    const myChart = echarts.init(echartDom);
    const option = {
        toolbox: {
        show: true,
        feature: {
            dataView: { readOnly: false },
            saveAsImage: {}
        }
        },
        title: {
            text: 'Distribution of projects categorized by geographic location.',
            left: 'center',
            textStyle: {
                fontSize: 12,
            },
            bottom: 0,
        },
        tooltip: {
            trigger: 'item',
        },
        legend: {
            type: 'scroll',
            orient: 'vertical',
            left: '0%',
            height: '90%',
            pageIconSize: 10,
        },
        series: {
            label: {
                show: false,
            },
            type: 'pie',
            radius: '70%',
            data: countCountryData.value,
        },
        emphasis: {
            itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    };
    myChart.setOption(option);
}

// 统计Isolation
function getIsolation() {
    const myChart = echarts.init(document.getElementById("isolation"));
    // 显示加载动画
    myChart.showLoading({   // 加载动画的文字
        color: '#007e37',       // 加载动画的圆环的颜色
        maskColor: 'rgba(247, 247, 247, 0.8)',  // 加载动画的背景颜色
    }); // 显示加载动画
    countIsolation()
        .then((res) => {
            countIsolationData.value = res.data
                .filter(item => item.isolation !== 'NA')
                .map(item => ({
                    value: item.countProject,
                    name: item.isolation,
            }));
            setTimeout(() => {
                myChart.hideLoading();
                Isolation();
            }, 500);
        })
        .catch((err) => {
            console.log(err)
        })
}
// Isolation图表
function Isolation() {
    const echartDom = document.getElementById("isolation");
    echartDom?.removeAttribute("_echarts_instance_");
    const myChart = echarts.init(echartDom);
    const option = {
        toolbox: {
        show: true,
        feature: {
            dataView: { readOnly: false },
            saveAsImage: {}
        }
        },
        title: {
            text: 'Distribution of projects categorized according to sampling location.',
            left: 'center',
            textStyle: {
                fontSize: 12,
            },
            bottom: 0,
        },
        tooltip: {
            trigger: 'item',
        },
        legend: {
            type: 'scroll',
            orient: 'vertical',
            left: '0%',
            height: '90%',
            pageIconSize: 10,
        },
        series: {
            label: {
                show: false,
            },
            type: 'pie',
            radius: '70%',
            data: countIsolationData.value,
        },
        emphasis: {
            itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    };
    myChart.setOption(option);
}

// 统计Level
function getLevel() {
    const myChart = echarts.init(document.getElementById("level"));
    // 显示加载动画
    myChart.showLoading({   // 加载动画的文字
        color: '#007e37',       // 加载动画的圆环的颜色
        maskColor: 'rgba(247, 247, 247, 0.8)',  // 加载动画的背景颜色
    }); // 显示加载动画
    countLevel()
        .then((res) => {
            levelCounts.value = res.data.map(item => item.count);
            levels.value = res.data.map(item => item.level);
            // 定义期望的顺序
            const order = ["Species", "Genus", "Family", "Order", "Class", "Phylum"];
            // 对levels.value和levelCounts.value进行排序
            let sortedLevels = [], sortedCounts = [];
            for (let i = 0; i < order.length; i++) {
                let index = levels.value.indexOf(order[i]);
                if (index !== -1) {
                    sortedLevels.push(levels.value[index]);
                    sortedCounts.push(levelCounts.value[index]);
                }
            }
            // 使用排序后的数据绘制图表
            levels.value = sortedLevels;
            levelCounts.value = sortedCounts;
            setTimeout(() => {
                myChart.hideLoading();
                Level();
            }, 500);
        })
        .catch((err) => {
            console.log(err)
        })
}
// Level图表
function Level() {
    const echartDom = document.getElementById("level");
    echartDom?.removeAttribute("_echarts_instance_");
    const myChart = echarts.init(echartDom);
    const option = {
        toolbox: {
        show: true,
        feature: {
            dataView: { readOnly: false },
            saveAsImage: {}
        }
        },
        title: {
            text: 'Microbiological statistics for six levels',
            left: 'center',
            textStyle: {
                fontSize: 12,
            },
            bottom: 0,
        },
        tooltip: {
            trigger: 'item',
        },

        color:{
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [{
                    offset: 0, color:'#fac858' // 0% 处的颜色
                }, {
                    offset: 1, color: '#73c0de' // 100% 处的颜色
                }],
                global: false // 缺省为 false
            },
        xAxis: {
            data: levels.value,
        },
        yAxis: {},
        series: {
            label: {
                show: true,
            },
            type: 'bar',
            data: levelCounts.value,
            itemStyle: {
                color: function (params) {
                    const colorList = ['#ee6666', '#5470c6', '#91cc75', '#fac858','#73c0de','#3ba272'];
                    return colorList[params.dataIndex];
                }
            }
        },
        emphasis: {
            itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    };
    myChart.setOption(option);
}

// 单选框
const radio1 = ref('1');

// 统计runs（世界地图数据）
echarts.registerMap("world", worldData);
const runs = ref([]);

function getMapData() {
    const myChart = echarts.init(document.getElementById("worldMap"));
    // 显示加载动画
    myChart.showLoading({   // 加载动画的文字
        color: '#007e37',       // 加载动画的圆环的颜色
        maskColor: 'rgba(247, 247, 247, 0.8)',  // 加载动画的背景颜色
    }); // 显示加载动画
    countRuns()
        .then((res) => {
            runs.value = res.data;
            setTimeout(() => {
                myChart.hideLoading();
                showWorldMap();
            }, 500);
        })
        .catch((err) => {
            console.log(err);
        });
}

// 处理数据
const cities = ref([]);
const convertMyData = function (data) {
    let res = [];
    for (let i = 0; i < data.value.length; i++) {
        let feature = worldData.features.find(
            (f) => f.properties.name === data.value[i].country
        );
        if (feature) {
            res.push({
                name: data.value[i].country,
                value: [
                    data.value[i].country,
                    data.value[i].countProject,
                    data.value[i].countRuns,
                ],
            });
        } else {
            cities.value.push(data.value[i]);
        }
    }
    convertCityData(cities);
    return res;
};

// 处理城市数据：查询cityData中是否有这个城市，如果有，将城市的经纬度与城市的数据连接，用于在世界地图中绘制散点图
const convertCityData = function (data) {
    let res = [];
    for (let i = 0; i < data.value.length; i++) {
        let feature = cityData.features.find((f) => f.id === data.value[i].country);
        if (feature) {
            res.push({
                name: data.value[i].country,
                value: [
                    feature.geometry.coordinates[0],
                    feature.geometry.coordinates[1],
                    data.value[i].countProject,
                    data.value[i].countRuns,
                ],
            });
        }
    }
    return res;
};

// 世界地图
function showWorldMap() {
    const echartDom = document.getElementById("worldMap");
    echartDom?.removeAttribute("_echarts_instance_");
    const myChart = echarts.init(echartDom);
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
                if (params.data) {
                    // 如果params.data.value[0]不是数字，说明是国家，否则是城市
                    if (isNaN(params.data.value[0])) {
                        return `Location:${params.data.value[0]}</br>Projects:${params.data.value[1]}</br>Runs:${params.data.value[2]}`;
                    } else {
                        return `Location:${params.data.name}</br>Projects:${params.data.value[2]}</br>Runs:${params.data.value[3]}`;
                    }
                }
                return undefined;
            },
            textStyle: {
                align: "left",
            },
        },
        visualMap: {
            min: 0,
            max: Math.max(
                ...runs.value.map((item) => item.countProject + item.countRuns)
            ),
            left: "left",
            top: "bottom",
            inRange: {
                color: ["#f0f9eb", "#95d475"], // 设置颜色范围，从浅绿色到深绿色
            },
            calculable: false,
            show: false,
            seriesIndex: 0,
        },
        geo: {
            map: "world",
            roam: true,
            emphasis: {
                label: {
                    show: false,
                },
                itemStyle: {
                    areaColor: "#529b2e",
                },
            },
        },
        series: [
            {
                name: "World Map",
                type: "map",
                map: "world",
                roam: true,
                geoIndex: 0,
                data: convertMyData(runs),
                itemStyle: {},
                emphasis: {
                    itemStyle: {
                        areaColor: "#529b2e",
                    },
                    label: {
                        show: false,
                    },
                },
                selectedMode: false,
            },
            {
                name: "City",
                type: "scatter",
                coordinateSystem: "geo",
                data: convertCityData(cities),
                symbolSize: 10,
                label: {
                    show: false,
                },
                itemStyle: {
                    color: "#008080",
                },
            },
        ],
    };
    myChart.setOption(option);
}




</script>

<style scoped lang="less">
.outBox {
    width: 100%;
}

.mainBox {
    width: 80%;
    margin-left: 15%;
    margin-right: 5%;
}

.chartsBox {
    width: 100%;
    padding: 20px;
    background-color: #f7f7f7;
    border-radius: 50px;
    height: 600px;
    margin-bottom: 50px;
    display: flex;
    flex-wrap: wrap;

    #worldMap {
        border-top: 1px solid #ccc;
        border-bottom: 1px solid #ccc;
    }
}

.uploadBox {
    .title {
        width: 100%;
        font-size: 20px;
        margin-bottom: 40px;
        font-weight: 600;
        color: #333;
    }

    .el-button {
        position: relative;
    }

    .el-icon-left {
        position: absolute;
        left: 10px;
    }

    .uploadItem {
        display: flex;
        align-items: center;
        margin-bottom: 10px;

    }

    :deep(.el-upload-list) {
        width: 160px;
    }

    .uploadItem>:first-child {
        display: flex;
        height: 40px;
    }
}

.uploadBox {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;

    .leftBox {
        width: 50%;
        box-sizing: border-box;
    }

    .rightBox {
        width: 40%;
        box-sizing: border-box;

        .title {
            margin-bottom: 20px;
            font-size: 16px;
            font-weight: 400;
        }
    }

    .submit-buttons {
        width: 100%;
        display: flex;
        margin-top: 20px;

        .submit {
            width: 100px;
            margin-right: 170px;
        }

        .clear {
            width: 100px;
        }
    }
}
</style>../../stores/modules/login