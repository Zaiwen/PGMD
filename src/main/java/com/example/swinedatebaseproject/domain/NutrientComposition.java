package com.example.swinedatebaseproject.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutrientComposition implements Serializable {

    @TableId(value = "Nutrient_id", type = IdType.AUTO)
    private Integer nutrientId; // 营养物质ID

    @TableField(value = "Bio_Project")
    private String bioProject; // 生物项目

    @TableField(value = "Dm")
    private Float dm; // 干物质

    @TableField(value = "Cp")
    private Float cp; // 粗蛋白

    @TableField(value = "Ee")
    private Float ee; // 粗脂肪

    @TableField(value = "Cf")
    private Float cf; // 粗纤维

    @TableField(value = "Nfe")
    private Float nfe; // 无氮提取物

    @TableField(value = "Ash")
    private Float ash; // 灰分

    @TableField(value = "Ndf")
    private Float ndf; // 中性洗涤纤维

    @TableField(value = "Adf")
    private Float adf; // 酸性洗涤纤维

    @TableField(value = "Starch")
    private Float starch; // 淀粉

    @TableField(value = "Ca")
    private Float ca; // 钙

    @TableField(value = "Tp")
    private Float tp; // 总磷

    @TableField(value = "Ep")
    private Float ep; // 有效磷

    @TableField(value = "Descriptive_Info")
    private String descriptiveInfo; // 描述信息

    @TableField(value = "Link_Doi")
    private String linkDoi; // 链接DOI

    @TableField(value = "head")
    private String head; // 负责人

    @TableField(value = "Nutrient_index")
    private String nutrientIndex; // index
}
