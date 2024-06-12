package com.example.swinedatebaseproject.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

@TableName(value = "metabolism")
public class Metabolism implements Serializable {
    @TableId(value = "metabolism_index")
    private Integer metabolismId;

    @TableField(value = "metabolism_name")
    private String metabolismName;

    @TableField(value = "Fold_change")
    private float foldChange;

    @TableField(value = "P_value")
    private float pvalue;

    @TableField(value = "VIP")
    private float vip;

    @TableField(value = "Sample")
    private String sample;

    @TableField(value = "identification")
    private String identification;

    @TableField(value = "metabolism_time")
    private Integer time;

    @TableField(value = "metabolome_difference")
    private Integer metabolismdifference;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Metabolism that = (Metabolism) o;

        if (Float.compare(that.foldChange, foldChange) != 0) return false;
        if (Float.compare(that.pvalue, pvalue) != 0) return false;
        if (Float.compare(that.vip, vip) != 0) return false;
        if (!metabolismId.equals(that.metabolismId)) return false;
        if (!metabolismName.equals(that.metabolismName)) return false;
        if (!sample.equals(that.sample)) return false;
        if (!identification.equals(that.identification)) return false;
        if (!time.equals(that.time)) return false;
        return metabolismdifference.equals(that.metabolismdifference);
    }

    @Override
    public int hashCode() {
        int result = metabolismId.hashCode();
        result = 31 * result + metabolismName.hashCode();
        result = 31 * result + (foldChange != +0.0f ? Float.floatToIntBits(foldChange) : 0);
        result = 31 * result + (pvalue != +0.0f ? Float.floatToIntBits(pvalue) : 0);
        result = 31 * result + (vip != +0.0f ? Float.floatToIntBits(vip) : 0);
        result = 31 * result + sample.hashCode();
        result = 31 * result + identification.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + metabolismdifference.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
                "Metabolism{" +
                "metabolismInd=" + metabolismId +
                ", metabolismName='" + metabolismName + '\'' +
                ", foldChange=" + foldChange +
                ", pValue=" + pvalue +
                ", vip=" + vip +
                ", sample='" + sample + '\'' +
                ", identification='" + identification + '\'' +
                ", time=" + time +
                ", metabolismdifference=" + metabolismdifference +
                '}';
        return  sb;
    }
}
