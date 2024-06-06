import datetime
import os
import numpy
import sys
import numpy as np
import openpyxl
import pandas as pd
def nutrient_composition(path_file,project_file):
    df = pd.read_excel(path_file)

    # print(df['index'].values[1],len(df['index'].values))
    #285 补齐标签部分
    for i in range(len(df['index'].values)-1):
        if not pd.isna(df.loc[i,'index']) and pd.isna(df.loc[i+1,'index']):
            df['index'].values[i+1] = df['index'].values[i]

    print(df['index'].values, len(df['index'].values))


    df1 = pd.read_excel(project_file)
    # 新增列 'NewValue' 到 df1，并根据 df2 的 'Value2' 值判断是否相同
    df['Project_'] = ''  # 创建空列

    for index, row in df.iterrows():
        id_value = row['index']
        matching_row = df1[df1['index'] == id_value]
        if not matching_row.empty:
            new_value = matching_row.iloc[0]['数据登录号']
            df.at[index, 'Project_'] = new_value

    df = df[df['营养成分表 （有/无）']!= '无' ]
    # print(df['Project_'].values, len(df['index'].values))
    df.to_excel('C:\\Users\\79430\Desktop\\nutrient_composition.xlsx', index=False, encoding='utf-8')
    pass

if __name__ == '__main__':
    file_path = 'C:\\Users\\79430\\Desktop\\营养表.xlsx'
    file_path1 = 'C:\\Users\\79430\\Desktop\\project.xlsx'
    nutrient_composition(file_path,file_path1)
    print("处理结束！")