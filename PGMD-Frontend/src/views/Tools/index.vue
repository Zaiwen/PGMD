<template>
    <div class="outBox">
        <div class="introductionBox">
            <div class="title">Tools</div>
            <div class="main">
                <p class="para"> This page is used to present some of PGMD's in-house or publicly available analytical 
                    and research tools to assist researchers in the field of swine ( Microbes, Nutrition, Phenotypes, etc.).
                </p>
            </div>
        </div>
        <div class="cardsBox">
            <div class="card" 
                v-for="(item,index) in cardData"
                :key="index"
                >
                <p class="title">{{item.title}}</p>
                <p class="content">{{ item.content }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'

//卡片数据

const cardData = ref([
    {
        title: 'Feed Proportion',
        content: 'Aiming at the lowest money-to-meat ratio, we analyze the data in combination with PGMD to provide users with feeds for different stages of the hog breeding process.'
    },
    {
        title:'Search',
        content:'Researchers can use the search page to customize their search criteria and search for content of interest.'
    },
    {
        title:'Data',
        content:'Some statistical information of the PGMD database, as well as some data uploading and feedback functions, are aimed at hopefully improving the functionality of the PGMD through user suggestions.'
    },
    {
        title:'Tools',
        content:'As mentioned in the literature, we intend to design some tools for users in the field of pig nutrition.'
    }
])

</script>

<style scoped lang="less">
.introductionBox {
    width: 100%;
    box-sizing: border-box;
    background-color: #f7f7f7;
    border-radius: 50px;
    padding: 40px 50px 100px;
    margin-bottom: 100px;

    .title {
        font-size: 30px;
        font-weight: 600;
        color: #000;
    }

    .main {
        font-size: 17.5px;
        color: #000;
        line-height: 25px;

        .para {
            margin-top: 20px;
        }
    }
}

.cardsBox {
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    box-sizing: border-box;
    margin-bottom: 50px;
    padding: 0 2%;

    .card {
        box-sizing: border-box;
        background-color: #498664;
        border-radius: 15px;
        width: 14vw;
        height: 21vw;
        padding: 1.5vw;

        .title {
            text-align: center;
            font-size: 24px;
            color: #fff;
            margin-bottom: 20px;
        }

        .content {
            font-size: 14.5px;
            color: #fff;
            line-height: 25px;
        }
    }
}
</style>