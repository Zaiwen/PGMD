package com.example.swinedatebaseproject.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * &#064;TableName  Project
 */

@TableName(value ="project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements Serializable {

    @TableId(value = "Bio_Project")
    private String project;

    @TableField(value = "Phenotypes")
    private String phenotypes;

    @TableField(value = "Phenotypes_info")
    private String phenotypesInfo;

    @TableField(value = "Experimental_design")
    private String experimentalDesign;

    @TableField(value = "Country")
    private String country;

    @TableField(value = "Category")
    private String category;

    @TableField(value = "Breed")
    private String breed;

    @TableField(value = "GrowthStage")
    private String growthStage;

    @TableField(value = "IsolationLocation")
    private String isolationLocation;

    @TableField(value = "Age")
    private String age;

    @TableField(value = "Weight")
    private String weight;

    @TableField(value = "Sex")
    private String sex;

    @TableField(value = "Title")
    private String title;

    @TableField(value = "Description")
    private String description;

    @TableField(value = "Link")
    private String link;

    @TableField(value = "SequencingTool")
    private String sequencingTool;

    @TableField(value = "ReleaseDate")
    private String releaseDate;

    @TableField(value = "Assay_type")
    private String assayType;

//    @TableField(value = "sample_number")
//    private Integer sampleNumber;


//    @TableField(value = "Assay_type")
//    private String layout;


//    @TableField(value = "publication")
//    private String publication;

//    @TableField(value = "platform")
//    private String platform;
//
//    @TableField(value = "nutritionalComposition")
//    private String nutritionalComposition;
//
//    @TableField(value = "feedComposition")
//    private String feedComposition;
//
//    @TableField(value = "researchTopic")
//    private String researchTopic;


    @Serial
    private static final long serialVersionUID = 1L;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (!project.equals(project.project)) return false;
        if (phenotypes != null ? !phenotypes.equals(project.phenotypes) : project.phenotypes != null) return false;
        if (experimentalDesign != null ? !experimentalDesign.equals(project.experimentalDesign) : project.experimentalDesign != null)
            return false;
        if (country != null ? !country.equals(project.country) : project.country != null) return false;
        if (category != null ? !category.equals(project.category) : project.category != null) return false;
        if (breed != null ? !breed.equals(project.breed) : project.breed != null) return false;
        if (growthStage != null ? !growthStage.equals(project.growthStage) : project.growthStage != null) return false;
        if (isolationLocation != null ? !isolationLocation.equals(project.isolationLocation) : project.isolationLocation != null)
            return false;
        if (age != null ? !age.equals(project.age) : project.age != null) return false;
        if (weight != null ? !weight.equals(project.weight) : project.weight != null) return false;
        if (sex != null ? !sex.equals(project.sex) : project.sex != null) return false;
        if (sequencingTool != null ? !sequencingTool.equals(project.sequencingTool) : project.sequencingTool != null)
            return false;
//        if (sampleNumber != null ? !sampleNumber.equals(project.sampleNumber) : project.sampleNumber != null)
//            return false;
//        if (layout != null ? !layout.equals(project.layout) : project.layout != null) return false;
        if (title != null ? !title.equals(project.title) : project.title != null) return false;
        if (link != null ? !link.equals(project.link) : project.link != null) return false;
        return releaseDate != null ? releaseDate.equals(project.releaseDate) : project.releaseDate == null;
    }

    @Override
    public int hashCode() {
        int result = project != null ? project.hashCode() : 0;
        result = 31 * result + (phenotypes != null ? phenotypes.hashCode() : 0);
        result = 31 * result + (experimentalDesign != null ? experimentalDesign.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (breed != null ? breed.hashCode() : 0);
        result = 31 * result + (growthStage != null ? growthStage.hashCode() : 0);
        result = 31 * result + (isolationLocation != null ? isolationLocation.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (sequencingTool != null ? sequencingTool.hashCode() : 0);
//        result = 31 * result + (sampleNumber != null ? sampleNumber.hashCode() : 0);
//        result = 31 * result + (layout != null ? layout.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Project{");
        sb.append("project='").append(project).append('\'');
        sb.append(", phenotypes='").append(phenotypes).append('\'');
        sb.append(", experimentalDesign='").append(experimentalDesign).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", category='").append(category).append('\'');
        sb.append(", breed='").append(breed).append('\'');
        sb.append(", growthStage='").append(growthStage).append('\'');
        sb.append(", isolationLocation='").append(isolationLocation).append('\'');
        sb.append(", age=").append(age);
        sb.append(", weight='").append(weight).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", sequencingTool='").append(sequencingTool).append('\'');
//        sb.append(", sampleNumber=").append(sampleNumber);
//        sb.append(", layout='").append(layout).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", link='").append(link).append('\'');
        sb.append(", releaseDate='").append(releaseDate).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
