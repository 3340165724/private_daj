import pandas

# todo 默认情况 read_csv 函数读取逗号分隔的文件，sep='\t' 显示指明使用制表符分隔
df = pandas.read_csv(r'../../data/gapminder.tsv', sep='\t')

# todo 按照指定格式导出数据
# df.to_csv(r"D:\pro_redskirt\private_daj\python\pythonBasic\8-module\2-Pandas\5-FileType\CSV_tsv_txt\a.csv",sep=",",index=False,header=True)
# print(df)

# todo 数据预览
print("返回DataFrame的前几行数据，默认返回前5行：\n", df.head())
print('通过传递参数来指定返回的行数：\n', df.head(10))
print("返回DataFrame的最后几行数据，默认返回最后5行：\n", df.tail())
print("通过传递参数来指定返回的行数：\n", df.tail(10))

# todo 数据选择和过滤
print("通过列名选择单个列，并返回一个Series对象：\n", df["lifeExp"])
print("通过列名选择多个列，并返回一个新的DataFrame对象：\n", df[["country", "lifeExp"]])
print("通过标签选择特定的行和列：\n", df.loc[5, "lifeExp"])
print(df.loc[2:5, ["country", "lifeExp"]])
print("通过位置选择特定的行和列：\n", df.iloc[5:8, 2:6])

# todo 数据统计和描述性统计
print("计算某一列DataFrame中每列的描述性统计信息：\n", df["lifeExp"].describe())
print("计算DataFrame中每列的描述性统计信息：\n", df.describe())

# 数据排序
print("根据指定的列对DataFrame进行排序，默认按升序排列：\n",df.sort_values(by="year"))
print("根据指定的列对DataFrame进行排序，默认按升序排列：\n",df.sort_values(by="year",ascending=False))


# todo 缺失值处理