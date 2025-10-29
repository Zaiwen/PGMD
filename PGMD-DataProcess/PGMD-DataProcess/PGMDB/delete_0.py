import tkinter.filedialog
import numpy
import sys
import xlrd
import openpyxl
import pandas as pd


df = pd.read_csv('C:\\Users\\79430\Desktop\\test202335.csv')

temp = []
# print(df.shape[1])
for i in range(df.shape[0]):
    if (df.values[i][3] == 0 ):
        temp.append(i)

print(len(temp))
df.drop(labels=temp, axis=0, inplace=True)
df.reset_index(drop=True)

# df.to_csv('C:/Users/79430/Desktop/' + "test202335"  '.csv', index=False)