# series  序列 (一维数据集)

----------

<br>

### 概述
- 
- Series是Pandas中的一维数据结构
- Series可以存储不同类型的数据，类似于Python内置的列表，表示DataFrame的每一列 

### 创建
- 自动索引：pandas.Series(["zhangsan",25])
- 手动索引：pandas.Series(["zhangsan",25],index=["name","age"])


### 属性

| Series属性           | 说明            |
|--------------|---------------|
| loc          | 使用索引值取子集      |
| iloc         | 使用索引位置取子集     |
| ix           | 使用索引值或索引位置取子集 |
| dtype或dtypes | Series内容的类型   |


### 方法
| Series方法        | 说明                             |
|-----------------|--------------------------------|
| append          | 连接两个或多个Series                  |
| corr            | 计算于另一个Series的相关系数              |
| cov             | 计算与另一个Series的协方差               |
| describe        | 计算概括统计量                        |
| drop_duplicates | 返回一个不含重复项的series               |
| equals          | 判断两个Series是否有相同元素              |
| get_values      | 获取Series的值，功能和values属性相同       |
| hist            | 绘制直方图                          |
| isin            | 逐个检查Series中的每一个元素是否存在于参数指定的序列中 |
| min             | 返回最**小值**                      |
| max             | 返回**最大值**                      |
| mean            | 返回**算术平均值**                    |
| median          | 返回中位数**中位数**                   |
| mode            | 返回**众数**                       |
| quantile        | 返回指定位置的**四分位数**                |
| replace         | 用指定值**代替**Series中的值            |
| sample          | 返回Series的随机采样值                 |
| sort_values     | 对**值**进行**排序**                 |
| to_frame        | 把Series转换为DataFrame            |
| transpose       | 返回转置矩阵                         |
| unique          | 返回由唯一值组成的numpy.ndarray         |


- 获取到某个列的值：ages =df['age']


### 操作自动对齐和向量化（广播）
- 同长度向量
- 向量和整数（标量）运算
- 不同长度向量间的运算
- 带有常见索引标签的向量（自动对齐）



