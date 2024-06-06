import pandas as pd
from pyecharts import options as opts
from pyecharts.charts import Bar3D
import pyecharts
'''

'''

df = pd.read_csv('C:\\Users\\79430\Desktop\\level-7-Phylum.csv')

temp_res = []
for i in range(df.shape[0]):
    if(df.values[i][2] =="Phylum"):
        temp_res.append(df.values[i])

df_copy = pd.DataFrame(data=temp_res,columns=df.columns)

# 挑选数据
data1 = [[values[1],values[5],values[4]] for values in df_copy.values]
# print(data)
# print(df_copy[df_copy.columns[5]])

# 3D柱状图
bar3d=Bar3D(
    init_opts=opts.InitOpts(theme='white',page_title=df.values[0][6],is_horizontal_center=True)  # 初始化配置项
    ).add(
    # 数据配置
    series_name="XXX丰富度对比", # 系列名称
    data=data1,
    shading="realistic",
    xaxis3d_opts=opts.Axis3DOpts(type_="category", data=df_copy[df_copy.columns[1]],name="分类",), # 坐标轴名称。
    yaxis3d_opts=opts.Axis3DOpts(type_="category", data=df_copy[df_copy.columns[5]],name="样品ID"),
    zaxis3d_opts=opts.Axis3DOpts(type_="value",max_=100,name="相对丰富度") # 此处最大值为1，也可以自行设置，不过值过大颜色都是红色
        # !!!!全局配置项!!!!
        ).set_global_opts(
            # 标题配置项
            title_opts=opts.TitleOpts(title="3D丰富度图") ,
            # 视觉映射配置项
            visualmap_opts=opts.VisualMapOpts(
            max_=100,
            range_opacity=0.7,# visualMap 图元以及其附属物（如文字标签）的透明度。
            range_color=[
                "#313695",
                "#4575b4",
                "#74add1",
                "#abd9e9",
                "#e0f3f8",
                "#ffffbf",
                "#fee090",
                "#fdae61",
                "#f46d43",
                "#d73027",
                "#a50026",
            ],
                                              #工具箱
        ),toolbox_opts=opts.ToolboxOpts(feature=opts.ToolBoxFeatureOpts(save_as_image=(opts.ToolBoxFeatureSaveAsImageOpts())))
        ,datazoom_opts=opts.DataZoomOpts(),#3d图无效果

        legend_opts=opts.LegendOpts(type_='scroll',selected_mode=bool,orient='vertical'),#图例配置项,'scroll'：可滚动翻页的图例。当图例数量较多时可以使用。#3d图无效果
        tooltip_opts=opts.TooltipOpts() #提示框配置项

    ).set_series_opts(
    # label_opts=opts.LabelOpts(),
    linestyle_opts=opts.LineStyleOpts()
)

bar3d.render("C:\\Users\\79430\Desktop\\test1.html")