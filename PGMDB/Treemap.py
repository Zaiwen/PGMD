import tkinter.filedialog
import numpy
import sys
import xlrd
import openpyxl
import pandas as pd
import plotly_express as px
import plotly
import plotly.graph_objects as go

df = pd.read_csv('C:\\Users\\79430\Desktop\\123456.csv')

fig = px.treemap(df,path=["k", "p", "c", 'o', 'f', 'g', 's'],values=df.columns[11],color=df.columns[11],labels=df.columns[1],
                 color_continuous_scale='Spectral', color_continuous_midpoint=30 )
fig.update_traces(textinfo='label+value+current path+ percent entry')
# fig.show()

plotly.offline.plot(fig,filename="C:\\Users\\79430\Desktop\\Treemap.html")
