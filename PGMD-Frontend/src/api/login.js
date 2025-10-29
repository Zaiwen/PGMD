import  request from  '../utils/request.js';
// 管理员登录
export function adminLogin(data){
  return request({
    url:'/login/administrator',
    method:'post',
    data
  })
}

// 用户登录
export function userLogin(data){
  return request({
    url:'/login/user',
    method:'post',
    data
  })
}