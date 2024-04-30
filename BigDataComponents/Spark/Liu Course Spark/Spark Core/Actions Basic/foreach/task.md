

---

<br>

## foreach 

**_官方释义_**

- Run a function func on each element of the dataset. This is usually done for side effects such as updating an Accumulator or interacting with external storage systems.

<div class="hint">

- 为每一个数据集的元素执行一个函数。通常用作有副作用的用途，比如更新一个累加器或与外部系统交互。

</div>

<br>

**_老刘解读_**

细心的你可能发现，在之前的案例中，很多案例的最终都会使用`foreach`遍历打印输出。没错！`foreach`就是一个典型的Action。

实际情况`foreach`通常与`println`搭配使用：`foreach(println)`。

如果你学过一点编程，一定知道for循环，其实`foreach`就是`for`循环的一个变种。

`foreach`是一种函数式的内循环写法，不只是Spark有这个算子，Scala源生语言也可以使用。虽然写法一致，但是他们遍历的对象是不同的。

<br>

---

<br>

**_小CASE_**

**1. 创建一些集合或RDD，用`foreach`遍历它们，对比`foreach`和普通的`for`循环有什么不同。**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 