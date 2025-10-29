# s1 ="k__Bacteria;p__Firmicutes;c__Clostridia;o__Clostridiales;f__Lachnospiraceae;g__Roseburia;__"
# temp = s1.split(";")
# for i in range(len(temp)):
#     if(len(temp[i])>3):
#         temp[i]=temp[i][3:]
#     elif (len(temp[i])==3):
#         temp[i] = temp[i][1:]
# s1 = ";".join(temp)
# print(s1)
import os
import time

import pandas as pd
# replace_columns = ";".join(temp[:len(temp) - 1])
# print(replace_columns)
# from PGMDB.util import delete_row
# df = pd.read_csv("C:\\Users\\79430\\Downloads\\level-7 (1).csv")


# print(df.iloc["B",1])



# data = pd.DataFrame({
#     "A":np.random.uniform(1,1000,5),
#     "B":np.random.uniform(1,1000,5),
#     "C":np.random.uniform(1,1000,5),
#     "D":np.random.uniform(1,1000,5)
# })
#
# print(data.values)
# data["A"] += data["B"].values
# print(data.values,data.columns[1])
# change_dict={}
# for i in range(1, data.shape[1]):
#
#     if (not (data.columns[i] in change_dict)):
#         change_dict[data.columns[i]] = i
# print(change_dict)
# s1='k__Bacteria;p__Cyanobacteria;c__4C0d-2;o__YS2;f__;g__;s__'
# Loop_times = s1.count(';')
# print(Loop_times)
# os.mkdir("E:\\project\\"+'project_name'+'_1')
# path=os.path.abspath("E:\\project\\"+'project_name'+'_1')
# print(path.split('\\')[-1])
# import datetime
#
# now = datetime.datetime.now()
# formatted_time = now.strftime("%Y%m%d%H%M%S")
# print("当前时间为：", formatted_time)
# str1 = "E:/分析结果/level-7 (5)/level-7 (5-11).csv"
# s1 = str1.split("/")
# print(s1)
# import pandas as pd
# def data_preprocess(df):
#     '''
#     每一个样本中，第七个水平可能有些分类完全一致，需要累加起来（计算丰度前的预处理）
#     :param df:
#     :return:
#     '''
#     # 找出重复的列
#     duplicate_columns = df.columns[df.columns.duplicated()]
#
#     replace_duplicate_columns = []
#     for item in duplicate_columns:
#         replace_item = item+"-sum"
#         replace_duplicate_columns.append(replace_item)
#     # 将重复列的值相加，并将结果存储在一个新列中
#         df[replace_item] = df[item].sum(axis=1)
#     # 删除原始的重复列
#     df.drop(duplicate_columns, axis=1, inplace=True)
#
#     for i in range(len(duplicate_columns)):
#     # 将新列的名称更改为原始的列名
#         df.rename(columns={replace_duplicate_columns[i]: duplicate_columns[i]}, inplace=True)
#
#     # 处理表格的数据
#     return df;
#
# data = [[1, 4, 7, 10, 13],
#         [2, 5, 8, 11, 14],
#         [3, 6, 9, 12, 15]]
#
# columns = ['A', 'B', 'C', 'D', 'F']
# df = pd.DataFrame(data=data,columns=columns)
#
# for item in df.columns.values:
#     print(item)
#     if item == "A" or item == "B":
#         df.rename(columns={item: "G"}, inplace=True)
#
#     if item =="D" or item =="C":
#         df.rename(columns={item: "E"}, inplace=True)
# print(df.columns.values)
# # 找出重复的列
# duplicate_columns = df.columns[df.columns.duplicated()]
# print(duplicate_columns)
# print(df[duplicate_columns])
# print(df.columns[0])
#
#
#
# input_string = 'k__Bacteria;p__TM7;c__TM7-3;o__CW040;f__F16;g__;s__'
#
# # 将字符串按照分号进行切分，并去除空格和下划线
# split_strings = [s.strip('__') for s in input_string.split(';')]
#
# # 将子字符串连接起来，并使用分号连接在一起
# output_string = ';'.join(split_strings)
#
# # 输出结果
# print(output_string)
#
# if input_string.split(";")[0] == "Unassigned":
#     print(True)
#
# temp = input_string.split(";")
# # 分割之后子块长度大于=3,进一步处理
# for j in range(len(temp)):
#     if (len(temp[j]) > 3):
#         temp[j] = temp[j][3:]
#         if temp[j].startswith('[') and temp[j].endswith(']'):
#             temp[j] = temp[j].strip('[]')
#     elif (len(temp[j]) == 3):
#         temp[j] = temp[j][1:]
# s1 = ";".join(temp)
# print(s1)
# print(s1[-1])

# print(data_preprocess(df))

import sys

import pandas as pd

