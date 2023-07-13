import pandas

# 从Excel文件中读取数据
df = pandas.read_excel('../../data/21dsj.xlsx', sheet_name='Sheet1')
print(df)

df.to_excel('output.xlsx', sheet_name='Sheet1', index=False)