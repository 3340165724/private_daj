

---

<br>

## `reduceByKey(func, [numPartitions])`

**_官方释义_**
 
- When called on a dataset of (K, V) pairs, returns a dataset of (K, V) pairs where the values for each key are aggregated using the given reduce function func, which must be of type (V,V) => V. Like in groupByKey, the number of reduce tasks is configurable through an optional second argument.

<div class="hint">

- 使用一个(K, V)/键值对数据集时，使每一个相同的Key的Values按照给定的函数作聚合运算，然后返回一个新的(K, V)/键值对数据集
- 聚合运算的函数必须是`(V, V) => V`形式，即输入相同V的Tuple2类型，输出一个V类型
- reduce的任务数可以在第二个可选参数位置被指定

</div>

<br>

**_老刘解读_**

`reduce`是数理中常用的运算，遵循"**质量不变，数量减少**"的原则，有关`reduce`运算会在后续讲解。

特别注意`reduceByKey`中`func`的等价写法：

- 一般写法：`reduceByKey((o, o_) => o + o_)`
- 占位符写法：`reduceByKey(_ + _)`

因为`reduce`操作至少需要两个元素，所以**函数传入两个同类型的参数，实现两两运算**。

`(o, o_) => o + o_`代表一个`reduce`过程为累加，即两两元素的加法操作，Scala语言允许在特定的情况下用占位符`_`指代相同类型的两个参数，即`(_ + _)`。

明白了`reduce`，还要注意`byKey`，说明这个算子处理的元素是(K, V)类型的，**Key相同的元素才`reduce`，简单来说就是分组做运算**。

<br>

---

<br>

**_小CASE_**

**1. 自主分析`reduceByKey`的过程，思考如果没有`byKey`，只有`reduce`时会发生什么？**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 