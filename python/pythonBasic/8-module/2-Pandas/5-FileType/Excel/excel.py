import pandas

# todo 从Excel文件中读取数据
df = pandas.read_excel('../../../../data/21dsj.xlsx', sheet_name='Sheet1')
print(df)

# todo 将DataFrame数据写入Excel文件
# 使用sheet_name参数指定工作表名称，index=False参数用于避免写入索引列
df.to_excel('output.xlsx', sheet_name='Sheet1', index=False)

# 数据选择和过滤
print(df[["学号", "姓名"]])
print(df[df["学号"] >= 213510205110])

# 缺失值处理
print(df.fillna(0))



