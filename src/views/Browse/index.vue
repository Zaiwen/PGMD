<template>
  <div class="outBox">
    <div class="chooseBox">
      <div class="chooseBtns">
        <button
          class="samplesBtn"
          @click="showSamples"
          :style="{
            color: pageStatus == 'Samples' ? '#1C683D' : '#000',
            borderColor: pageStatus == 'Samples' ? '#007E37' : '#A6A6A6',
          }"
        >
          Samples/Runs
        </button>
        <button
          class="projectsBtn"
          @click="showProjects"
          :style="{
            color: pageStatus == 'Projects' ? '#1C683D' : '#000',
            borderColor: pageStatus == 'Projects' ? '#007E37' : '#A6A6A6',
          }"
        >
          Projects
        </button>
      </div>
      <div class="country-select-container">
        <template v-if="pageStatus == 'Projects'">
          <span class="title">Country</span>
          <el-checkbox-group v-model="choosingCountryList">
            <el-checkbox
              v-for="(item, index) in countryList.slice(0, 4)"
              :key="index"
              :label="item"
            >
            </el-checkbox>
          </el-checkbox-group>
          <el-select
            placeholder="other countries"
            v-model="choosingCountryList"
            multiple
            collapse-tags
            collapse-tags-tooltip
            clearable
            :max-collapse-tags="1"
          >
            <el-option
              v-for="(item, index) in countryList.slice(4)"
              :key="index"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </template>
      </div>
      <div class="select-groups">
        <div class="selectGrp" v-for="(item, index) in titles" :key="index">
          <div class="title">
            <span>{{ item }}</span>
          </div>
          <div class="division"></div>
          <el-select
            clearable
            v-model="searchForm[titleMap[item]].breed"
            placeholder="Breed"
          >
            <el-option
              v-for="(item, index) in allSearchForm[titleMap[item]].breed"
              :key="index"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
          <div class="division"></div>
          <el-select
            clearable
            v-model="searchForm[titleMap[item]].growthStage"
            placeholder="Growth Stage"
          >
            <el-option
              v-for="(item, index) in allSearchForm[titleMap[item]].growthStage"
              :key="index"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
          <div class="division"></div>
          <el-select
            clearable
            v-model="searchForm[titleMap[item]].isolationLocation"
            placeholder="Isolation Location"
          >
            <el-option
              v-for="(item, index) in allSearchForm[titleMap[item]]
                .isolationLocation"
              :key="index"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
          <div class="division"></div>
          <el-select
            v-if="pageStatus == 'Projects'"
            clearable
            v-model="privateSearchForm[titleMap[item]].phenotypes"
            placeholder="Phenotypes"
          >
            <el-option
              v-for="(item, index) in allSearchForm[titleMap[item]].phenotypes"
              :key="index"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
          <el-select
            v-else
            clearable
            v-model="privateSearchForm[titleMap[item]].experimentalDesign"
            placeholder="Experimental-Design"
          >
            <el-option
              v-for="(item, index) in allSearchForm[titleMap[item]]
                .experimentalDesign"
              :key="index"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
        </div>
      </div>
      <div class="submitBox">
        <el-button @click="submitForm" :loading="searchLoading"
          >Submit</el-button
        >
      </div>
    </div>
    <div class="tableBox" v-if="tableData.length">
      <div class="title">{{ pageStatus }}</div>
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
          v-for="(item, index) in pageStatus == 'Projects'
            ? projectsTableColumns
            : samplesTableColumns"
          :key="index"
          :label="item.label"
          :width="120"
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
        :current-page="currentPageNum"
      />
    </div>
    <template v-if="JSON.stringify(choosingProject) != '{}' && pageStatus==='Projects'">
      <ProjectDetail :choosingProject="choosingProject" />
    </template>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import {
  searchProjects,
  searchSamples,
} from "../../api/Browse/index";
import { ElMessage } from "element-plus";
import { boar, sow, piglet, growingFinishingPig,countries } from './selectData.js';
import ProjectDetail from '../../components/ProjectDetail/index.vue'

// 处理空白单元格
const emptyHandler = ({row,column}) => {
  row[column.property] = row[column.property] || ''
}

// 搜索部分 start

//控制查询的标题
const titles = ref(["Boar", "Sow", "Piglet", "Growing-finishing Pigs"]);

//标题与变量名映射
const titleMap = ref({
  Boar: "boar",
  Sow: "sow",
  Piglet: "piglet",
  "Growing-finishing Pigs": "growingFinishingPigs",
});

//国家列表
const countryList = ref(countries);

//选择的国家列表
const choosingCountryList = ref([]);

