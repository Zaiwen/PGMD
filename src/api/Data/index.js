import request from  '../../utils/request.js';
import { getToken } from '../../utils/auth.js';

// 上传项目文件
export function uploadProjectFileAPI(data){
  return request({
    url:'/Project/uploadFile',
    method:'post',
    data,
    timeout: 1000 * 30,
    headers: {
      Authorization: getToken(),
    }
  })
}

// 统计Category
export function countCategory(){
  return request({
    url: '/Project/count_category',
    method:'get',
  })
}

// 统计country
export function countCountry() {
  return request({
    url: 'Project/count_country',
    method:'get'
  })
}

// 统计runs
export function countRuns() {
  return request({
    url: 'Project/count_runs',
    method:'get'
  })
}

// 统计isolation
export function countIsolation() {
  return request({
    url: 'Project/count_isolation',
    method:'get'
  })
}

// 查找根据微生物name值查找level值出现的次数
export function countLevel() {
  return request({
    url: `/microbial/count_level`,
    method:'get'
  })
}

// 下载模板文件
export function downloadTemplateFileAPI(name){
  return request({
    url:`/Project/downloadTemplateFile/${name}`,
    method:'post',
    responseType: 'blob',
  })
}