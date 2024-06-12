<template>
    <div class="outBox">
        <div class="searchBox">
            <el-form 
                ref="ruleFormRef" 
                class="nameSearch" 
                :model="nameSearchForm" 
                :rules="nameSearchFormRules">
                <span class="searchTitle">Search Targets</span>
                <el-form-item prop="projectType">
                    <el-select
                        class="projectType-selector"
                        placeholder="project type"
                        size="large"
                        v-model="nameSearchForm.phenoTypeId">
                        <el-option
                        v-for="(item,index) in projectTypeList"
                        :key="index"
                        :label="item.label"
                        :value="item.id">
                    </el-option>
                    </el-select>
                </el-form-item>
                <span class="searchTitle">Query box</span>
                <el-form-item prop="name">
                    <el-input 
                        clearable 
                        size="large" 
                        placeholder="e.g. Firmicutes or Average daily weight gain(ADGkg/d) or L-Arginine" 
                        v-model="nameSearchForm.name" 
                        suffix-icon="Search">
                    </el-input>
                </el-form-item>
                <el-item-form>
                    <el-button
                        @click="nameSubmit()" 
                        class="button searchBtn" 
                        :loading="nameSearchLoading">
                        Search</el-button>
                </el-item-form>
            </el-form>
            <div class="detailSearch">
                <div class="chooseFormBox">
                    <div class="searchTitle">Query Conditions</div>
                    <el-select 
                        placeholder="query conditions" 
                        v-model="choosingForm"
                        multiple
                        clearable
                        @change="handleChange">
                        <el-option
                            v-for="(item0,index) in form"
                            :key="index"
                            :label=toLine(index)
                            :value="index"
                        ></el-option>
                    </el-select>
                </div>
                <div class="searchFormBox">
                    <div class="searchTitle">Search</div>
                    <div 
                        v-if="!choosingForm.length" 
                        class="searchTitle" 
                        style="margin-top: 50px;">
                        No Query Condition!</div>
                    <div class="searchFormItem"
                        v-for="(item,index) in choosingForm"
                        :key="index">
                        <div class="title">{{toLine(item)}}</div>
                        <el-cascader v-if="item==='experimentalDesign'"
                            :placeholder="item"
                            :show-all-levels="false"
                            :props="{emitPath:false}"
                            :options="formOptions[item]"
                            v-model="form[item]">
                            <template #default="{ node, data }">
                                <el-tooltip
                                v-if="node.isLeaf"
                                    effect="customized"
                                    placement="top"
                                    >
                                    <template #content>
                                        <div style="max-width: 160px">
                                        {{ data.label }}
                                        </div>
                                    </template>
                                    <span v-if="data.label && data.label.length <= 30">
                                        {{ data.label }}
                                    </span>
                                    <span v-if="data.label && data.label.length > 30">
                                        {{ data.label.substring(0, 30) + "..." }}
                                    </span>
                                </el-tooltip>
                                <span style="width: 250px;" v-else>{{ toBlank(data.label) }}</span>
                            </template>
                        </el-cascader>
                        <el-select
                            v-else
                            :placeholder="item"
                            v-model="form[item]">
                            <el-option
                            v-for="(option,index) in formOptions[item]"
                            :key="index"
                            :label="option"
                            :value="option">
                            </el-option>
                        </el-select>
                        <el-input clearable 
                            v-model="form[item]"
                            :placeholder="`e.g.${formExample[item]}`"
                            suffix-icon="Search">
                        </el-input>
                    </div>
                </div>
                <div class="submitBox">
                    <el-button 
                        @click="fillExample" 
                        class="button exampleBtn">
                        Example</el-button>
                    <el-button 
                        @click="reset" 
                        class="button clearBtn">
                        Clear</el-button>
                    <el-button 
                        @click="detailSubmit" 
                        class="button submitBtb" 
                        :loading="detailSearchLoading">
                        Submit</el-button>
                </div>
            </div>
        </div>
        <div class="tableBox" v-if="tableData.length">
        <div class="title">{{ projectTypeList[phenoTypeId].label }}</div>
        <el-table
            :data="
            tableData.slice(
                (currentPageNum - 1) * pageSizeNum,
                currentPageNum * pageSizeNum
            )
            "
            border
            stripe
            max-height="516px"
            @row-click="showDetail"
            style="width: 100%"
            :cell-style="emptyHandler"
        >
            <el-table-column
            v-for="(item, index) in getTableColumns()"
            :key="index"
            :label="item.label"
            :min-width="120"
            >
            <template #header>
                <el-tooltip
                effect="customized"
                placement="top"
                >
                <template #content>
                    <div style="max-width: 160px">
                    {{ item.label }}
                    </div>
                </template>
                <span v-if="item.label && item.label.length <= 30">
                    {{ item.label }}
                </span>
                <span v-if="item.label && item.label.length > 30">
                    {{ item.label.substring(0, 30) + "..." }}
                </span>
                </el-tooltip>
            </template>
            <template #default="{ row }">
                <el-tooltip
                effect="customized"
                placement="top"
                >
                <template #content>
                    <div style="max-width: 160px">
                    {{ row[item.prop] }}
                    </div>
                </template>
                <span v-if="row[item.prop] && row[item.prop].length <= 30">
                    {{ row[item.prop] }}
                </span>
                <span v-if="row[item.prop] && row[item.prop].length > 30">
                    {{ row[item.prop].substring(0, 30) + "..." }}
                </span>
                </el-tooltip>
            </template>
            </el-table-column>
        </el-table>
        <el-pagination
            class="pagination"
            background 
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :total="totalNum"
            :page-sizes="[5, 10, 20, 30, 50]"
            :page-size="pageSizeNum"
            :current-page="currentPageNum" />
        </div>
        <template v-if="JSON.stringify(choosingProject)!='{}' && phenoTypeId === 1">
            <ProjectDetail :choosingProject="choosingProject" :isShowSiderNav="phenoTypeId === 1"/>
        </template>
    </div>