//供选择的sample与project共用搜索条件
const allSearchForm = ref({
  boar: {
    breed: boar.breed,
    growthStage: boar.growthStage,
    isolationLocation: boar.isolationLocation,
    phenotypes: boar.phenotypes,
    experimentalDesign: boar.experimentalDesign,
  },
  sow: {
    breed: sow.breed,
    growthStage: sow.growthStage,
    isolationLocation: sow.isolationLocation,
    phenotypes: sow.phenotypes,
    experimentalDesign: sow.experimentalDesign,
  },
  piglet: {
    breed: piglet.breed,
    growthStage: piglet.growthStage,
    isolationLocation: piglet.isolationLocation,
    phenotypes: piglet.phenotypes,
    experimentalDesign: piglet.experimentalDesign,
  },
  growingFinishingPigs: {
    breed: growingFinishingPig.breed,
    growthStage: growingFinishingPig.growthStage,
    isolationLocation: growingFinishingPig.isolationLocation,
    phenotypes: growingFinishingPig.phenotypes,
    experimentalDesign: growingFinishingPig.experimentalDesign,
  },
});

// 公用搜索表单
const searchForm = ref({
  boar: {
    breed: "",
    growthStage: "",
    isolationLocation: "",
  },
  sow: {
    breed: "",
    growthStage: "",
    isolationLocation: "",
  },
  piglet: {
    breed: "",
    growthStage: "",
    isolationLocation: "",
  },
  growingFinishingPigs: {
    breed: "",
    growthStage: "",
    isolationLocation: "",
  },
});

// 特有搜索表单
const privateSearchForm = ref({
  boar: {
    phenotypes: "",
    experimentalDesign: "",
  },
  sow: {
    phenotypes: "",
    experimentalDesign: "",
  },
  piglet: {
    phenotypes: "",
    experimentalDesign: "",
  },
  growingFinishingPigs: {
    phenotypes: "",
    experimentalDesign: "",
  },
});

// 根据当前选择的查找方式修改提交表单
const changeForm = () => {
  //是projects则在表单中加入国家，添加experimentalDesign，反之亦然
  if (pageStatus.value == "Projects") {
    for (let key in searchForm.value) {
      searchForm.value[key].phenotypes =
        privateSearchForm.value[key].phenotypes;
    }
    searchForm.value.countries = choosingCountryList.value;
  } else {
    for (let key in searchForm.value) {
      searchForm.value[key].experimentalDesign =
        privateSearchForm.value[key].experimentalDesign;
    }
  }
};

// 搜索成功后删除提交表单中的私有表单项与国家
const deletePrivateSearchForm = () => {
  for (let key in searchForm.value) {
    delete searchForm.value[key].phenotypes;
    delete searchForm.value[key].experimentalDesign;
  }
  delete searchForm.value?.countries;
};

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

// 搜索loading
const searchLoading = ref(false);

//提交表单
const submitForm = () => {
  // 判断searchForm是否为空
  // for (let key in searchForm.value) {
  //   if (
  //     searchForm.value[key].breed ||
  //     searchForm.value[key].growthStage ||
  //     searchForm.value[key].isolationLocation ||
  //     searchForm.value[key].phenotypes ||
  //     searchForm.value[key].experimentalDesign
  //   ) {
  //     break;
  //   }
  // }

  // 修改表单
  changeForm()
  searchLoading.value = true
  if(pageStatus.value == "Samples"){
    searchSamples(searchForm.value)
    .then(res=>{
      console.log(res)
        if(res.code != 200) return ElMessage.error('search error')
        tableData.value=sampleData.value=res.data
        handleNumber(tableData.value)
        // 判断是否为空
        if(tableData.value.length==0){
            ElMessage.warning('no suitable data!')
        }else{
            ElMessage.success('search success!')
        }   
    }).catch(err=>{
        console.error('request error!',err)
    }).finally(()=>{
        // 搜索成功后删除提交表单中的私有表单项与国家
        deletePrivateSearchForm()
        searchLoading.value = false
    })
    return null
  }else{
    searchProjects(searchForm.value)
    .then(res=>{
        if(res?.code != 200) return ElMessage.error('search error!')
        tableData.value=projectData.value=res.data
        handleNumber(tableData.value)
          // 判断是否为空
          if(tableData.value.length==0){
            ElMessage.warning('no suitable data!')
        }else{
            ElMessage.success('search success!')
        }        
    }).catch(err=>{
        console.error('request error!',err)
    }).finally(()=>{
        // 搜索成功后删除提交表单中的私有表单项与国家
        deletePrivateSearchForm()
        searchLoading.value = false
    })
    return null
  }
}

//控制samples or Projects
const pageStatus = ref("Projects");

// 监听pageStatus切换tableData
watch(pageStatus, (newVal) => {
  if (newVal == "Projects") {
    tableData.value = projectData.value;
  } else {
    tableData.value = sampleData.value;
  }
});


//显示samples
const showSamples = () => {
  pageStatus.value = "Samples";
};

//显示projects
const showProjects = () => {
  pageStatus.value = "Projects";
};

// 搜索部分 end

//表格部分 start

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
        label: 'Sample Number'
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

