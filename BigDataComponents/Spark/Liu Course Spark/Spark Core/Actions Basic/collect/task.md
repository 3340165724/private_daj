

---

<br>

## collect

**_官方释义_**

- Return all the elements of the dataset as an array at the driver program. This is usually useful after a filter or other operation that returns a sufficiently small subset of the data.

<div class="hint">

- 把所有数据集的元素作为一个集合返回到Driver程序。通常用于在一个filter或其他算子执行之后高效地得到一个小的子集。

</div>

<br>

**_老刘解读_**

**collect**算子，简而言之就是把分布在集群上的RDD结果"回收"到本地。RDD是一种数据集，相当于集合类型，在本地也是集合。

RDD回收到本地以后，就成了一个本地的集合，可以执行任何集合相关操作。

在Spark常出现的`Driver`，其实就是指**本地应用程序**，有关Driver的讲解会在后续专门讲解。

<br>

---

<br>

**_小CASE_**

**1. 拆解案例代码的实现过程，分析每一步实现了什么功能。注意观察他们的输入、输出类型。**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 