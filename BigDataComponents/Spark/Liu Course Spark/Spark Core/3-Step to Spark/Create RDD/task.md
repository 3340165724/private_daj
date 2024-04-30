

---

<br>

## 一切从创建RDD开始

**_官方释义_**

Spark revolves around the concept of a resilient distributed dataset (RDD), which is a fault-tolerant collection of elements that can be operated on in parallel. 

<div class="hint">

- Spark的一切都是围绕RDD展开
- RDD是一个有容错功能的数据集合
- RDD中的元素可以并行地执行运算过程

</div>

There are two ways to create RDDs: parallelizing an existing collection in your driver program, or referencing a dataset in an external storage system, such as a shared filesystem, HDFS, HBase, or any data source offering a Hadoop InputFormat.

<div class="hint">

**有两种创建RDD的方式**

- 在驱动程序中并行化一个已定义的集合
- 使用一个外部的数据集，比如共享文件系统、HDFS、HBase或者任意能提供Hadoop输入格式的数据源

</div>

<br>

**_老刘解读_**

要运行数据运算，首要解决的是装数据容器问题。在分布式计算中，用什么来"装"大量的数据？

Spark创新了一种很好的数据结构作为容器，就是Resilient Distributed Dataset (RDD)/弹性分布式数据集。所以，掌握RDD也是进阶学习Spark的基础，你不用担心它的难度，RDD的内部机制对编程人员几乎是透明的，使用者不用考虑其原理也能用得很好。

RDD既然是一种数据集，说明它可以装任何类型、任何形式的数据，在RDD的编程过程中一定要特别注意它的泛型。

可以通过以下方法创建一个RDD：

- `sc.parallelize()` 生成任意集合范围类型的RDD
- `sc.range()` 按起始位和步长生成一个Long型集合RDD
- `sc.textFile()` 从外部数据源生成RDD

<br>

---

<br>

**_小CASE_**

**1. 练习RDD的几种创建方法，对比它们的泛型，思考分别适用什么场景？**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 