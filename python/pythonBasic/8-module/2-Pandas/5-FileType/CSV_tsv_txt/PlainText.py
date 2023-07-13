import pandas

# todo 默认情况 read_csv 函数读取逗号分隔的文件，sep='\t' 显示指明使用制表符分隔
df = pandas.read_csv(r'../../data/gapminder.tsv', sep='\t')
print(df)