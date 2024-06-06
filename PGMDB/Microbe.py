import datetime
import os
import numpy
import sys

import openpyxl
import pandas as pd
import tkinter as tk

#读取文件
from PGMDB.util import delete_row


def resolve_microbe(file_path1):

    # str1 = file_path1.split('.')

    # print(str1[0][:-8:-1][::-1])
    df = pd.read_csv(file_path1)
    # print(type(df))
    level = dict({1: 'Phylum', 2: 'Class', 3: 'Order', 4: 'Family', 5: 'Genus',6:'Species'})


    # print(row,col)
    #将预处理数据提取出来
    # arr_val = df.values

    # print(type(arr_val[1][1]))
    # print(arr_val[1][1:].sum())

    # print(round(arr_val[1][1]*100/arr_val[1][1:].sum(),3))
    # print(str(df.columns[1]).split(";")[-1])
    # print(df.columns[1])

    # df.sort_index(axis=1)
    if "BioProject" in df.columns:
        project_name = df.loc[0, "BioProject"]
    else:
        project_name="column_not_exist"
    print(project_name,end=' ')
    # 删除古细菌的数据
    # temp = []
    # for i in range(df.shape[1]):
    #     if(df.columns[i][:10] == 'k__Archaea'):
    #         temp.append(df.columns[i])
    # df.drop(temp, axis=1, inplace=True)

    #将第一列和微生物数据列分出来
    for j in range(1,df.shape[1]):
        if (df.columns[j][:3]  not in  ["k__","Una"]):
            break
    # 删除无记录的行数据
    delete_row(df, j)
    # 获取当前时间
    now = datetime.datetime.now()
    formatted_time = now.strftime("%Y%m%d_%H%M%S")

    #新建文件夹用于将.CSV文件保存至E盘,如果没有E盘，可以更换其他盘符
    if not os.path.exists("E:\\project\\"+project_name):
        os.mkdir("E:\\project\\"+project_name+'_'+formatted_time)
        path = os.path.abspath("E:\\project\\"+project_name+'_'+formatted_time)
    # else:
    #     os.mkdir("E:\\project\\" + project_name )
    #     path = os.path.abspath("E:\\project\\" + project_name )

    file_name = path.split('\\')[-1]
    # 如果列名不存在，则插入新列并设置默认值
    # if "COUNTRY" not in df.columns:
    #     df = df.assign(**{"COUNTRY": "Missing:Not provided"})
    #
    # 抽取元数据第一版。已弃用
    # target_list = ["index", "BioProject", "BioSample",  "Experiment", "Instrument", "Platform","SRA Study"]
    # df[target_list].to_csv('E:\\project\\' + file_name + '\\samples_project_' + file_name + '.csv', index=False)

    # 提取元数据信息
    # extraction_metadata(df, formatted_time,file_name)
    #元数据描述信息已提取，只保留丰度数据
    df.drop(df.columns[j:],axis=1, inplace=True)
    #此处待优化
    Loop_times = str(df.columns[1]).count(';')
    # print(Loop_times)
    # #处理数据
    temp_res = []
    # #设置列名
    column = ['microbial_name','specific_taxonomy','level','count','abundance(%)','runs',"BioProject"]
    print(df.shape[1],end=" ")
    #去除前缀
    #df = delete_prefix_colunm_name(df)

    #计算丰度前的预处理
    df = data_preprocess(df)
    print(df.shape[1])

    for k in range(Loop_times):
        # temp_res = []
        row, col = df.shape[0], df.shape[1]
        arr_val = df.values
        for i in range(row):
            temp_sum = arr_val[i][1:].sum();
            for j in range(1,col):
                if(arr_val[i][j]!=0):
                    abundance = round(arr_val[i][j]*100/temp_sum,3);
                else:
                    abundance = 0;
                if(str(df.columns[j]).split(";")[0]=="Unassigned"):
                    name="Unassigned"
                elif (str(df.columns[j]).split(";")[-1]=="__" ):
                    name = "Unknown"
                else:
                    name = str(df.columns[j]).split(";")[-1]
                level_dist = str(df.columns[j]).count(';')
                if abundance != 0:   #不保存丰富度为0的数据
                    temp_res.append([name,df.columns[j],level[level_dist],arr_val[i][j],abundance,arr_val[i][0],project_name])
        # #每处理一个水平的数据就保存该水平的数据
        # res = pd.DataFrame(data=temp_res, columns=column)
        # res.to_csv('C:/Users/79430/Desktop/' + str1[0][:-8:-1][::-1] +"-"+ level[6-k] + '.csv', index=False)
        if(k<Loop_times-1):
            df = data_process(df)

    res = pd.DataFrame(data=temp_res, columns=column)
    res.to_csv('E:\\project\\'+file_name + "\\microbe_" + file_name + '.csv', index=False)
    print("已保存")
    #

    # #将处理好的数据保存至.csv文件
    # print('#保存结果#')
    #将空值替换成0.0
    # res.fillna(value=0.0,inplace=True)
    # res.to_csv('C:/Users/79430/Desktop/'+str1[0][:-8:-1][::-1]+''+'.csv',index=False)
    # # print(df.columns.values[2])#读取数据表列



