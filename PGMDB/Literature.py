from math import isnan

import numpy as np
import pandas as pd

def resolve_literature(df):
    colunm1 = ["ID","groups"]

    colunm2 = ["source","target","value"]

    row,col =df.shape[0],df.shape[1]

    temp1=[]
    temp2=[]
    temp3=list(df.columns[11:])
    # pandas 内置判断是否为空值nan
    # print(pd.isna(df.values[2][13]))

    for i in range(75):
        temp1.append([df.values[i][1],"Literature"])
        for j in range(8):
            if(not pd.isna(df.values[i][j])):
                temp2.append([df.values[i][1],df.columns[11+j],1])

    for item in range(8):
        temp1.append([temp3[item],"Theme"+str(item)])

    res1 = pd.DataFrame(data=temp1, columns=colunm1)
    res1.to_csv('C:/Users/79430/Desktop/' + "temp1"+ '.csv', index=False,encoding="utf-8")
    res2 = pd.DataFrame(data=temp2, columns=colunm2)
    res2.to_csv('C:/Users/79430/Desktop/' + "temp2"+ '.csv', index=False)

    pass


if __name__ == '__main__':
    filepath='C:\\Users\\79430\\Desktop\\文献收集.xlsx'
    df = pd.read_excel(filepath)
    resolve_literature(df)
    pass