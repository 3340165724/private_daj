# cartesian 笛卡尔积

<br>

---

<br>

### 官网
| Scala | 翻译  | python | 翻译  |
|-------|-----|--------|-----|
|When called on datasets of types T and U, returns a dataset of (T, U) pairs (all pairs of elements).|在Tuple[T, U]类型的数据集上调用，返回Tuple[T, U]类型的键值对（所有元素两两配对|Return the Cartesian product of this RDD and another one, that is, the RDD of all pairs of elements (a, b) where a is in self and b is in other.|返回此 RDD 和另一个 RDD 的笛卡尔乘积，即所有元素对 （a， b） 的 RDD，其中 a 在自身中，b 在 other 中。|

### 总结
- 笛卡尔积是一种基本的行列运算，使前后两个数据集两两对应。
- 对于RDD1（数据量M）和RDD2（数据量N），做笛卡尔积的数据量为M*N。
- 使用cartesian算子做笛卡尔积，要注意先后之分导致结果形式不同，但是数量是相同的。