# 有些项目号没有加到表格中，现在加进去。。。临时处理一部分
def temp_resolve_microbe(file_path):
    df = pd.read_csv(file_path)

    s1 = file_path.split("/")[-2].split("_")
    # print(s1)
    # s1 = s1[-2].split("_")
    print(s1[0])
    df["BioProject"]=s1[0]
    df.to_csv(file_path,index=False)
    # print(df.values[1])

    pass


# 文件改名
def change_name(file_path):
    df = pd.read_csv(file_path)
    project_name = df.loc[0, "BioProject"]
    print(project_name)
    # 提取文件名和文件路径
    # filename = os.path.basename(file_path)
    dirname = os.path.dirname(file_path)

    # # 构造新的文件名
    new_filename = f'{project_name}.csv'

    # 构造新的文件路径
    new_filepath = os.path.join(dirname, new_filename)
    print(new_filepath)

    if os.path.exists(new_filepath):
        new_filename=f'{project_name+"-1"}.csv'
        new_filepath = os.path.join(dirname, new_filename)
        print(new_filepath)


    # 重命名文件
    os.rename(file_path, new_filepath)
    # os.rename(file_path.split("/")[-1],project_name)
    # print(project_name, end=' ')
    # df.to_csv('E:\\project\\' + project_name + '.csv', index=False)
    pass


def change_feed_name(file_path):

    pass

# 后续用于生成空白饲料表（备用）
def create_excel(file_path):
    df = pd.read_excel(file_path)

    # 获取指定列的值
    column_values = df['your_column_name']
    # 指定保存文件的文件夹路径
    folder_path = 'your_folder_path'
    # 遍历列的值并生成空白Excel文件
    for value in column_values:
        # 创建ExcelWriter对象
        file_path = os.path.join(folder_path, f'{value}.xlsx')
        writer = pd.ExcelWriter(file_path, engine='xlsxwriter')

        # 创建空白的数据框架
        df_blank = pd.DataFrame()

        # 将数据框架写入Excel文件
        df_blank.to_excel(writer, sheet_name=value, index=False)

        # 保存Excel文件
        writer.save()

def extraction_metadata(df,formatted_time,file_name):
    """
    处理样本元数据的函数，根据需求在做更改 2023-06-14 17:37:46
    :param file_df:
    :return:
    """
    # df = pd.read_csv(file_path)

    project_name = df.loc[0, "BioProject"]
    print(project_name,end=' ')

    # 获取当前时间////时间作为参数传递进来
    # now = datetime.datetime.now()
    # formatted_time = now.strftime("%Y%m%d_%H%M%S")

    # project_name = df.loc[0,"BioProject"]
    #新建文件夹用于将.CSV文件保存至E盘,如果没有E盘，可以更换其他盘符
    # if not os.path.exists("E:\\project\\"+project_name):
    #     os.mkdir("E:\\project\\"+project_name+'_'+formatted_time)
    #     path = os.path.abspath("E:\\project\\"+project_name+'_'+formatted_time)
    # else:
    #     os.mkdir("E:\\project\\" + project_name )
    #     path = os.path.abspath("E:\\project\\" + project_name )

    # file_name = path.split('\\')[-1]
    # 如果列名不存在，则插入新列并设置默认值
    target_list = ["index", "BioProject",  'BioSample', 'LibraryLayout', "ReleaseDate", 'Instrument'
        ,'Experimental_design','Group','Sub_Group','Category','Growth_Stage','Sex',
                   'Country', 'Isolation_Location','Breed','Growth_Stage','Age','Weight']

    for item in target_list:
        if item not in df.columns:
            df = df.assign(**{item: "NA"})

    # if 'ReleaseDate' in df.columns:
    #     df['ReleaseDate']  =df['ReleaseDate'].apply(change_release_time)
    df[target_list].to_csv('E:\\project\\' + file_name + '\\samples_Metadata' + file_name + '.csv', index=False)


