# count 统计个数

----

<br>

### 官网
| Scala | 翻译  | Python | 翻译  |
|-------|-----|--------|-----|
|Return the number of elements in the dataset|返回数据集中的元素数|Return the number of elements in this RDD.|返回此 RDD 中的元素数|

<br>

---

### 区别
- 使用方式和底层实现上略有不同
- PySpark中count函数只能应用于DataFrame和Dataset，返回DataFrame或Dataset中行的数量
- Spark中count函数可以应用于RDD和DataFrame，返回RDD或DataFrame中元素的数量


<br>

---



### yield
- Python 中用于创建生成器的关键字，生成器是一种特殊的 <b>迭代器</b>
- 逐个地生成值，而不是返回整个列表或集合
- 用于处理大型或无限序列