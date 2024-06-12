package com.example.swinedatebaseproject.domain.dto;

import lombok.Data;

import java.util.stream.Stream;

@Data
public class AnimalData {
    private String breed;
    private String growthStage;
    private String isolationLocation;
    private String phenotypes;
    private String experimentalDesign;

    /**
     * 检查所有字段是否都为null或都为空。
     * 该方法使用Java 8的Stream API和Objects工具类来简化对多个字段的非空和非空字符串的检查。
     *
     * @return boolean 如果所有字段都不为null且不为空，则返回true；否则返回false。
     */
    public boolean isAllFieldsNotNullOrEmpty() {
        // 使用Java 8的Stream API 和 Objects工具类来简化检查
        return Stream.of(breed, growthStage, isolationLocation, phenotypes, experimentalDesign)
                .anyMatch(s -> s != null && !s.isEmpty());
    }

}