def data_preprocess(df):
    '''
    每一个样本中，第七个水平可能有些分类完全一致，需要累加起来（计算丰度前的预处理）
    :param df:
    :return:
    '''
    row, col = df.shape[0], df.shape[1]
    for i in range(1, col):
        if df.columns[i].split(";")[0] == "Unassigned":
            continue

        temp = df.columns[i].split(";")
        # 分割之后子块长度大于=3,进一步处理
        for j in range(len(temp)):
            if (len(temp[j]) > 3):
                temp[j] = temp[j][3:]
                # 去除首位方括号
                if temp[j].startswith('[') and temp[j].endswith(']'):
                    temp[j] = temp[j].strip('[]')
            elif (len(temp[j]) == 3):
                temp[j] = temp[j][1:]
        s1 = ";".join(temp)
        # 更换列名
        df.rename(columns={df.columns[i]:s1},inplace=True)

    # 找出重复的列
    duplicate_columns = df.columns[df.columns.duplicated()]
    print("重复列"+str(len(duplicate_columns)))
    # print(duplicate_columns)

    replace_duplicate_columns = []
    for item in duplicate_columns:
        replace_item = item + "-sum"
        replace_duplicate_columns.append(replace_item)
        # 将重复列的值相加，并将结果存储在一个新列中
        df[replace_item] = df[item].sum(axis=1)
    # 删除原始的重复列
    df.drop(duplicate_columns, axis=1, inplace=True)


    for i in range(len(duplicate_columns)):
        # 将新列的名称更改为原始的列名
        df.rename(columns={replace_duplicate_columns[i]: duplicate_columns[i]}, inplace=True)

    # 处理表格的数据
    return df;
    # pass

def data_process(df):

    #每次调用，将每一个列名所代表的微生物水平向前递进一级
    drop_colunms = []
    change_dict = {}

    for i in range(1,df.shape[1]):
        temp = str(df.columns[i]).split(";")
        replace_columns = ";".join(temp[:len(temp) - 1])

        if( replace_columns not  in change_dict):
            change_dict[replace_columns] = i
            df.rename(columns={df.columns[i]:replace_columns},inplace=True)#更改列名
        else:
            index = change_dict[replace_columns]
            df[df.columns[index]] += df[df.columns[i]]
            drop_colunms.append(df.columns[i])      #记录要删除的列

    df.drop(drop_colunms, axis=1, inplace=True)
    #按列的索引进行排序
    # df.sort_index(axis=1)

    #处理表格的数据
    return df;


def process_lefse(file_path):
    df = pd.read_csv(file_path)

    # 提取需要处理的列数据
    columns = ["index",'BioProject', 'Phenotypes_Experiment_Design', 'microbial_name', 'Group', 'LDA score']
    print(file_path)
    index_list = file_path.split("/")
    name = index_list[-1].split(".")[0]
    if len(index_list[-2])>12:
        index = index_list[-3]
    else:
        index = index_list[-2]

    arr_values = df.values
    row, col = df.shape
    columns_names = df.columns.tolist()
    print(columns_names)
    data = []
    # for i in range(2):
    for i in range(row):

        if not pd.isnull(arr_values[i][2]) :
            data.append([index,"xxx", 'xxx', arr_values[i][0], arr_values[i][2], arr_values[i][3]])

    res = pd.DataFrame(data=data, columns=columns)
    res.to_csv(f'E:\\分析结果\\lefse\\lefse_after_process\\{name}_{index}.csv', index=False)
    print("已保存")
    # pass

# 统一下元数据中发布时间格式！
def change_release_time(str):
    return str[:str.index('T')]
# if __name__ == '__main__':
#     print("#程序开始运行----------")
#     create_gui()
#     print("#程序运行结束----------")