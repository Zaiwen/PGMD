<template>
  <div>
    <div class="website-nav">
      <div class="logobg">
        <img src="../../assets/img/logo.png">
      </div>
      <p class="title">Pig Gut Microbiome Database</p>
      <el-menu mode="horizontal" router :default-active="defaultPage" class="el-menu-demo" text-color="#fff"
        background-color="#00652c" active-text-color="#f88519">
        <el-menu-item @click="saveCurrentPage" v-for="item in routerArr" :key="item" :index="'/' + item">
          {{ item }}
        </el-menu-item>
      </el-menu>

      <div class="logout">
        <el-popconfirm v-if="loginDialog.isLogin" title="Confirm to logout?" @confirm="logout">
          <template #reference>
            <div class="logout-button">{{ username }}
              <div class="logout-tooltip">click to logout</div>
            </div>
          </template>
        </el-popconfirm>
        <div v-else @click="openLogin" class="login-container">
          <el-icon color="#fff" :size="30">
            <UserFilled />
          </el-icon>
          <p class="login-text">login</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { getCurrentInstance, ref, watch } from 'vue'
import { removeToken } from "../../utils/auth";
import { useLoginDialogStore } from "../../stores/modules/login";

//vue实例
const {
  proxy: { $modal },
} = getCurrentInstance();

const router = useRouter();

//登录弹窗实例
const loginDialog = useLoginDialogStore();

// 监视登录状态
watch(() => loginDialog.isLogin, (newVal) => {
  getUser()
})

//路由数组
const routerArr = [
  "Home",
  "Browse",
  "Search",
  "Data",
  "Tools",
  "Help",
];

// 获取用户名
const username = ref('')
const getUser = () => {
  username.value = localStorage.getItem('username')
}
getUser()

//记录当前页面
const defaultPage = sessionStorage.getItem("currentPage") || '/' + routerArr[0];
const saveCurrentPage = (e) => {
  sessionStorage.setItem("currentPage", e.index);
};

//退出登录
const logout = () => {
  removeToken()
  //重新挂载页面
  loginDialog.isLogin = false;
  ElMessage.success('logout successfully')
}

//打开登录弹窗
const openLogin = () => {
  loginDialog.openLoginDialog();
};


</script>

<style scoped lang="less">
body {
  font-family: "Noto Sans SC";
}

.website-nav {
  height: 80px;
  position: relative;
  background-color: #007E37;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: center;
}

.logobg {
  height: 100%;
}

.logobg img {
  height: 100%;
  width: 100px;
  margin-left: 10px;
}

.title {
  font-size: 25px;
  color: #fff;
}

.el-menu-demo {
  height: 100%;
  width: 800px;
  border-bottom: 0;
  background: rgba(0, 126, 55, 1);
}

:deep(.el-menu-item) {
  font-size: 25px;
  color: #fff;
  height: 100%;
}

.logout {
  position: absolute;
  right: 40px;

  .logout-button {
    position: relative;
    background-color: #007E37;
    color: #fff;
    font-size: 14px;
    border-radius: 5px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2) inset;
    padding: 0 15px;
    height: 40px;
    line-height: 40px;
    cursor: pointer;
    transition: all 0.2s;

    &:hover {
      box-shadow: 0 0 20px rgba(0, 0, 0, 0.2) inset;
    }

    &:hover .logout-tooltip {
      opacity: 1;
    }

  }

  .logout-tooltip {
    position: absolute;
    width: 100px;
    top: 130%;
    left: 50%;
    text-align: center;
    transform: translateX(-50%);
    background-color: #eee;
    color: #000;
    font-size: 13px;
    padding: 0px 10px;
    border-radius: 5px;
    opacity: 0;
    transition: all 0.2s;
    pointer-events: none;

    &:after {
      content: '';
      position: absolute;
      top: -13px;
      left: 50%;
      transform: translateX(-50%);
      border: 7px solid transparent;
      border-bottom-color: #eee;
    }
  }

  .login-container {
    display: flex;
    align-items: center;
    cursor: pointer;

    .login-text {
      color: #fff;
      font-size: 18px;
      margin-left: 10px;
    }
  }
}

@media(max-width: 1300px) {
  .title {
    display: none;
  }
}
</style> ../../stores/modules/login