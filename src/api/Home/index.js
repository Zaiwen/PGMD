import request from  '../../utils/request.js';

// 获取首页statistics数据
export function getStatisticsAPI(){
  return request({
    url:'/Project/show',
    method:'get',
  })
}

// 获取上一次数据更新时间
export function getLastUpdateTime(){
  return request({
    url:'/lastUpdateTime',
    method:'get',
  })
}

// 获取公告信息
export function getNewsAPI(){
  return request({
    url:'/notice/getList',
    method:'get',
  })
}