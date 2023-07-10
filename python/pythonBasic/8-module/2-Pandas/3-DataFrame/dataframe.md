# DataFrame  数据框（二维数据表）

------

<br>


### 分析
- 在DataFrame中，每一列的数据类型（dtype）必须相同
- 由Series对象组成的字典

### 注意
- 输出的结果顺序并非固定不变，可以使用columns参数指定列顺序


### 方法
| 方法                                | 执行结果             |
|-----------------------------------|------------------|
| df[column_name]                   | 得到单列的指           |
| df[column1,colume2,...]           | 多列               |
| df.loc[row_label]                 | 使用行索引标签（行名）获取数据行 |
| df.loc[row_label1，row_label2,...] | 使用索引标签获取多行数据     |
| df.iloc[row_num]                  | 使用行号获取数据行        |    
| df[start:stop:step]               | 使用切片方法获取数据       |