</template>

<script setup>
import { ref, reactive, watch} from 'vue';
import { ElMessage } from 'element-plus';
import { getProjectInfo, getProjectInfoByDetail } from "../../api/Search/index.js"
import searchSelectDatas from './selectData';
import ProjectDetail from '../../components/ProjectDetail/index.vue';

// 处理空白单元格
const emptyHandler = ({row,column}) => {
  row[column.property] = row[column.property] || ''
}


// 小驼峰转下划线+首字母大写
const toLine = (name) => {
    let newName =name.replace(/([A-Z])/g, "_$1").toLowerCase();
    return newName.charAt(0).toUpperCase() + newName.slice(1);
}

// 小驼峰转空格+首字母大写
const toBlank = (name) => {
    let newName =name.replace(/([A-Z])/g, " $1").toLowerCase();
    return newName.charAt(0).toUpperCase() + newName.slice(1);
}


// 处理嵌套对象中的null与数字
const handleNumber = (obj) => {
  for (let key in obj) {
    if (obj[key] == null) {
      obj[key] = "";
    } else if (typeof obj[key] == "object") {
      handleNumber(obj[key]);
    }
    if(typeof(obj[key])=='number' && obj[key]){
        obj[key]=obj[key].toString()
    }
  }
};

// 按名称与表类型查询表单 start

// 按名称与表类型查询表单
const nameSearchForm=reactive({
    name:'',
    phenoTypeId:1
})

// 表单实例
const ruleFormRef = ref(null)

// 按名称与表类型查询表单校验规则
const nameSearchFormRules=reactive({
    name:[
        { required: true, message: 'please input search condition', trigger: 'blur' }
    ]
})

// 供选择的表类型
const projectTypeList=ref([
    {
        label:'Samples/Runs',
        id:0
    },
    {
        label:'Projects',
        id:1
    },
    {
        label:'Microbiome',
        id:2
    }
])

// 展示的表类型
const phenoTypeId=ref(1)

// 根据当前选择表类型获取表格标签
const getTableColumns=()=>{
    if(phenoTypeId.value===1){
        return projectsTableColumns.value
    }else if(phenoTypeId.value===0){
        return samplesTableColumns.value
    }else{
        return microbiomeTableColumns.value
    }
}

// 搜索loading
const nameSearchLoading = ref(false)

// 按名称搜索提交
const nameSubmit=async ()=>{
    // 校验表单
    let isValid;
    await ruleFormRef.value.validate(valid => {
        isValid=valid
        return
    })
    if(!isValid){
        return
    }
    nameSearchLoading.value=true
    getProjectInfo(nameSearchForm)
    .then(res=>{
        phenoTypeId.value=nameSearchForm.phenoTypeId
        tableData.value=res.data
        handleNumber(tableData.value)
        // 判断是否为空
        if(tableData.value.length===0){
            ElMessage.warning('no suitable data!')
        }else{
            ElMessage.success('search success!')
        }      
    }).catch(err=>{
        console.log('request error!',err);
    }).finally(()=>{
        nameSearchLoading.value=false
    })
}

// 按名称与表类型查询表单 end

// 精确查询表单 start

//精确查询表单
const form =reactive({
    phenotypes:'',
    experimentalDesign:'',
    country:'',
    category:'',
    breed:'',
    growthStage:'',
    isolationLocation:'',
    sex:'',
})

