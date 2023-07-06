import pandas

# todo 创建数据集
df = pandas.DataFrame(
    data={'name': ['zhangsan', "lis", 'tom'],
     'age': [25, 29, 26],
     'sex': ['男', '女', '男']
     },
    columns=["name","age","sex"]  # 确定列的顺序的
)
print(df)

print("根据索引值取出对应的子集：\n",df.loc[2])

# todo 获取到某个列的值
ages =df['age']
print("获取到某个列的值: \n",ages)

# todo 计算概括统计量
print("计算概括统计量: \n ", ages.describe())

# todo 同长度向量
print("同长度向量：\n", ages + ages)


