import tkinter.filedialog
import numpy
import sys
import xlrd
import openpyxl
import pandas as pd
import plotly_express as px
import plotly
import plotly.graph_objects as go

df1 = pd.read_csv('C:\\Users\\79430\Desktop\\1234567.csv')


# print()
fig = go.Figure(go.Sunburst(
    values=df1.abundance, parents=df1.parents, opacity=0.7, labels=df1.name

))

# fig.update_layout(margin = dict(t=0, l=0, r=0, b=0),title="1234")
fig.update_layout(margin = dict(t=0, l=0, r=0, b=0))
fig.show()
# plotly.offline.plot(fig, filename="sunburst3.html")