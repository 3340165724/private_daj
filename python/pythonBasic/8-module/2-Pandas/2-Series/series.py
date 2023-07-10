import pandas

s = pandas.Series(["zhangsan", 25])
print(s)

s1 = pandas.Series(["zhangsan", 25], index=["name", "age"])
print(s1)

# todo 默认情况 read_csv 函数读取逗号分隔的文件，sep='\t' 显示指明使用制表符分隔
s2 = pandas.read_csv(r'../data/gapminder.tsv', sep='\t')

print(s2.shape)