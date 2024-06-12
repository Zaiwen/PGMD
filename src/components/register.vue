<template>
  <div class="register">
    <el-form ref="registerRef" :model="registerForm" :rules="registerRules" class="register-form">
      <div class="back-button">
        <el-icon @click="backToLogin" :size="26">
          <Back />
        </el-icon>
      </div>
      <div @click="closeDialog" class="close-button">
        <el-icon :size="26">
          <Close />
        </el-icon>
      </div>
      <h3 class="title">Register</h3>
      <el-form-item prop="mail">
        <el-input v-model="registerForm.mail" type="text" size="large" auto-complete="off" placeholder="email"
          prefix-icon="Message">
        </el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-input v-model="registerForm.code" type="text" size="large" auto-complete="off" placeholder="auth code"
          prefix-icon="Connection">
          <template #append>
            <el-button class="send-code" :disabled="sendCodeCd > 0" style="width: 100px;" @click="sendCode">{{
      sendCodeCd > 0 ? `${Math.floor(sendCodeCd)}s` : 'send code' }}</el-button>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item prop="name">
        <el-input v-model="registerForm.name" type="text" size="large" auto-complete="off" placeholder="username"
          prefix-icon="UserFilled">
        </el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input show-password v-model="registerForm.password" type="password" size="large" auto-complete="off"
          placeholder="password" prefix-icon="Lock">
        </el-input>
      </el-form-item>
      <el-form-item prop="passwordConfirm">
        <el-input show-password v-model="registerForm.passwordConfirm" type="password" size="large" auto-complete="off"
          placeholder="confirm password" prefix-icon="Lock">
        </el-input>
      </el-form-item>
      <el-form-item style="width:100%;">
        <el-button class="button_style" :loading="registering" size="large" type="primary" style="width:100%;"
          @click.prevent="handleRegister">
          <span v-if="!registering">Register</span>
          <span v-else>Registering</span>
        </el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script setup>
import { ref, getCurrentInstance, watch } from "vue";
import { ElMessage } from "element-plus";
import { useLoginDialogStore } from '../stores/modules/login';
import { useRegisterDialogStore } from "../stores/modules/register";
import { confirmRegister, getCode } from '../api/register'
const registerForm = ref({
  mail: "",
  code: "",
  password: "",
  passwordConfirm: "",
  name: "",
});

// 登录与注册弹窗实例
const registerDialog = useRegisterDialogStore();
const loginDialog = useLoginDialogStore();

//关闭弹窗
const closeDialog = () => {
  ElMessage.info('cancel register')
  registerDialog.closeRegisterDialog();
}

// 返回登录
const backToLogin = () => {
  registerDialog.closeRegisterDialog();
  loginDialog.openLoginDialog();
}

// 邮箱校验
const mailValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("please input your email"));
  }
  const reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/
  if (!reg.test(value)) {
    return callback(new Error("please input correct email"));
  }
  callback();
};

// 密码校验
const passwordValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("please input your password"));
  }
  if (value.length < 8) {
    return callback(new Error("password length must be greater than 8"));
  }
  if (value.length > 20) {
    return callback(new Error("password length must be less than 20"));
  }
  // 密码必须包含数字、字母、特殊字符中的两种
  const reg = /^(?![0-9]+$)(?![a-zA-Z]+$)(?![^0-9a-zA-Z]+$)\S+$/;
  if (!reg.test(value)) {
    return callback(new Error("password must contain at least two of the following: letters, numbers, special characters"));
  }
  callback();
};

// 密码确认校验
const passwordConfirmValidator = (rule, value, callback) => {
  if (!value) {
    return callback(new Error("please input your password again"));
  }
  if (value !== registerForm.value.password) {
    return callback(new Error("passwords do not match"));
  }
  callback();
};

const registerRules = {
  mail: [{ validator: mailValidator, trigger: "blur" }],
  code: [{ required: true, trigger: "blur", message: "please input auth code" }],
  password: [{ validator: passwordValidator, trigger: "blur" }],
  passwordConfirm: [{ validator: passwordConfirmValidator, trigger: "blur" }],
  name: [{ required: true, trigger: "blur", message: "please input your name" }],
};



// 表单实例
const registerRef = ref(null);

const registering = ref(false);

// 发送验证码
async function sendCode() {
  // 取消对验证码的校验
  registerRef.value.clearValidate('authCode')
  // 校验邮箱
  let isValid;
  await registerRef.value.validateField('mail').then(() => {
    isValid = true
  }).catch(() => {
    isValid = false
  })
  if (!isValid) {
    return
  }
  // 发送验证码
  getCode(registerForm.value.mail).then((res) => {
    if (res.code != 200) {
      ElMessage.error('error email or this email has been registered')
      return
    }
    ElMessage.success('send successfully')
    // 发送验证码
    sendCodeCd.value = 10;
    // 记录发送验证码的时间戳
    localStorage.setItem('sendCodeTime', Date.now())
  }).catch(() => {
    ElMessage.error('send failed')
  })
}

let timer = null;

// 记录发送验证码的cd
const sendCodeCd = ref(0);

// 当cd变化时，每秒减一
watch(sendCodeCd, (newVal) => {
  timer && clearTimeout(timer)
  if (newVal > 0) {
    timer = setTimeout(() => {
      sendCodeCd.value--
    }, 1000)
  }
})
// 在localStorage中获取验证码发送时间
const sendTime = localStorage.getItem('sendCodeTime')
sendCodeCd.value = Date.now() - sendTime > 10000 ? 0 : (10000 - (Date.now() - sendTime)) / 1000

// 注册
function handleRegister() {
  // 校验表单
  registerRef.value.validate((valid) => {
    if (!valid) return
  })
  console.log(112233);
  // 注册
  registering.value = true;
  const data = {
    name: registerForm.value.name,
    password: registerForm.value.password,
    mail: registerForm.value.mail,
    code: registerForm.value.code
  }
  confirmRegister(data).then((res) => {
    console.log(res.code, typeof (res.code));
    if (res.code == 200) {
      ElMessage.success('register successfully')
      registerDialog.closeRegisterDialog();
      loginDialog.openLoginDialog();
    } else {
      ElMessage.error("error auth code or username has been registered");
    }
  }).catch(() => {
    ElMessage.error('register failed')
  }).finally(() => {
    registering.value = false;
  })

}

</script>

<style lang='less' scoped>
.close-button,
.back-button {
  position: absolute;
  color: #999;
  top: 20px;
  cursor: pointer;
  transition: all 0.3s;

  &:hover {
    color: #333;
  }
}

.close-button {
  right: 20px;
}

.back-button {
  left: 20px;
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

.register {
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

.register-form {
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

}

:deep(.el-input-group__append) {
  color: #555;
  font-weight: 600;
}
</style>../stores/modules/register../stores/modules/login