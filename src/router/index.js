import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
    {
        path: '/',
        meta:{
            index:0
        },
        redirect: '/Home',
        component: () => import('../views/index.vue'),
        children:[
            {
                name: 'Home',
                path: '/Home',
                meta:{
                    index:1
                },
                component: () => import('../views/Home/index.vue')
            },
            {
                name: 'Browse',
                path: '/Browse',
                meta:{
                    index:2
                },
                component: () => import('../views/Browse/index.vue')
            },
            {
                name: 'Search',
                path: '/Search',
                meta:{
                    index:3
                },
                component: () => import('../views/Search/index.vue')
            },
            {
                name: 'Data',
                path: '/Data',
                meta:{
                    index:4
                },
                component: () => import('../views/Data/index.vue')
            },
            {
                name: 'Tools',
                path: '/Tools',
                meta:{
                    index:0
                },
                component: () => import('../views/Tools/index.vue')
            },
            {
                name: 'Help',
                path: '/Help',
                meta:{
                    index:7
                },
                component: () => import('../views/Help/index.vue')
            }
        ]
    },
]

// 添加index
routes[0].children.forEach((item,index)=>{
    item.meta.index = index
})

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});



//防止用户直接输入网址跳转到页面
// router.beforeEach((to, from, next) => {
//     if (to.path === '/Login') {
//         next()
//     } else {
//         const token = localStorage.getItem('token')
//         if (!token) {
//             next('/Login')
//         } else {
//             next()
//         }
//     }
// })
  
export default router