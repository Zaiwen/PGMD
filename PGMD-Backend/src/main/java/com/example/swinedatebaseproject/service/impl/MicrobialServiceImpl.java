package com.example.swinedatebaseproject.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.swinedatebaseproject.domain.Microbial;
import com.example.swinedatebaseproject.domain.vo.CountLevelVO;
import com.example.swinedatebaseproject.domain.vo.GroupVO;
import com.example.swinedatebaseproject.domain.vo.RunsAndCount;
import com.example.swinedatebaseproject.mapper.MicrobialMapper;
import com.example.swinedatebaseproject.mapper.SamplesMapper;
import com.example.swinedatebaseproject.response.ResponseResult;
import com.example.swinedatebaseproject.service.MicrobialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
* @author s1mple
* @description 针对表【cds】的数据库操作Service实现
* @createDate 2023-02-26 00:20:22
*/
@Service
public class MicrobialServiceImpl extends ServiceImpl<MicrobialMapper, Microbial>
    implements MicrobialService{

    @Autowired
    MicrobialMapper microbialMapper;

    @Autowired
    SamplesMapper samplesMapper;

    @Override
    public ResponseResult countLevelByName() {

        List<CountLevelVO> list =microbialMapper.countLevelByName();
        return ResponseResult.success(list);
    }




    @Override
    // 加个缓存备注，毕竟查一下还是要花费很久的
    @Cacheable(value = "countGroup", key = "#name")
    public ResponseResult countGroup(String name) {
        System.out.println("Get into method: " + name);

        // 根据生物项目名称查询所有关联的runs 相同的group整合为一个runs
        List<String> groupList = samplesMapper.getGroupsByBioProject(name);

        // 去掉重复的group值
        List<String> groups = groupList.stream()
                .distinct()
                .collect(Collectors.toList());

        // 如果项目一个组都没有提前返回
        if (groups == null || groups.isEmpty()) {
            System.out.println("No groups found for the bio project: " + name);
            return ResponseResult.success(200, "No groups found for the bio project: " + name);
        }
        System.out.println("groups.size " + groups.size());

        List<GroupVO> groupVOList = new ArrayList<>();
        // 循环组
        for (String group : groups) {
            List<RunsAndCount> runs;
            try {
                System.out.println("Querying runs for group: " + group);
                // 查组内所有的样本和其count数
                runs = microbialMapper.selectRunsByGroupsAndLevels(group,name);
                if (runs == null || runs.isEmpty()) {
                    System.out.println("No runs found for group: " + group);
                    continue; // 跳过当前group
                }

                // 首先，按runs分组，并收集每个组的RunsAndCount列表
                Map<String, List<RunsAndCount>> groupedRuns = runs.stream()
                        .collect(Collectors.groupingBy(RunsAndCount::getRuns, LinkedHashMap::new, Collectors.toList()));

                // 然后，遍历分组后的Map，并合并每个组的RunsAndCount对象的count
                List<RunsAndCount> uniqueRunsList = groupedRuns.entrySet().stream()
                        .map(entry -> {
                            int totalCount = entry.getValue().stream()
                                    .mapToInt(RunsAndCount::getCount)
                                    .sum();
                            return new RunsAndCount(entry.getKey(), totalCount);
                        })
                        .collect(Collectors.toList());

//                List<RunsAndCount> uniqueRunsList = new ArrayList<>(runsCountMap.values());
                // 计算组内所有的样本的count，作为总分母
                int totalCount = 0;
                for (RunsAndCount item : uniqueRunsList) {
                    totalCount += item.getCount();
                }


                // 循环runs
                for (RunsAndCount oneRun : uniqueRunsList) {
                    try {
                        String run = oneRun.getRuns();
                        List<GroupVO> runsList = microbialMapper.getSpecificTaxonomyByRuns(run,group,name);
                        float runCountFloat = (float) totalCount;
                        AtomicInteger totalRunsAtomic = new AtomicInteger(0);
                        runsList.forEach(item -> {
                            if (oneRun.getCount() > 0) {
                                totalRunsAtomic.addAndGet(item.getCount());
                                float percent = (float) item.getCount() / runCountFloat * 100;
                                item.setValue(percent);
                                item.setSubGroup(group);
                            }
                        });
                        groupVOList.addAll(runsList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                // 异常处理
                return ResponseResult.error(500, "Failed to query runs for group: " + group + ", error: " + e.getMessage());
            }
        }

        // 返回计算结果
        return ResponseResult.success(groupVOList);
    }

    @Override
    public ResponseResult searchMicrobialByProject(String name) {
        List<Microbial> list = microbialMapper.getMicrobialByBioProject(name);
        return ResponseResult.success(list);
    }

    @Override
    public ResponseResult searchMicrobial(String name) {
        List<Microbial> list = microbialMapper.getMicrobial(name);
        return ResponseResult.success(list);
    }


}




