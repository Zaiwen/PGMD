import request from '../utils/request.js';
// 注册

// data: {mail,name,password,code}
export function confirmRegister(data){
  return request({
    url:'/register/user',
    method:'post',
    data
  })
}

// 获取验证码
export function getCode(email){
  return request({
    url:'/register/sendEmail',
    method:'post',
    params:{
      email
    }
  })
}