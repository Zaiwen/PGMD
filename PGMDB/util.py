

# 删除无记录的行数据
def delete_row(df, col_number):

    delete_rows = []
    # print("删除"+str(col_number))
    arr_val = df.values
    for i in range(df.shape[0]):
        if arr_val[i][1:col_number].sum() == 0:
            delete_rows.append(i)
    # print(len(delete_rows))
    df.drop(delete_rows, inplace=True)
    print("数据行 " + str(df.shape[0]))
    pass

#删除列名前缀
def delete_prefix_colunm_name(df):

    row, col = df.shape[0], df.shape[1]
    for i in range(1,col):
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
            elif ( len(temp[j]) == 3 ):
                temp[j] = temp[j][1:]
        s1 = ";".join(temp)
        #更换列名
        df.rename(columns={s1: df.columns[i]}, inplace=True)
    # return df
    pass


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
        df.rename(columns={s1: df.columns[i]}, inplace=True)

    # 找出重复的列
    duplicate_columns = df.columns[df.columns.duplicated()]
    print("重复列"+str(len(duplicate_columns)))

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
    # return df;
    pass

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

