package com.example.swinedatebaseproject.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowDataVO {
    private int literatures; // 文献总数
    private int projects;    // 项目总数
    private int runs;        // 运行总数
    private int microbiome;  // 微生物名字不重复的总数

}
