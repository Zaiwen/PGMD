import plotly_express as px
import plotly
import pandas as pd

# plotly.offline.iplot(df)

df = pd.read_csv('C:\\Users\\79430\Desktop\\level-7-Phylum.csv')

temp_res = []
for i in range(df.shape[0]):
    if(df.values[i][2] =="Class"):
        temp_res.append(df.values[i])

df_copy = pd.DataFrame(data=temp_res,columns=df.columns)

fig = px.bar(df_copy,x="sample_accession",y=df_copy.columns[4],color=df_copy.columns[1],title="相对丰富度",
             color_continuous_scale='Spectral', color_continuous_midpoint=30,
             template="plotly")

plotly.offline.plot(fig)
# fig.show()
