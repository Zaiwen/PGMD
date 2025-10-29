<template>
    <div class="login">
        <el-form ref="loginRef" :model="loginForm" :rules="loginRules" class="login-form">
            <div @click="closeDialog" class="close-button">
                <el-icon :size="26">
                    <Close/>
                </el-icon>
            </div>
            <h3 class="title">Login</h3>
            <el-form-item prop="username">
                <el-input v-model="loginForm.username" type="text" size="large" auto-complete="off" placeholder="username" prefix-icon="UserFilled">
                </el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input v-model="loginForm.password" type="password" size="large" auto-complete="off" placeholder="password" prefix-icon="Lock"
                    @keyup.enter="handleLogin">
                </el-input>
            </el-form-item>
            <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">Remember me</el-checkbox>
            <div class="register-tip">
                <a @click="toRegister">Register</a>
            </div>
            <el-form-item style="width:100%;">
                <el-button class="button_style" :loading="loading" size="large" type="primary" style="width:100%;"
                    @click.prevent="handleLogin">
                    <span v-if="!loading">Login</span>
                    <span v-else>Loading</span>
                </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>
<script setup>
import { ref ,getCurrentInstance} from "vue";
import {adminLogin, userLogin} from "../api/login";
import { ElMessage } from "element-plus";
import { useLoginDialogStore } from '../stores/modules/login';
import { useRegisterDialogStore } from "../stores/modules/register";
import { encrypt, decrypt } from "../utils/jsencrypt"
const proxy = getCurrentInstance();
const loginForm = ref({
    username: "",
    password: "",
    rememberMe: false,
    mail:"2258362782@qq.com"
});


//登录弹窗实例
const loginDialog = useLoginDialogStore();

// 注册弹窗实例
const registerDialog = useRegisterDialogStore();

//关闭弹窗
const closeDialog = () => {
    ElMessage.info('cancel login')
    loginDialog.closeLoginDialog();
}

const loginRules = {
    username: [{ required: true, trigger: "blur", message: "please input your username" }],
    password: [{ required: true, trigger: "blur", message: "please input your passward" }],
    code: [{ required: true, trigger: "change", message: "please input author code" }]
};

const loading = ref(false);

// 注册
const toRegister = () => {
    loginDialog.closeLoginDialog();
    registerDialog.openRegisterDialog();
}

// 管理员或者权限人员登入
function handleLogin() {
    proxy.refs.loginRef.validate((valid) => {
        if (valid) {
            loading.value = true;
            if (loginForm.value.rememberMe) {
                localStorage.setItem("username", loginForm.value.username);
                localStorage.setItem("password", encrypt(loginForm.value.password));
                localStorage.setItem("rememberMe", loginForm.value.rememberMe);
            } else {
                localStorage.removeItem("username");
                localStorage.removeItem("password");
                localStorage.removeItem("rememberMe");
            }
            //登录
            // 判断是否是管理员
            if(loginForm.value.username === 'admin'){
                adminLogin({
                    name:loginForm.value.username,
                    password:loginForm.value.password,
                    mail:loginForm.value.mail
                }).then((res) => {
                    console.log(res);
                    if (res.code == 200) {
                        //登录成功
                        loginDialog.closeLoginDialog();
                        loginDialog.isLogin = true;
                        // 储存用户名
                        localStorage.setItem('username',loginForm.value.username)
                        ElMessage.success('login successfully')
                    } else {
                        //登录失败
                        ElMessage.error('incorrect username or password');
                    }
                    loading.value = false;
                }).catch((err) => {
                    console.log(err);
                    ElMessage.error('incorrect username or password');
                    loading.value = false;
                })
            }else{
                userLogin({
                    name:loginForm.value.username,
                    password:loginForm.value.password,
                }).then((res) => {
                    console.log(res);
                    if (res.code == 200) {
                        //登录成功
                        loginDialog.closeLoginDialog();
                        loginDialog.isLogin = true;
                        // 储存用户名
                        localStorage.setItem('username',loginForm.value.username)
                        ElMessage.success('login successfully')
                    } else {
                        //登录失败
                        ElMessage.error('incorrect username or password');
                    }
                    loading.value = false;
                }).catch((err) => {
                    console.log(err);
                    loading.value = false;
                })
            }
        }
    });
}


function getCookie() {
    const username = localStorage.getItem("username");
    const password = decrypt(localStorage.getItem("password"));
    const rememberMe = localStorage.getItem("rememberMe");
    loginForm.value = {
    username: username === undefined ? loginForm.value.username : username,
    password: password === undefined ? loginForm.value.password : password,
    rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
    mail:'2258362782@qq.com'
  };
}

getCookie();
</script>

<style lang='less' scoped>
:deep(.is-checked) {
    color: #707070;
}

:deep(.el-checkbox__label) {
    color: #707070 !important;
}

:deep(.el-checkbox__input.is-checked .el-checkbox__inner) {
    background-color: #707070;
    border-color: #707070;
}

.close-button{
    position: absolute;
    right: 20px;
    color: #999;
    top: 20px;
    cursor: pointer;
    transition: all 0.3s;
    &:hover{
        color: #333;
    }
}
.button_style {
    border: #707070;
    background-color: #007E37;
    text-align: center;
    display: block;
    height: 50px;
    padding: 12px;
    font: 900 18px '';
    border-radius: 10px;
    color: #fff;
    letter-spacing: 3px;
}

.login {
    display: flex;
    position: fixed;
    left: 0;
    top: 0;
    z-index: 999;
    justify-content: center;
    align-items: center;
    height: 100vh;
    width: 100vw;
    background-color: #00000033;
}

.register-tip {
    float: right;
    height: 32px;
    line-height: 32px;
    a {
        color: #707070;
        text-decoration: none;
        transition: all 0.3s;
        cursor: pointer;
        &:hover {
            color: #333;
        }
    }
}

@keyframes fadeIn {
    to {
        opacity: 1;
    }
}


.title {
    margin: 0px auto 30px auto;
    text-align: center;
    color: #707070;

    font: 800 25px '';
    text-align: center;
    letter-spacing: 5px;
    color: #3d3d3d;
}

:deep(.el-input__inner:focus) {
    box-shadow:0 0 0 1px #007E37 inset !important;
}

.login-form {
    position: relative;
    border-radius: 6px;
    width: 400px;
    padding: 25px 25px 5px 25px;
    background-color: #fff;
    opacity: 0; // 默认设置为透明
    animation: fadeIn 0.5s forwards; // 2秒渐显效果
    .el-input {
        height: 40px;

        input {
            height: 40px;
        }
    }

    .input-icon {
        height: 39px;
        width: 14px;
        margin-left: 0px;
    }

}

.login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
}

.login-code {
    width: 33%;
    height: 40px;
    float: right;

    img {
        cursor: pointer;
        vertical-align: middle;
    }
}

.el-login-footer {
    height: 40px;
    line-height: 40px;
    position: fixed;
    bottom: 0;
    width: 100%;
    text-align: center;
    color: #fff;
    font-family: Arial;
    font-size: 12px;
    letter-spacing: 1px;
}

.login-code-img {
    height: 40px;
    padding-left: 12px;
}
</style>