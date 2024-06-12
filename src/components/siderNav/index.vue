<template>
    <div class="siderNav" v-show="!isChanging">
        <div class="axis"></div>
        <div class="item" v-for="(item, index) in navList" :key="index">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20">
                <circle cx="10" cy="10" r="10" fill="#007E37" />
                <circle cx="10" cy="10" r="5" fill="#fff" />
            </svg>
            <div class="navLink" @click="locateTo(index)">
                <span v-if="item.name.length < 15">{{ item.name }}</span>
                <el-tooltip
                    v-else
                    class="noEvent"
                    effect="customized"
                    placement="top"
                    :content="item.name"    
                    >
                    {{ item.name.slice(0, 15) + '...' }}
                </el-tooltip>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, reactive, watch, onUnmounted } from 'vue'

const props = defineProps({
    navList: {
        type: Array,
        require: true,
    },
    flash: {
        type: Boolean,
        require: false,
        default: false
    },
    changeFlag: {
        type: Number,
        require: false,
        default: true
    }
})

let navList = ref(props.navList)
const _navList = props.navList
console.log(_navList,'_navList');
const flash = props.flash

//定位模块的位置
const location = ref([])

// 记录是否正在切换模块
const isChanging = ref(false)

//填充模块位置坐标及轴长
const fillLocation = () => {
    console.log('fillLocation');
    // 获取页面完整高度-视口高度
    const body=document.querySelector('body')
    const scrollHeight=body.scrollHeight-window.innerHeight
    // 过滤掉高度为0的模块
    navList.value = _navList.filter(item => {
        const target = document.querySelector(item.location)
        return target.offsetHeight !== 0
    })
    const navAxis = document.querySelector('.axis')
    navAxis.style.height = navList.value.length * 60 + 'px'
    location.value = []

    navList.value.forEach((item, index) => {
        const target = document.querySelector(item.location)
            // console.log(target,'target');
            location.value.push({
                axisY:  window.scrollY + target.getBoundingClientRect().top + target.offsetHeight / 2 - window.innerHeight / 2>scrollHeight?
                scrollHeight-10:
                window.scrollY + target.getBoundingClientRect().top + target.offsetHeight / 2 - window.innerHeight / 2,
                location: item.location,
                height: target.offsetHeight
            })
    })
    isChanging.value = false
    // 给最后一个模块设置高度
    // console.log(location.value,'location');
}

//记录上一个导航栏链接
let lastLink = null
let lastTag = null

//动态设置导航栏链接的样式
const setNavLinkStyle = (navLink) => {
    //设置导航栏链接高亮
    navLink.style.setProperty('--textcolor', '#1C683D')
    lastLink && lastLink.style.setProperty('--textcolor', 'rgba(0, 0, 0, 0.8)')
    lastLink = navLink

    //设置链接前的小圆点
    const tag = navLink.previousSibling
    tag.style.setProperty('--tagScale', '1')
    lastTag && lastTag.style.setProperty('--tagScale', '0')
    lastTag = tag
}

//点击跳转后，对应模块闪烁
const showFlash = (target) => {
    target.classList.add('flash')
    setTimeout(() => {
        target.classList.remove('flash')
    }, 1000)
}

//当前所在的模块
const currentModule = reactive({
    index: 0,
    location: '.overViewBox'
})

//监听模块变化
const watchCurrentModule = (newVal, oldVal) => {
    if (newVal === oldVal) return
    const target = document.querySelectorAll('.navLink')[newVal]
    setNavLinkStyle(target)
}
watch(() => currentModule.index, watchCurrentModule)

//监听滚动条滚动事件
const lisentScroll = () => {
    // console.log(window.scrollY,'scrollY');
    for (let i = 0; i < location.value.length; i++) {
        if (window.scrollY >= location.value[i].axisY) {
            currentModule.index = i
            currentModule.location = location.value[i].location
        }
    }
}


//点击导航栏链接，滚动到指定位置
const locateTo = (index) => {
    //设置导航栏链接的颜色
    if (event.target === lastLink) return
    //滚动到指定位置
    window.scrollTo({
        top: location.value[index].axisY + 1,
        behavior: 'smooth'
    })
    //是否闪烁
    if (flash)
    showFlash(document.querySelector(navList.value[index].location))
}

onMounted(() => {
    isChanging.value = true
    //填充模块位置坐标,异步填充数据
    setTimeout(fillLocation,100)
    //初始化导航栏链接样式
    watchCurrentModule(0, -1)
    window.addEventListener('scroll', lisentScroll, { passive: true })
})

// 监视dom元素尺寸变化
onMounted(() => {
    const observer = new ResizeObserver(() => {
        fillLocation()
    })
    navList.value.forEach(item => {
        observer.observe(document.querySelector(item.location))
    })
})

// 监听changeFlag变化
watch(() => props.changeFlag, (newVal, oldVal) => {
    if (newVal === oldVal) return
    fillLocation()
})

onUnmounted(()=>{
    // 取消事件监听
    window.removeEventListener('scroll', lisentScroll, { passive: true })
})

</script>

<style lang="less">
.el-popper.is-customized {
  padding: 6px 12px;
  background: linear-gradient(90deg, #1c683d, rgb(192, 221, 104));
  color: #fff;
  font-size: 18px;
  z-index: 999;
  pointer-events: none;
}
.el-popper.is-customized .el-popper__arrow::before {
    background: linear-gradient(45deg, #699e5b, #80af64);
    right: 0;
}
</style>

<style scoped lang="less">


.siderNav {
    position: sticky;
    left: 5%;
    top: 20%;
    transform: translateY(200px);
    float: left;
    z-index: 99;

    .axis {
        position: absolute;
        width: 8px;
        height: 100%;
        background-color: #CCCCCC;
        border-radius: 5px;
        top: 0;
        left: 10px;
        transform: translateX(-50%);
        z-index: -1;
    }

    .item {
        position: relative;
        width: 100%;
        height: 30px;
        margin-top: 20px;
        display: flex;
        align-items: center;

        svg {
            margin-right: 10px;
            --tagScale: 0;
            transform: scale(var(--tagScale));
            transition: all 0.2s;
        }

        .navLink {
            font-size: 20px;
            font-weight: 500;
            color: rgba(0, 0, 0, 0.8);
            transition: all 0.3s;
            color: var(--textcolor);

            &:hover {
                color: #1C683D;
                cursor: pointer;
            }
        }
    }
}
</style>