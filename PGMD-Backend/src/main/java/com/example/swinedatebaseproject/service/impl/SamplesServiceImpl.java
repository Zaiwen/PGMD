package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.domain.Project;
import com.example.swinedatebaseproject.domain.Samples;
import com.example.swinedatebaseproject.domain.dto.AnimalData;
import com.example.swinedatebaseproject.domain.dto.SearchFormDTO;
import com.example.swinedatebaseproject.domain.vo.DataCountVO;
import com.example.swinedatebaseproject.domain.vo.LefseVO;
import com.example.swinedatebaseproject.domain.vo.MicrobialVO;
import com.example.swinedatebaseproject.mapper.SamplesMapper;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.service.SamplesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author s1mple
* @description 针对表【Samples】的数据库操作Service实现
* @createDate 2023-02-26 00:20:22
*/
@Service
public class SamplesServiceImpl extends ServiceImpl<SamplesMapper, Samples>
    implements SamplesService {

    @Autowired
    private SamplesMapper samplesMapper;

    @Override
    public ResponseResult searchLefse(String name, Integer currentPage,Integer pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<LefseVO> lefsseDataBySample = samplesMapper.findLefseDataBySample(name, offset, pageSize);
        return ResponseResult.success(lefsseDataBySample);


    }

    @Override
    public ResponseResult searchMicrobial(String name, Integer currentPage, Integer pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<MicrobialVO> microbialDataBySample = samplesMapper.findMicrobialDataBySample(name, offset, pageSize);
        return ResponseResult.success(microbialDataBySample);
    }

//    @Override
//    public ResponseResult countSample(String bioSample) {
////        int lefseCount = samplesMapper.getLefseCountByBioSample(bioSample);
//        int nutrientCompositionCount = samplesMapper.getNutrientCompositionCountByBioSample(bioSample);
////        int projectCount = samplesMapper.getProjectCountByBioSample(bioSample);
//        int microbialCount = samplesMapper.getMicrobialCountByBioSample(bioSample);
//        DataCountVO dataCountVO = new DataCountVO(0, nutrientCompositionCount, microbialCount, 0,0);
//        return ResponseResult.success(dataCountVO);
//    }



    @Override
    public ResponseResult searchSample(SearchFormDTO searchFormDTO) {
        List<Samples> list = new ArrayList<>();
        // 循环处理不同类型的动物数据
        Map<String, AnimalData> animalDataMap = new HashMap<>();
        if (searchFormDTO.getBoar() != null && searchFormDTO.getBoar().isAllFieldsNotNullOrEmpty()){
            animalDataMap.put("Boar", searchFormDTO.getBoar());
        }
        if (searchFormDTO.getPiglet() != null && searchFormDTO.getPiglet().isAllFieldsNotNullOrEmpty()) {
            animalDataMap.put("Piglets", searchFormDTO.getPiglet());
        }

        if (searchFormDTO.getSow() != null && searchFormDTO.getSow().isAllFieldsNotNullOrEmpty()) {
            animalDataMap.put("Sow", searchFormDTO.getSow());
        }


        if (searchFormDTO.getGrowingFinishingPigs() != null && searchFormDTO.getGrowingFinishingPigs().isAllFieldsNotNullOrEmpty()) {
            animalDataMap.put("Growing-finishing Pigs", searchFormDTO.getGrowingFinishingPigs());
        }

        for (Map.Entry<String, AnimalData> entry : animalDataMap.entrySet()) {
            AnimalData animalData = entry.getValue();
            if (animalData != null) {

                list.addAll(processAnimalData(entry.getKey(), animalData));

            }
        }


        return ResponseResult.success(list);
    }

    private List<Samples> processAnimalData(String type, AnimalData animalData) {
        String breed = animalData.getBreed();
        String growthStage = animalData.getGrowthStage();
        String isolationLocation = animalData.getIsolationLocation();
        String experimentalDesign = animalData.getExperimentalDesign();
        return samplesMapper.searchSample(type, breed, growthStage, isolationLocation, experimentalDesign);
    }




}




