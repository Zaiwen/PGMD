<template>
    <div class="outBox">
        <div class="welcomeBox">
            <div class="title">Welcome to PGMD</div>
            <div class="main">
                <p class="para">Pig gut microbiome plays an important role in pig health, especially in precision pig breeding. 
                    The pig gut microbiota database (PGMD) is a database that reveals the relationship between microbiota 
                    and phenotypes through the composition of microbiota. We collected 
                    <span>{{ statisticsData[0]?.runs }}</span>
                    raw sequencing samples of pig gut microbes from <span>{{ statisticsData[0]?.projects }}</span>  public projects 
                    in <span>{{ statisticsData[0]?.literatures }}</span> papers, 
                    and 21 types of metadata information, including sampling location, breed, country, sex, growth stage, weaned age, 
                    experimental design and so on.

                </p>
                <p class="para">
                    PGMD allows users to browse, search, and download pig gut microbiota composition  data associated with some specific phenotypes. 
                    For selected samples, the users can view the microbial species composition and corresponding metadata information of the samples. 
                    For the selected projects, the users can view the microbial species composition and metadata of all the samples in the project, 
                    know the average composition of gut microbiota in each group, identify the differential microbiota among these groups, 
                    and know the differential microbiota across all projects relevant to this research topic.
                    The information within the PGMD will provide robust data support for the exploration of functional gut microbiota.
                </p>
                <p class="para">
                    See the help page for details on how to use it.<br>
                    See our paper for a detailed analysis method.
                </p>
            </div>
        </div>
        <div class="bottomBox">
        <div class="statisticsBox">
            <div class="titleBox">Statistics</div>
            <div class="statisticsItem">
                <div class="image-container statistics-img"></div>
                <div class="text">
                    <p>Literatures</p>
                    <p>{{ statisticsData[0]?.literatures }}</p>
                </div>
            </div>
            <div class="statisticsItem">
                <div class="image-container projects-img"></div>
                <div class="text">
                    <p>Projects</p>
                    <p>{{ statisticsData[0]?.projects }}</p>
                </div>
            </div>
            <div class="statisticsItem">
                <div class="image-container runs-img"></div>
                <div class="text">
                    <p>Runs</p>
                    <p>{{ statisticsData[0]?.runs }}</p>
                </div>
            </div>
            <div class="statisticsItem">
                <div class="image-container sequences-img"></div>
                <div class="text">
                    <p>Sequences</p>
                    <p>678.9G</p>
                </div>
            </div>
            <div class="statisticsItem">
                <div class="image-container microbiome-img"></div>
                <div class="text">
                    <p>Microbiome</p>
                    <p>{{ statisticsData[0]?.microbiome }}</p>
                </div>
            </div>
            <div class="statisticsItem">
                <div class="image-container phenotype-img"></div>
                <div class="text">
                    <p>Phenotype</p>
                    <p>27</p>
                </div>
            </div>
            <div class="collectedDate">The data was collected before August 23, 2023.</div>
        </div>
        <!-- latest-news -->
        <div class="latestNewsBox">
            <div class="titleBox">Latest-News</div>
            <el-timeline>
                <el-timeline-item 
                    v-for="(item, index) in news" 
                    :key="index" 
                    :timestamp="item.date" 
                    placement="top"
                    type="primary" :hollow="true">
                    {{ item.content }}
                </el-timeline-item>
            </el-timeline>
        </div>
    </div>
</div>
</template>

<script setup>
import {ref,onMounted} from 'vue'
import {getStatisticsAPI , getNewsAPI} from '../../api/Home/index.js'

const statisticsData=ref([])

// 获取statistics数据
const getStatistics=()=>{
    getStatisticsAPI()
    .then(res=>{
        statisticsData.value=res.data
    })
}

const news = ref([])

// 获取news数据
const getNews=()=>{
    getNewsAPI()
    .then(res=>{
        news.value=res.data.notices.map(item=>({
            date:item.startTime,
            content:item.content
        }))
        console.log(news.value);
    })
}

onMounted(()=>{
    // 获取statistics数据
    getStatistics()
    // 获取news数据
    getNews()
})
//时间线数据

</script>
<style scoped lang="less">
.bottomBox{
    display: flex;
    flex-wrap: wrap;
    justify-content: space-around;
}

.welcomeBox {
    width: 100%;
    box-sizing: border-box;
    background-color: #f7f7f7;
    border-radius: 50px;
    padding: 40px 50px 10px;
    margin-bottom: 50px;
    .title {
        font-size: 30px;
        font-weight: 600;
        text-align: center;
        vertical-align: middle;
        color: #000;
    }
    .main {
        font-size: 17.5px;
        color: #000;
        line-height: 25px;
        .para {
            margin-top: 20px;
        }
        span{
            color: rgba(2, 111, 40, 0.877);
            font-size: 17.5px;
        }
    }
}

.titleBox{
    font-size: 18px;
    text-align: center;
    color: #FFF;
    width: 130px;
    height: 40px;
    line-height: 40px;
    border-radius: 8px;
    background-color:#007E37 ;
    margin: 0 calc(50% - 65px) 20px;    
}

.statisticsBox{
    width: 60%;
    box-sizing: border-box;
    display: flex;
    flex-wrap: wrap;
    .statisticsItem{
        width: 33.33%;
        margin-bottom:20px ;
        box-sizing: border-box;
        display: flex;
        flex-wrap: wrap;
        .image-container{
            width: 110px;
            height: 110px;
            margin-right: 12px;
            background-size:cover
        }
        p{
            font-size: 18px;
            color: #000;
            height: 25%;
            text-align: center;
            vertical-align: middle;
        }
        .text{
            display: flex;
            flex-direction: column;
            justify-content: center;
        }
    }
    .collectedDate{
        width: 100%;
        text-align: right;
        color: #000;
        font-size: 12px;
        margin-top: 20px;
    }
}

.latestNewsBox{
    width: 30%;
    box-sizing: border-box;
}

// 填充图片
.statistics-img{
    background: url(https://img.js.design/assets/img/658bed02c3c8605540da89e1.png#3d78101d1c3065a034787ab386f7480c);
}
.projects-img{
    background: url(https://img.js.design/assets/img/658bf10c5cc060c34d20fb67.png#df551fadd99c83bc4ec4cba952205627);
}
.runs-img{
    background: url(https://img.js.design/assets/img/658bf1d64dc150a2dea98fc0.png#9a255c42d86712e29f672763d6a0504e);
}
.sequences-img{
    background: url(https://img.js.design/assets/img/658bf142b985c292a83d7f41.png#b3e78975eeb7c1a160fab80af8f43218);
}
.microbiome-img{
    background: url(https://img.js.design/assets/img/658bf0c5cbee48a33ca07719.png#ce60e8d571364899d519e934bb162fd5);
}
.phenotype-img{
    background: url(https://img.js.design/assets/img/658bf072cc210967de975d8e.png#2924839c9829697ac07fcdd9721d99c9);
}
</style>