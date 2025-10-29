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

@TableName(value = "microbe")
public class Microbe implements Serializable {
    @TableId(value = "microbe_id")
    private Integer microbeId;

    @TableField(value = "taxonomy")
    private String taxonomy;

    @TableField(value = "microbe_Name")
    private String microbeName;

    @TableField(value = "col")
    private String col;

    @TableField(value = "days")
    private Integer days;

    @TableField(value = "abundance")
    private float abundance;

    @TableField(value = "P_value_dpf_tpf")
    private float pvaluedpftpf;

    @TableField(value = "P_value_age")
    private float pvalueAge;

    @TableField(value = "microbe_dpf_tpf_difference")
    private Integer microbedpftpfdifference;

    @TableField(value = "microbe_age_difference")
    private Integer microbeagedifference;

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Microbe microbe = (Microbe) o;

        if (Float.compare(microbe.abundance, abundance) != 0) return false;
        if (Float.compare(microbe.pvaluedpftpf, pvaluedpftpf) != 0) return false;
        if (Float.compare(microbe.pvalueAge, pvalueAge) != 0) return false;
        if (!microbeId.equals(microbe.microbeId)) return false;
        if (!taxonomy.equals(microbe.taxonomy)) return false;
        if (!microbeName.equals(microbe.microbeName)) return false;
        if (!col.equals(microbe.col)) return false;
        if (!days.equals(microbe.days)) return false;
        if (!microbedpftpfdifference.equals(microbe.microbedpftpfdifference)) return false;
        return microbeagedifference.equals(microbe.microbeagedifference);
    }

    @Override
    public int hashCode() {
        int result = microbeId.hashCode();
        result = 31 * result + taxonomy.hashCode();
        result = 31 * result + microbeName.hashCode();
        result = 31 * result + col.hashCode();
        result = 31 * result + days.hashCode();
        result = 31 * result + (abundance != +0.0f ? Float.floatToIntBits(abundance) : 0);
        result = 31 * result + (pvaluedpftpf != +0.0f ? Float.floatToIntBits(pvaluedpftpf) : 0);
        result = 31 * result + (pvalueAge != +0.0f ? Float.floatToIntBits(pvalueAge) : 0);
        result = 31 * result + microbedpftpfdifference.hashCode();
        result = 31 * result + microbeagedifference.hashCode();
        return result;
    }

    @Override
    public String toString() {
        String sb = getClass().getSimpleName() +
        "Microbe{" +
                "Hash = " + hashCode() +
                "microbeId=" + microbeId +
                ", taxonomy='" + taxonomy + '\'' +
                ", microbeName='" + microbeName + '\'' +
                ", col='" + col + '\'' +
                ", days=" + days +'\'' +
                ", abundance=" + abundance +'\'' +
                ", pValueAge=" + pvalueAge +'\'' +
                ", DptTpfdifference=" + microbedpftpfdifference +'\'' +
                ", Agedifference=" + microbeagedifference +'\'' +
                ", serialVersionUID=" + serialVersionUID +'\'' +
                '}';
        return sb;
    }
}
