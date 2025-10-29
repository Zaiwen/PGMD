import { createApp } from 'vue'
import App from './App.vue';
import ElementPlus from 'element-plus';
import '/node_modules/element-plus/dist/index.css'
import router from "./router/index.js"
import elementIcons from './components/SvgIcon/svgicon.js'
import { setupStore } from './stores/index.js'

// 全局样式
import '../src/assets/styles/element-plus.css'
import '../src/assets/styles/global.css'

// 侧边导航栏组件
import siderNav from './components/siderNav/index.vue'
// 底部信息组件
import footerInfo from './components/footerInfo/index.vue'
// 头部导航栏组件
import headerNav from './components/headerNav/index.vue'
// 登录组件
import login from './components/login.vue'
// 注册组件
import register from './components/register.vue'

// vue实例
const app = createApp(App)

// 全局注册
setupStore(app)
app.use(ElementPlus)
app.use(router)
app.use(elementIcons)

// 全局组件挂载
app.component('sider-nav',siderNav)
app.component('footer-info',footerInfo)
app.component('header-nav',headerNav)
app.component('login',login)
app.component('register',register)

app.mount('#app')