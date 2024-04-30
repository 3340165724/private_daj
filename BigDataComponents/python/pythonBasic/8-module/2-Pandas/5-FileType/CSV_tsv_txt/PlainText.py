import pandas

# todo 默认情况 read_csv 函数读取逗号分隔的文件，sep='\t' 显示指明使用制表符分隔
df = pandas.read_csv(r'../../../../data/gapminder.tsv', sep='\t')
print(df)


# todo 按照指定格式导出数据
# df.to_csv(r"D:\pro_redskirt\private_daj\python\pythonBasic\8-module\2-Pandas\5-FileType\CSV_tsv_txt\a.csv",sep=",",index=False,header=True)
# print(df)

# todo 数据预览
print("返回DataFrame的前几行数据，默认返回前5行：\n", df.head())
print('通过传递参数来指定返回的行数：\n', df.head(10))
print("返回DataFrame的最后几行数据，默认返回最后5行：\n", df.tail())
print("通过传递参数来指定返回的行数：\n", df.tail(10))
print("返回DataFrame的行数和列数:\n",df.shape)
print("返回DataFrame的列名:\n",df.columns)
print("显示DataFrame的基本信息，包括列名、非空值的数量和数据类型等: \n ", df.info())
print("返回DataFrame每列的数据类型:\n", df.dtypes)
print("随机返回DataFrame中的n行数据样本\n",df.sample(n=5))

# todo 数据选择和过滤
print("通过列名选择单个列，并返回一个Series对象：\n", df["lifeExp"])
print("通过列名选择多个列，并返回一个新的DataFrame对象：\n", df[["country", "lifeExp"]])
print("通过标签选择特定的行和列：\n", df.loc[5, "lifeExp"])
print(df.loc[2:5, ["country", "lifeExp"]])
print("通过位置选择特定的行和列：\n", df.iloc[5:8, 2:6])

# todo 数据统计和描述性统计
print("计算某一列DataFrame中每列的描述性统计信息：\n", df["lifeExp"].describe())
print("计算DataFrame中每列的描述性统计信息：\n", df.describe())

# todo 数据排序
print("根据指定的列对DataFrame进行排序，默认按升序排列：\n", df.sort_values(by="year"))
print("根据指定的列对DataFrame进行排序，默认按升序排列：\n", df.sort_values(by="year", ascending=False))

# todo 缺失值处理
print("返回一个布尔型DataFrame，用于指示每个元素是否为缺失值：\n", df.isnull)
print("删除包含缺失值的行或列：\n", df.dropna())
print("将缺失值替换为指定的值：\n", df.fillna("year"))

# todo 数据分组和聚合
print("根据指定的列对数据进行分组:\n", df.groupby("year"))

# todo 数据处理和转换
print("删除指定的列:\n", df.drop(columns="country"))
print("重命名列名:\n",df.rename(columns={'year': 'year_'}))