//下拉框选项
const formOptions=reactive({
    phenotypes:searchSelectDatas.phenotypes,
    experimentalDesign:searchSelectDatas.experimentalDesign,
    country:searchSelectDatas.country,
    category:searchSelectDatas.category,
    breed:searchSelectDatas.breed,
    growthStage:searchSelectDatas.growthStage,
    sex:searchSelectDatas.sex,
    isolationLocation:searchSelectDatas.isolationLocation,
})

//示例项
const formExample=reactive({
    phenotypes:'Average daily feed intake(ADFI kg/d)',
    experimentalDesign:'Feed additives',
    country:'China',
    category:'Piglets',
    breed:'Duroc ×  Landrace × Yorkshire',
    growthStage:'Growing-finishing Pigs(50-80kg)',
    isolationLocation:'Colon',
    sex:'famale',
})

//选中的查询条件
let choosingForm=ref(['phenotypes','experimentalDesign','country','category'])

//点击填充示例项
const fillExample=()=>{
    reset()
    choosingForm.value.forEach(key=>{
        form[key]=formExample[key]
    })
}

//清空表单
const reset=()=>{
    nameSearchForm.projectType=[]
    nameSearchForm.name=''
    for(let i in form){
        form[i]=''
    }
    delete form.projectType
}

const detailSearchLoading=ref(false)

//精确搜索提交
const detailSubmit=()=>{
    if(!choosingForm.value.length){
        ElMessage.warning('please choose query conditions!')
        return
    }
    // 检查phenotypes与experimental_design是否同时存在
    if(form.phenotypes&&form.experimentalDesign){
        ElMessage.warning('phenotypes and experimental_design cannot exist at the same time!')
        return
    }else if(form.experimentalDesign){
        form.phenoTypeId=phenoTypeId.value=0
    }else{
        form.phenoTypeId=phenoTypeId.value=1
    }
    detailSearchLoading.value=true
    getProjectInfoByDetail(form)
    .then(res=>{
        tableData.value=res.data
        handleNumber(tableData.value)
        // 判断是否为空
        if(tableData.value.length===0){
            ElMessage.warning('no suitable data!')
        }else{
            ElMessage.success('search success!')
        }       
    }).catch(err=>{
        console.log('request error!',err);
    }).finally(()=>{
        detailSearchLoading.value=false
    })

    delete form.phenoTypeId

}

//条件变化时回调
const handleChange=(val)=>{
    //清空未选中的表单项
    for(let i in form){
        if(!val.includes(i)){
            form[i]=''
        }
    }
}

// 精确查询表单 end

// 表格部分 start

//pojects表格标签
const projectsTableColumns = ref([
{
        prop: 'project',
        label: 'ProjectID',
    },
    {
        prop: 'description',
        label: 'Description',
    },
    {
        prop: 'phenotypes',
        label: 'Phenotypes'
    },
    {
        prop: 'experimentalDesign',
        label: 'Experimental Design'
    },
    {
        prop: 'totalNumbersOfRuns',
        label: 'Sample number'
    },
    {
        prop: 'country',
        label: 'Country'
    },
    {
        prop: 'breed',
        label: 'Breed'
    },
    {
        prop: 'category',
        label: 'Category'
    },
    {
        prop: 'growthStage',
        label: 'Growth Stage'
    },
    {
        prop: 'isolationLocation',
        label: 'Isolation Location'
    },
    {
        prop: 'weight',
        label: 'Weight'
    },
    {
        prop: 'age',
        label: 'Age'
    },
    {
        prop: 'sex',
        label: 'Sex'
    },
    {
        prop: 'sequencingTool',
        label: 'Platform'
    },
    {
        prop: 'assayType',
        label: 'AssayType'
    },
    {
        prop: 'title',
        label: 'Title'
    },
    {
        prop: 'link',
        label: 'Link'
    },
    {
        prop: 'releaseDate',
        label: 'Release Date'
    },

])

// samples表格标签
const samplesTableColumns=ref([
{prop:'runs',label:'Runs'},
{prop:'bioSample',label:'BioSample'},
{prop:'bioProject',label:'BioProject'},
{prop:'experimental_Design',label:'Experimental Design'},
{prop:'isolation_Location',label:'Isolation Location'},
{prop:'breed',label:'Breed'},
{prop:'category',label:'Category'},
{prop:'country',label:'Country'},
{prop:'group',label:'Group'},
{prop:'subGroup',label:'SubGroup'},
{prop:'age',label:'Age'},
{prop:'growth_Stage',label:'Growth Stage'},
{prop:'sex',label:'Sex'},
{prop:'weight',label:'Weight'},
{prop:'library_Layout',label:'Layout'},
{prop:'release_Date',label:'Release Date'},
{prop:'platform',label:'Sequencing Tool'},
])