# # 读取A文件和B文件
# df_a = pd.read_excel('C:\\Users\\79430\\Desktop\\project_process.xlsx')
# df_b = pd.read_excel('C:\\Users\\79430\\Desktop\\test.xlsx')
#
# # 找到在A文件的Bio_project列中存在，但在B文件的project列中不存在的数据行
# missing_rows = df_a[~df_a['BioProject'].isin(df_b['Project'])]
#
# # 保存结果到Excel文件
# missing_rows.to_excel('C:\\Users\\79430\\Desktop\\test123.xlsx', index=False)
# 读取Excel文件
# df = pd.read_excel('E:\\华中农业大学\\生猪项目\\数据处理\\lda.xlsx')
#
# # 提取需要处理的列数据
# columns = ['BioProject','Phenotypes_Experiment_Design','microbial_name','Group','LDA score']
# #
# if 'PRJEB20514' in df.columns:
#     column_data1 = df['PRJEB20514']
#     df['PRJEB20514'] = column_data1.abs()
# else:
#     print("Column 'PRJEB20514' not found in DataFrame.")
#
# if 'PRJNA437010' in df.columns:
#     column_data2 = df['PRJNA437010']
#     df['PRJNA437010'] = column_data2.abs()
# else:
#     print("Column 'PRJNA437010' not found in DataFrame.")
#
# arr_values = df.values
# row,col = df.shape
# columns_names = df.columns.tolist()
# print(columns_names)
# data = []
# for i in range(2):
#     for j in range(row):
#         data.append([columns_names[i+1],'xxx',arr_values[j][0],arr_values[j][3],arr_values[j][i+1]])
#
# res = pd.DataFrame(data=data, columns=columns)
# res.to_csv('E:\\华中农业大学\\生猪项目\\数据处理\\data.csv', index=False)
# print("已保存")
#
# 您选择的文件路径是： E:/分析结果/lefse/lefse分析结果(无5)/1-lefse/1_24/lefse.LDA.csv
# E:/分析结果/lefse/lefse分析结果(无5)/1-lefse/1_27/lefse7SUBuApKxP94RRr6qXJ8Uv_result/lefse.LDA.csv
# ['name', 'xx', 'group', 'LDAscore', 'xxx']
# 已保存
# 您选择的文件路径是： E:/分析结果/lefse/lefse分析结果(无5)/1-lefse/1_27/lefse7SUBuApKxP94RRr6qXJ8Uv_result/lefse.LDA.csv
# s = "E:/分析结果/lefse/lefse分析结果(无5)/1-lefse/1_27/lefse7SUBuApKxP94RRr6qXJ8Uv_result/lefse.LDA.csv"
# index = s.split("/")
# print(index[-3])


# print(len("Bacteria;Actinobacteria;Actinobacteria;Actinomycetales;Intrasporangiaceae;__;__"))

# df = pd.read_csv('C:\\Users\\79430\\Desktop\\无标题.csv')
#
# # 获取第一列和第二列的数据
# column1 = df.iloc[:, 0]
# column2 = df.iloc[:, 1]
#
# # 找出在第二列存在但不在第一列存在的数据
# unique_values = column2[~column2.isin(column1)]
#
# # 输出结果
# print(unique_values)

# import os
#
#
# # 指定目录路径
# directory = 'E:\\华中农业大学\\生猪项目\\系统开发\\composition of diet\\7'
#
# # 遍历目录下的文件
# for filename in os.listdir(directory):
#     if filename.endswith('.xlsx'):
#         # 提取文件名和扩展名
#         file_parts = os.path.splitext(filename)
#         file_name = file_parts[0]
#         file_extension = file_parts[1]
#
#         # 以 "-" 分割文件名，并获取最后一个分割块作为新名字
#         new_name = file_name.split('-')[-1].strip()
#
#         # 构建新的文件名
#         new_filename = new_name + file_extension
#
#         # 构建旧文件路径和新文件路径
#         old_filepath = os.path.join(directory, filename)
#         new_filepath = os.path.join(directory, new_filename)
#
#         # 更改文件名
#         os.rename(old_filepath, new_filepath)


# 比较数据库中的差异数据（基于项目号）
file_path1 = "C:\\Users\\79430\\Desktop\\sample_项目号.csv"
file_path2 = "C:\\Users\\79430\\Desktop\\project_项目号430.csv"
file_path3 = "C:\\Users\\79430\\Desktop\\microbial_项目号.csv"
file_path4 = "C:\\Users\\79430\\Desktop\\share.xlsx"

df1 = pd.read_csv(file_path1)
df2 = pd.read_csv(file_path2)
df3 = pd.read_csv(file_path3)
df4 = pd.read_excel(file_path4)

col_data_set1 = set(df1["Bio_Project"])
col_data_set2 = set(df2["Bio_Project"])
col_data_set3 = set(df3["BioProject"])
col_data_set4 = set(df4["数据登录号"])

# columns1 = ["差异一"]
# columns2 = ["差异二"]
# columns3 = ["差异三"]
# res1 = pd.DataFrame(data=list(col_data_set2-col_data_set4),columns=columns1)
# res2 = pd.DataFrame(data=list(col_data_set2-col_data_set1),columns=columns2)
# res3 = pd.DataFrame(data=list(col_data_set2-col_data_set3),columns=columns3)

# print("数据库中有共享表已删除:",list(col_data_set2-col_data_set4))
# print(col_data_set2-col_data_set1)
print(col_data_set3-col_data_set2)

# res1.to_excel("C:\\Users\\79430\\Desktop\\差异一.xlsx",index=False)
# res2.to_excel("C:\\Users\\79430\\Desktop\\差异二.xlsx",index=False)
# res3.to_excel("C:\\Users\\79430\\Desktop\\差异三.xlsx",index=False)
######