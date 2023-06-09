import pandas

s = pandas.Series(["zhangsan", 25])
print(s)

s1 = pandas.Series(["zhangsan", 25], index=["name", "age"])
print(s1)

# todo 默认情况 read_csv 函数读取逗号分隔的文件，sep='\t' 显示指明使用制表符分隔
s2 = pandas.read_csv(r'../data/gapminder.tsv', sep='\t')

print(s2.shape)


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

# todo 同长度向量（如果在两个长度相同的向量之间执行计算，所得向量的每个元素是两个向量对应元素的计算结果）
print("同长度向量（如果在两个长度相同的向量之间执行计算，所得向量的每个元素是两个向量对应元素的计算结果）：\n", ages + ages)
print("同长度向量（如果在两个长度相同的向量之间执行计算，所得向量的每个元素是两个向量对应元素的计算结果）：\n", ages * ages)

# todo 向量和整数（标量）运算（当对向量和标量进行运算时，标量会与向量中的每个元素逐一进行计算）
print("向量和整数（标量）运算（当对向量和标量进行运算时，标量会与向量中的每个元素逐一进行计算）：\n",ages + 100)
print("向量和整数（标量）运算（当对向量和标量进行运算时，标量会与向量中的每个元素逐一进行计算）：\n",ages * 2)

# todo 不同长度向量间的运算
print("不同长度向量间的运算：\n", ages + pandas.Series([1,100]))

# todo 带有常见索引标签的向量（自动对齐）