//表格数据
const tableData = ref([]);

const sampleData = ref([]);

const projectData = ref([]);


// 表格分页
const currentPageNum = ref(1)
const pageSizeNum = ref(5)
const totalNum = ref(0)

// 分页事件
const handleSizeChange = (val) => {
  pageSizeNum.value = val;
};

const handleCurrentChange = (val) => {
  currentPageNum.value = val;
};

// 监视表格数据变化
watch(tableData, (newVal) => {
  totalNum.value = newVal.length;
  currentPageNum.value = 1;
});

// 点击表格行显示详情
const showDetail = (row) => {
    if(pageStatus.value == 'Samples'){
        return
    }
    choosingProject.value = row
}

// 表格部分 end

const choosingProject=ref({})

</script>

<style lang="less">
.el-popper.is-customized {
  padding: 6px 12px;
  background: linear-gradient(90deg, #1c683d, rgb(192, 221, 104));
  color: #fff;
  font-size: 18px;
  z-index: 999;
}
.el-popper.is-customized .el-popper__arrow::before {
    background: linear-gradient(45deg, #699e5b, #80af64);
    right: 0;
}
</style>

<style scoped lang="less">
// 表格样式
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

.chooseBox {
  width: 100%;
  background-color: #f7f7f7;
  border-radius: 50px;
  height: 500px;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;

  .chooseBtns {
    width: 100%;
    height: 30px;
    margin-top: 41px;
    padding-left: 73px;

    .samplesBtn,
    .projectsBtn {
      border: none;
      background-color: transparent;
      height: 37px;
      font-size: 24px;
      border-bottom: 3px solid #a6a6a6;
      transition: all 0.3s;

      &:hover {
        color: #1c683d !important;
      }
    }

    .projectsBtn {
      padding-left: 20px;
    }
  }
}

.country-select-container {
  .title{
    line-height: 32px;
    vertical-align: middle;
  }
  display: flex;
  flex-wrap: wrap;
  width: 70%;
  justify-content: space-between;
  align-content: center;

  .el-select {
    :deep(.el-input__inner) {
      width: 180px;
    }
  }

  :deep(.el-checkbox-group) {
    width: 70%;
    display: flex;
    justify-content: space-around;
  }

  :deep(.el-input__inner) {
    background-color: #56a87a;
    box-shadow: none;
    width: 128px;

    &::placeholder {
      color: #fff;
    }
  }

  :deep(.el-select__caret) {
    color: #fff;
  }

  span {
    color: rgba(0, 0, 0, 0.8);
    font-size: 16px;
  }
}

.select-groups {
  display: flex;
  width: 90%;
  justify-content: space-around;

  .title {
    padding: 5px 0 5px 10px;
    background-color: #fff;
    color: rgba(0, 0, 0, 0.8);
  }

  .selectGrp {
    width: 16%;
  }

  .division {
    height: 8px;
    background-color: #bab6b6;
  }

  :deep(.el-select) {
    width: 100%;
  }

  :deep(.el-input__inner) {
    box-shadow: none;
    width: 100%;

    &::placeholder {
      color: #8e8e8e;
    }
  }
}

.submitBox {
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 20px;

  .el-button {
    height: 40px;
    width: 160px;
    border-radius: 0;
    background-color: #1c683d;
    color: #fff;
    border: none;
    font-size: 20px;
  }
}

.tableBox {
  overflow-x: auto;
  margin-top: 30px;

  .title {
    font-size: 20px;
    margin-bottom: 40px;
  }

  .pagination {
    margin-top: 20px;
  }
}

.overViewBox {
  margin-top: 50px;

  .headerTitle {
    width: 100%;
    text-align: center;
    font-size: 24px;
    font-weight: 600;
    color: rgba(0, 0, 0, 0.8);
    a{
        color: rgba(0, 0, 0, 0.8);
        text-decoration: none;
    }
  }
  .download-link{
    color:rgba(0, 0, 0, 0.6);
    text-decoration:underline;
    cursor: pointer;
  }
}

.nutrientBox{
    margin: 50px 0 50px 0;
    .headerTitle {
    width: 100%;
    text-align: center;
    margin-bottom: 50px;
    font-size: 24px;
    font-weight: 600;
    color: rgba(0, 0, 0, 0.8);
  }
}

.projectsBox {
  width: 80%;
  margin-left: 15%;
  .MicrobialBox{
    display: flex;
    flex-direction: column;
    align-items: center;
    .radio{
      margin-top: 50px;
      display: flex;
      align-items: center;
    }
  }
}

.overViewMain {
  width: 100%;
  margin-top: 30px;
  padding: 20px 30px;
  background-color: #f7f7f7;
  border-radius: 50px;

  .item {
    letter-spacing: 0;
    line-height: 25px;

    .title {
      font-size: 16px;

      font-weight: 600;
      color: rgba(0, 0, 0, 0.8);
    }
  }
}
</style>
