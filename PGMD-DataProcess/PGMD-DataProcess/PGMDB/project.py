import datetime
import os
import numpy
import sys
import numpy as np
import openpyxl
import pandas as pd
def project_data_process(path_file):

    # 读取project文件
    df_Original = pd.read_excel(path_file)

    df = df_Original.copy()

    row, col = df.shape[0], df.shape[1]
    arr_val = df.values

    # print(df.columns[25])
    # 先处理表型信息
    for i in range(15,21):
        for j in range(row):
            if arr_val[j][i] != "无" and (not pd.isna(df.loc[j,df.columns[i]])):
                df.at[j, df.columns[i]] = df.columns[i]
            else:
                df.at[j, df.columns[i]] = np.nan

    # 处理实验信息
    for i in range(21, 25):
        for j in range(row):
            if not pd.isna(df.loc[j,df.columns[i]]) and  arr_val[j][i] != "无":
                df.at[j, df.columns[i]] = str(df.columns[i]) + '(' + str(arr_val[j][i]) + ')'
            else:
                df.at[j, df.columns[i]] = np.nan
                # print(arr_val[j][i])

    print(df[df.columns[23]][:10])
    df["Use of antibiotics"] = df["Use of antibiotics"].apply(Antibiotics)

    df['Phenotype'] = df.apply(merge_columns_Phenotype, axis=1)
    # print(df['Phenotype'][:10])
    df['Experimental design'] = df.apply(merge_columns_design, axis=1)
    # print(df['Experimental design'][:10])

    df_Original = df_Original.merge(df[['文章标题', 'Phenotype', 'Experimental design']], on='文章标题')
    # rows = [1,2,3,4,5,6]
    # cols = df.columns.values[13:15]
    # print(df.loc[rows,cols])

    df_Original['Phenotype_info'] = df_Original.apply(merge_columns_Phenotype,axis=1)
    # 保存处理完的数据
    df_Original.to_excel('C:\\Users\\79430\Desktop\\project_process_4_9.xlsx',index =False, encoding='utf-8')
    # print(df.columns.values[18])
    pass


def Antibiotics(value):
    if value == "是":
        value = 'Use of antibiotics'
    elif value == "否":
        value = np.nan
    return value


# 将某几列内容合并为一列
def merge_columns_Phenotype(row):
    # 获取每个单元格的值
    values = [str(row[col]) for col in ['Birth weight;Weaning weight;Weaning diarrhea rate;Weaning survival rate',
                                        'Average daily weight gain(ADGkg/d);Average daily feed intake(ADFI kg/d);Feed conversion efficiency(FCR)',
                                        'Litter size;Number of litters per litter;Milk yield',' Immune performance ',
                                        'Fat deposition;Backfat Thickness;Ocular muscle area;Muscle-to-fat ratio;Intramuscular fat content',
                                        'Metabolites of intestinal flora']]

    # 过滤掉没有内容的单元格
    values = [value for value in values if (value != 'nan' and value!= '无')]
    # print(values)

    merged_value = ';'.join(values)

    return merged_value
    # 返回

def merge_columns_design(row):
    # 获取每个单元格的值
    values = [str(row[col]) for col in ['Feed conversion efficiency(FCR)',
                                        'Early weaning measures',
                                        'Feed additives',
                                        'Feeding practices',
                                        'Others','Use of antibiotics',]]
    # 过滤掉没有内容的单元格
    values = [value for value in values if value != 'nan']
    # print(values)
    merged_value = ';'.join(values)
    # 返回两列
    return merged_value



if __name__ == '__main__':
    file= 'C:\\Users\\79430\\Downloads\\111.xlsx'
    project_data_process(file)