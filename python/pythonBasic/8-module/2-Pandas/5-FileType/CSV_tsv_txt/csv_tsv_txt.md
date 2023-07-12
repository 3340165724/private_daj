# csv_tsv_txt 纯文本文件

---------

<br>

### 概述

- 用逗号分隔、tab分割的纯文本文件

### csv

- 逗号分隔值的非常灵活的数据存储格式
- 对于每行，各列采用逗号分隔，除了逗号，也可以其它类型的分隔符，比如制表符或分号
- [DataFrame ](../../3-DataFrame/dataframe.md "DataFrame") 和 [Series ](../../2-Series/series.md "Series")都有to_csv方法

### 方法

| 分类            | 方法                                                              | 说明                                           | 属性                                                                                                                                                                                                                | 方法对应的属性说明                                                       | 注意                         |
|---------------|-----------------------------------------------------------------|----------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------|----------------------------|
| 读文件           | pandas.read_csv()                                               | 从CSV文件中读取数据并返回一个DataFrame对象                  | sep='\t'                                                                                                                                                                                                          | 默认情况 read_csv 函数读取逗号分隔的文件，sep='\t' 显示指明使用制表符分隔                  | 文件以什么来分隔                   |
| 导出            | df.to_csv()                                                     | DataFrame数据导出为CSV文件                          | <ul><li>sep=","</li><li>index=False</li><li>header=True</li></ul>                                                                                                                                                 | <ul><li>指定数据分隔符</li><li>避免写入行索引</li><li>将文件的第一行作为列名</li></ul>   | 路径后面要跟上文件名                 |
| 数据预览          | df.head()                                                       | 返回DataFrame的前几行数据，默认返回前5行                    | 通过传递参数来指定返回的行数： df.head(10)                                                                                                                                                                                       | 指定返回的行数                                                         ||
|               | df.tail()                                                       | 返回DataFrame的最后几行数据，默认返回最后5行                  | 通过传递参数来指定返回的行数： df.tail(10)                                                                                                                                                                                       | 指定返回的行数                                                         ||
|               | df.shape                                                        | 返回DataFrame的行数和列数                            ||
|               | df.columns                                                      | 返回DataFrame的列名                               ||
|               | df.info()                                                       | 显示DataFrame的基本信息，包括列名、非空值的数量和数据类型等           ||
|               | df.dtypes                                                       | 返回DataFrame每列的数据类型                           |||
|               | df.sample(n=5)                                                  | 随机返回DataFrame中的n行数据样本                        |||
| 数据选择和过滤       | df[column]                                                      | 通过列名选择单个列，并返回一个Series对象                      ||||
|               | df[[col1, col2, ...]]                                           | 通过列名选择多个列，并返回一个新的DataFrame对象                 |                                                                                                                                                                                                                   |                                                                 | 有两个中括号 [[]]                |
|               | df.loc[row_indexer, column_indexer]                             | 通过**标签**选择特定的行和列                             |                                                                                                                                                                                                                   |                                                                 | column_indexer参数是列名        |
|               | df.iloc[row_indexer, column_indexer]                            | 通过**位置**选择特定的行和列                             |                                                                                                                                                                                                                   |                                                                 | column_indexer参数位置索引       |
| 数据统计和描述性统计    | df.describe()                                                   | 计算DataFrame中每列的描述性统计信息，例如计数、均值、标准差、最小值、最大值等  | <ul><li>count：非缺失值的数量</li><li>mean：平均值</li><li>std：标准差，表示数据的离散程度</li><li>min：最小值</li><li>25%：第一四分位数，即数据的25th百分位数</li><li>50%：第二四分位数，即数据的50th百分位数，也就是中位数</li><li>75%：第三四分位数，即数据的75th百分位数</li><li>max：最大值</li></ul> |                                                                 | 描述性统计信息数据类型的值              |
|               | df.mean()                                                       | 计算每列的均值                                      |||||
|               | df.median()                                                     | 计算每列的中位数                                     |
|               | df.min()                                                        | 计算每列的最小值                                     |
|               | df.max()                                                        | 计算每列的最大值                                     |
|               | df.std()                                                        | 计算DataFrame中每列的标准差的方法                        | 标准差                                                                                                                                                                                                               | 描述数据分散程度的一种统计指标，表示数据围绕均值的离散程度。它衡量了数据的平均值与每个数据点之间的差异             |
| 数据排序          | df.sort_values(by=column)                                       | 根据指定的列对DataFrame进行排序，默认按升序排列                 | 参数 ascending=False                                                                                                                                                                                                | 降序排序                                                            ||
|               | df.sort_values(by=['col1', 'col2'], ascending=[True, False])    | 根据多个列对DataFrame进行降序排序                        ||||
| 缺失值处理         | df.isnull()                                                     | 返回一个布尔型DataFrame，用于指示每个元素是否为缺失值              ||||
|               | df.dropna()                                                     | 删除包含缺失值的行或列。                                 |||
|               | df.fillna(value)                                                | 将缺失值**替换**为指定的值                              |||
| 数据分组和聚合       | df.groupby(column)                                              | 根据指定的列对数据进行分组。                               ||||
|               | grouped_df.agg(func)                                            | 对分组后的数据应用聚合函数，例如求和、平均值、计数等                   ||
| 数据处理和转换       | df.drop(columns=[col1, col2, ...])                              | 删除指定的列                                       ||
|               | df.rename(columns={'old_name': 'new_name'})                     | 重命名列名                                        ||||
|               | df.astype({'col1': type1, 'col2': type2, ...})                  | 将指定列的数据类型转换为指定的类型                            |||






