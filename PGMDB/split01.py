import tkinter.filedialog
import numpy
import sys
import xlrd
import openpyxl
import pandas as pd
import plotly_express as px
import plotly


df = pd.read_csv('C:\\Users\\79430\Desktop\\test202335.csv')

temp = []
row, col = df.shape[0], df.shape[1]
column = ['name','ALL','parents','level','count','abundance(%)','sample_accession','Projects_accession']



for i in range(row):
    str_temp = str(df.values[i][1]).split(";")
    temp1 = []
    temp1.append(str_temp[-1])
    temp1.append( df.values[i][1])
    temp1.append(str_temp[-2])
#     temp_len = len(str_temp)
#     for j in range(temp_len):
#         if(len(str_temp[j])>3):
#             str_temp[j] = str_temp[j][3:]
#         else:
#             str_temp[j] = " "
#     temp1 =7-len(str_temp)
#     while (temp1!=0 ):
#         str_temp.append(" ")
#         temp1-=1
#
    # temp1.insert(0, df.values[i][1])
    # temp1.insert(0,df.values[i][0])
    for x in range(5):
        temp1.append(df.values[i][2+x])
    temp.append(temp1)

print(temp[1])
res = pd.DataFrame(data=temp, columns=column)
res.to_csv('C:/Users/79430/Desktop/' +"1234567"+'.csv', index=False)
# df1 = pd.read_csv('C:\\Users\\79430\Desktop\\12345.csv')
# fig = px.sunburst(df1, names=df1.columns[0],values=df1.columns[11],color=df1.columns[1],
#                         hover_name=df1.columns[0],
#                     path = ["k", "p", "c", 'o', 'f', 'g', 's'],
#                       color_continuous_scale='RdBu',
#                         branchvalues="total",
#                       title="相对峰度")
#
# # path = ["k", "p", "c", 'o', 'f', 'g', 's'],
# # # fig.update_traces(textinfo="label + percent entry")
# #
# plotly.offline.plot(fig, filename="sunburst3.html")
# # fig.show()
# print(type(df.values[1][1]))