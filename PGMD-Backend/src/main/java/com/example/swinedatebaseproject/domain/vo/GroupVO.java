package com.example.swinedatebaseproject.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupVO {
    // 微生物名称
    private String name;
    // 微生物的分类
    private String specific_taxonomy;
    // 微生物分组
    private String subGroup;

    private String level;
    // 微生物在组内的占比
    private float value;

    private Integer count;
}
