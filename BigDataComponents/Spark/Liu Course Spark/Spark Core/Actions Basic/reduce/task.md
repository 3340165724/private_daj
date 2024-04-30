

---

<br>

## `reduce(func)`

**_官方释义_**

- Aggregate the elements of the dataset using a function func (which takes two arguments and returns one). The function should be commutative and associative so that it can be computed correctly in parallel.

<div class="hint">

- 特定函数：输入两个相同类型的参数，输出一个结果
- 用这个函数聚合数据集的元素
- 这个函数应该是可交换和可结合的，才能正确地参与并行化计算

</div>

<br>

**_老刘解读_**

注意`reduce`是Action算子，`reduceByKey`是Transformation算子。

`reduce`是一种汇总，并不是简单的"加法"，可以是任意一种实现同类型元素两两"汇总"起来的方法。

`reduce`详解，回顾课程[reduceByKey](course://Spark Core/Transformations Basic/reduceByKey)

<br>

回顾"**质量不变，数量减少**"，对于一个`Int`集合`Seq(1, 2, 3, 4, 5)`：

- **质量不变**：1 + 2 + 3 + 4 + 5 = 15，左边式子和右边的数"质量"是一致的
- **数量减少**：左边5个数，右边1个数

类似这样的过程就是`reduce`。

<br>

---

<br>

**_小CASE_**

**1. 使用`reduce`算子合并指定RDD元素，在Quiz代码中完成。**

<div class="hint">

集合元素合并可使用`.++()`方法。

</div>

**2. 说明`reduce`与`reduceByKey`的异同？**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 