// Microbiome表格标签
const microbiomeTableColumns=ref([
{prop:'id',label:'ID'},
{prop:'microbialName',label:'Microbial Name'},
{prop:'bioProject',label:'BioProject'},
{prop:'specificTaxonomy',label:'Specific Taxonomy'},
{prop:'level',label:'Level'},
{prop:'count',label:'Count'},
{prop:'abundance',label:'Abundance'},
{prop:'runs',label:'Runs'},
])

//表格数据
const tableData = ref([])

// 营养成分表格数据
const nutritionData = ref([])

// 表格分页
const currentPageNum = ref(1)
const pageSizeNum = ref(5)
const totalNum = ref(0)

// 分页事件
const handleSizeChange = (val) => {
    pageSizeNum.value = val
}

const handleCurrentChange = (val) => {
    currentPageNum.value = val
}

// 监视表格数据变化
watch(tableData, (newVal) => {
    totalNum.value = newVal.length
    currentPageNum.value = 1
})

// 点击表格行显示详情
const showDetail = (row) => {
    if(phenoTypeId.value === 0){
        return
    }
    choosingProject.value = row
}

// 表格部分 end

// 项目浏览及可视化图表部分 start

const choosingProject=ref({})

</script>


<style>
.el-popper.is-customized {
  padding: 6px 12px;
  background: linear-gradient(90deg, #1c683d, rgb(192, 221, 104));
  color: #fff;
  font-size: 18px;
}

.el-popper.is-customized .el-popper__arrow::before {
  background: linear-gradient(45deg, #699e5b, #80af64);
  right: 0;
}
</style>

<!-- 深层样式 -->
<style scoped lang="less">

:deep(.el-input__inner) {
    background-color: #F7F7F7;
    border-radius: 0px;
}
:deep(.el-select .el-input__inner),
:deep(.el-cascader .el-input__inner){
    &::placeholder {
        color: #8E8E8E;
    }
    background-color: #F5F5F5;
}

:deep(.el-cascader-node),
:deep( .in-active-path) {
    width: 200px;
}

:deep(.el-form-item){
    margin-bottom: 0px;
}

:deep(.el-table__cell) {
    font-size: 13px;
}

:deep(.cell){
  word-break: keep-all;
}

:deep(thead) {
  font-weight: 600;
  color: rgba(0, 0, 0, 0.8);
}
</style>


<style scoped lang="less">

.button {
    background-color: #1C683D;
    color: #fff;
    border: none;
    border-radius: 0px;
}
.searchBox{
    display: flex;
    flex-wrap: wrap;
    width: 100%;
    // margin-left: 10%;
}

.nameSearch {
    display: flex;
    width: 100%;
    height: 40px;
    margin-bottom:30px;
    justify-content: space-between;
    align-items: center;
    .inputBox{
        margin-left:5%;
        // width: 70%;
    }
    .searchTitle{
        font-size: 20px;
        font-weight: 600;
        color:#7f7f7f;
        // margin-right: 20px;
    }
    .searchBtn {
        width: 90px;
        font-size: 16px;
        height: 40px;
        margin-left: 50px;
    }
    .el-input{
        margin-left: 20px;
        width: 40vw;
    }
    .projectType-selector{
        width: 180px;

    }
}

.detailSearch{
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    .searchTitle{
        width: 100%;
        font-size: 20px;
        font-weight: 600;
        margin-bottom: 20px;
        color:#7f7f7f;
    }
    .chooseFormBox{
        width: 30%;
        margin-top: 20px;
        margin-bottom: 20px;
        .el-select {
            width: 70%;
        }
    }
    .searchFormBox{
        width: 70%;
        min-height: 350px;
        margin-top: 20px;
        margin-bottom: 30px;
        .el-select{
            // width: 20%;
        }
        .searchFormItem{
            display: flex;
            width: 100%;
            justify-content: left;
            margin-bottom: 40px;
            .title{
                width: 20%;
                font-size: 14px;
                font-weight: 600;
                color:#7f7f7f;
            }
            .el-input{
                width: 58%;
                margin-left: 2%;
            }
        }
    }
    .submitBox {
        width: 90%;
        display: flex;
        justify-content: flex-end;
        button{
            height:35px;
            margin-right: 100px;
        }
        .exampleBtn{
            width: 180px;
            background-color: #1890FF;
        }
        .clearBtn, .submitBtb{
            width: 120px;
        }
    }

}

.tableBox {
    overflow-x: auto;
    margin-top: 30px;

    .title {
        font-size: 20px;
        margin-bottom: 40px;
    }
    .pagination{
        margin-top: 20px;
    }
}


</style>