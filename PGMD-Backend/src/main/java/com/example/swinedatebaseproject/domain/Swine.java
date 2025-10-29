package com.example.swinedatebaseproject.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor


@TableName(value = "swine")
public class Swine implements Serializable {

    @TableId(value = "swine_index")
    private Integer swineIndex;

    @TableField(value = "swine_id")
    private String swineTag;

    @TableField(value = "col_id")
    private String colId;

    @TableField(value = "sex")
    private String sex;

    @TableField(value = "ages")
    private Integer ages;

    @TableField(value = "weights")
    private String weights;

    @TableField(value = "assay_station")
    private String assayStation;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Swine swine = (Swine) o;

        if (!swineIndex.equals(swine.swineIndex)) return false;
        if (!swineTag.equals(swine.swineTag)) return false;
        if (!colId.equals(swine.colId)) return false;
        if (!sex.equals(swine.sex)) return false;
        if (!ages.equals(swine.ages)) return false;
        if (!weights.equals(swine.weights)) return false;
        return assayStation.equals(swine.assayStation);
    }

    @Override
    public int hashCode() {

        int result = swineIndex.hashCode();
        result = 31 * result + swineTag.hashCode();
        result = 31 * result + colId.hashCode();
        result = 31 * result + sex.hashCode();
        result = 31 * result + ages.hashCode();
        result = 31 * result + weights.hashCode();
        result = 31 * result + assayStation.hashCode();
        return result;
    }

    @Override
    public String toString() {

        String sb = getClass().getSimpleName() + "Swine{" +
                "Hash = " + hashCode() +
                "swineIndex=" + swineIndex +
                ", swineTag='" + swineTag + '\'' +
                ", colId='" + colId + '\'' +
                ", sex='" + sex + '\'' +
                ", ages=" + ages +
                ", weights='" + weights + '\'' +
                ", assayStation='" + assayStation + '\'' +
                ", serialVersionUID=" + serialVersionUID +'\''+
                '}';
        return sb;
    }
}
