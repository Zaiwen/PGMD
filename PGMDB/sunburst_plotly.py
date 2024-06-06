import plotly_express as px
import plotly
import pandas as pd

# plotly.offline.iplot(df)

df = pd.read_csv('C:\\Users\\79430\Desktop\\level-7-Phylum.csv')

fig = px.sunburst(df,values=df.columns[4],names=df.columns[0],
                  path=["Projects_accession","sample_accession","level","name"],color=df.columns[1],color_continuous_scale='RdBu',
                  title="相对峰度")

fig.update_traces(textinfo="label + percent entry")

plotly.offline.plot(fig,filename="sunburst.html")
# print(df.values[1][0])