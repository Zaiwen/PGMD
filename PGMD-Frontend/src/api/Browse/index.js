import  request from  '../../utils/request.js';
import axios from 'axios'

// 在projects表中查
export function searchProjects(data){
    return request({
        url:'/Project/search_project',
        method:'post',
        data,
    })
}
// 在samples/runs表中查
export function searchSamples(data){
    return request({
        url:'/samples/search_sample',
        method:'post',
        data
    })
}

// 根据项目名称查询microbial表获取相关数据
export function getStackedBarData(project) {
    return request({
        url: `/microbial/search_microbial_byProject/${project}`,
        method: "get",
        timeout: 1000000,
    });
}

// 根据项目名称查询lefse表获取相关数据
export function getNegativeBarData(project) {
    return request({
        url: `/Project/search_lefse/${project}`,
        method: "get",
    })
}

// 根据项目id查询营养成分表获取相关数据
export function getNutritionData(project) {
    return request({
        url: `/nutrient/search/${project}`,
        method: "post",
    })
}

// 根据项目id下载对应表格
export function downloadTable(name) {
    return axios({
        method: 'post',
        url: `http://121.40.234.220:8090/Project/download/${name}`,
        responseType: 'blob'
    })
}
// 根据项目名称查询phenotype_Experiment和microbial_Name相同属性项目
export function getPhenotypeData(project) {
    return request({
        url: `/Lsfse/search_lefse/${project}`,
        method: "post",
    })
}

// 根据项目名称查询微生物分组及其比例
export function getCountgroupData(project) {
    return request({
        method: 'get',
        url: `/microbial/count_group/${project}`,
        timeout:1000000
    })
}