import request from  '../../utils/request.js';

// 根据项目名称获取项目信息
export function getProjectInfo(data){
  return request({
    url:'/Project/searchProjectOrSample',
    method:'post',
    data
  })
}

// 根据项目详细信息获取项目信息
export function getProjectInfoByDetail(data){
  return request({
    url:'/Project/searchProjectOrRuns',
    method:'post',
    data
  })
}