<template>
  <div>
    <login v-if="loginDialog()"></login>
    <register v-if="registerDialog()"></register>
    <div class="mainBox">
      <header-nav></header-nav>
      <div class="view">
          <router-view v-slot="{Component}">
              <transition :name="leftOrRight" mode="out-in">
                <component :is="Component"></component>
              </transition>
            </router-view>  
      </div>
    </div>
    <footer-info></footer-info>
    <el-tooltip content="Back to top" effect="light">
    <el-backtop class="back-top-container" :right="100" :bottom="100">
        <el-icon class="back-top" :size="25" color="#000">
          <DArrowLeft />
        </el-icon>
    </el-backtop>
  </el-tooltip>
  </div>
</template>

<script setup>
import { ref,watch } from 'vue'
import {onBeforeRouteUpdate } from 'vue-router'
import { useLoginDialogStore } from '../stores/modules/login';
import { useRegisterDialogStore } from "../stores/modules/register";
const loginDialog =()=>useLoginDialogStore().isShow;
const registerDialog =()=>useRegisterDialogStore().isShow;

// 禁止滚轮滚动
const preventWheel=(e)=>{
  e.preventDefault()
}

let winX=0,winY=0;

// 禁止滚动条
const preventScroll=(e)=>{
  console.log(123);
  window.scrollTo(winX,winY)
}

// 监听isShow的变化
watch([loginDialog, registerDialog],([loginNewVal, registerNewval])=>{
  console.log(loginNewVal, registerNewval);
  if(loginNewVal || registerNewval){
    // 禁用滚动事件
    winX=window.scrollX
    winY=window.scrollY
    window.addEventListener('wheel',preventWheel,{passive:false})
    window.addEventListener('scroll',preventScroll,{passive:false})
  }else{
    // 移除监听事件
    window.removeEventListener('wheel',preventWheel)
    window.removeEventListener('scroll',preventScroll)
  }
})

//监听路由变化
const leftOrRight = ref('')

onBeforeRouteUpdate((to, from) => {
  to.meta.index > from.meta.index ? leftOrRight.value = 'rightFade' : leftOrRight.value = 'leftFade'
})

</script>
  
<style scoped lang="less">
.mainBox {
  min-height: 80vh;
}

.view{
  box-sizing: border-box;
  width: 90%;
  margin: 8vh auto 8vh;
}

.back-top-container{
  .back-top{
    transform: rotate(90deg) translateX(-1px);
    font-weight: 600;
  }
}

</style>
