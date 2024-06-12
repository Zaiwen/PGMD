package com.example.swinedatebaseproject.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @TableName microbial
 */
@TableName(value ="microbial")
@Data
public class Microbial implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value = "microbial_name")
    private String microbialName;

    @TableField(value = "specific_taxonomy")
    private String specificTaxonomy;

    @TableField(value = "level")
    private String level;

    @TableField(value = "count")
    private Integer count;

    @TableField(value = "abundance")
    private Float abundance;

    @TableField(value = "Runs")
    private String runs;

    @TableField(value = "BioProject")
    private String bioProject;

    @Serial
    private static final long serialVersionUID = 1L;

    // Constructors, getters, and setters
    public Microbial() {
    }

    public Microbial(String microbialName, String specificTaxonomy, String level, Integer count, Float abundance, String runs) {
        this.microbialName = microbialName;
        this.specificTaxonomy = specificTaxonomy;
        this.level = level;
        this.count = count;
        this.abundance = abundance;
        this.runs = runs;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMicrobialName() {
        return microbialName;
    }

    public void setMicrobialName(String microbialName) {
        this.microbialName = microbialName;
    }

    public String getSpecificTaxonomy() {
        return specificTaxonomy;
    }

    public void setSpecificTaxonomy(String specificTaxonomy) {
        this.specificTaxonomy = specificTaxonomy;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Float getAbundance() {
        return abundance;
    }

    public void setAbundance(Float abundance) {
        this.abundance = abundance;
    }

    public String getRuns() {
        return runs;
    }

    public void setRuns(String runs) {
        this.runs = runs;
    }

    public String getBioProject() {
        return bioProject;
    }

    public void setBioProject(String bioProject) {
        this.bioProject = bioProject;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Microbial microbial = (Microbial) o;

        if (!id.equals(microbial.id)) return false;
        if (!Objects.equals(microbialName, microbial.microbialName))
            return false;
        if (!Objects.equals(specificTaxonomy, microbial.specificTaxonomy))
            return false;
        if (!Objects.equals(level, microbial.level)) return false;
        if (!Objects.equals(count, microbial.count)) return false;
        if (!Objects.equals(abundance, microbial.abundance)) return false;
        if(!Objects.equals(runs,microbial.runs)) return false;
        return Objects.equals(bioProject, microbial.bioProject);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (microbialName != null ? microbialName.hashCode() : 0);
        result = 31 * result + (specificTaxonomy != null ? specificTaxonomy.hashCode() : 0);
        result = 31 * result + (level != null ? level.hashCode() : 0);
        result = 31 * result + (count != null ? count.hashCode() : 0);
        result = 31 * result + (abundance != null ? abundance.hashCode() : 0);
        result = 31 * result + (runs != null ? runs.hashCode() : 0);
        result= 31 * result+(bioProject!=null?bioProject.hashCode():0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Microbial{");
        sb.append("id=").append(id);
        sb.append(", microbialName='").append(microbialName).append('\'');
        sb.append(", specificTaxonomy='").append(specificTaxonomy).append('\'');
        sb.append(", level='").append(level).append('\'');
        sb.append(", count=").append(count);
        sb.append(", abundance=").append(abundance);
        sb.append(", runs='").append(runs).append('\'');
        sb.append(", bioproject='").append(bioProject).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
