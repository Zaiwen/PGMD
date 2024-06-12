package com.example.swinedatebaseproject.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @TableName sample_runs
 */
@TableName(value ="sample_runs")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Samples implements Serializable {

    @TableId(value = "Runs")
    private String runs;


    @TableField(value = "Bio_Project")
    private String bioProject;

    @TableField(value = "Bio_Sample")
    private String bioSample;


    @TableField(value = "Experimental_Design")
    private String experimental_Design;


    @TableField(value = "Group")
    private String Group;


    @TableField(value = "Sub_Group")
    private String subGroup;


    @TableField(value = "Country")
    private String country;


    @TableField(value = "Category")
    private String category;


    @TableField(value = "Breed")
    private String breed;


    @TableField(value = "Growth_Stage")
    private String growth_Stage;


    @TableField(value = "Isolation_Location")
    private String isolation_Location;


    @TableField(value = "Age")
    private String age;


    @TableField(value = "Weight")
    private String weight;


    @TableField(value = "Sex")
    private String sex;

    // 图书布局字段名调整为Library_Layout
    @TableField(value = "Library_Layout")
    private String library_Layout;


    @TableField(value = "Platform")
    private String platform;


    @TableField(value = "Release_Date")
    private String release_Date;



    @Serial
    private static final long serialVersionUID = 1L;



    // equals方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Samples samples = (Samples) o;
        return Objects.equals(runs, samples.runs) &&
                Objects.equals(bioProject, samples.bioProject) &&
                Objects.equals(experimental_Design, samples.experimental_Design) &&
                Objects.equals(Group, samples.Group) &&
                Objects.equals(subGroup, samples.subGroup) &&
                Objects.equals(country, samples.country) &&
                Objects.equals(category, samples.category) &&
                Objects.equals(breed, samples.breed) &&
                Objects.equals(growth_Stage, samples.growth_Stage) &&
                Objects.equals(isolation_Location, samples.isolation_Location) &&
                Objects.equals(age, samples.age) &&
                Objects.equals(weight, samples.weight) &&
                Objects.equals(sex, samples.sex) &&
                Objects.equals(library_Layout, samples.library_Layout) &&
                Objects.equals(platform, samples.platform) &&
                Objects.equals(release_Date, samples.release_Date);
    }

    // hashCode方法
    @Override
    public int hashCode() {
        return Objects.hash(runs, bioProject, experimental_Design, Group, subGroup, country, category, breed, growth_Stage, isolation_Location, age, weight, sex, library_Layout, platform, release_Date);
    }

    // toString方法
    @Override
    public String toString() {
        return "Samples{" +
                "runs='" + runs + '\'' +
                ", bioProject='" + bioProject + '\'' +
                ", experimental_Design='" + experimental_Design + '\'' +
                ", Group='" + Group + '\'' +
                ", subGroup='" + subGroup + '\'' +
                ", country='" + country + '\'' +
                ", category='" + category + '\'' +
                ", breed='" + breed + '\'' +
                ", growth_Stage='" + growth_Stage + '\'' +
                ", isolation_Location='" + isolation_Location + '\'' +
                ", age='" + age + '\'' +
                ", weight='" + weight + '\'' +
                ", sex='" + sex + '\'' +
                ", library_Layout='" + library_Layout + '\'' +
                ", platform='" + platform + '\'' +
                ", release_Date='" + release_Date + '\'' +
                '}';
    }
}
