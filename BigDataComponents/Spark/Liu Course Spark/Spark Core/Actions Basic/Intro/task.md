

---

<br>

## Action 执行运算

**_官方释义_**

- Return a value to the driver program after running a computation on the dataset.
- The transformations are only computed when an action requires a result to be returned to the driver program. This design enables Spark to run more efficiently.

<div class="hint">

- 执行数据集上的运算，最终返回运算结果
- 只有当Action需要执行结果的时候，Transformations才会执行计算过程。这种设计使得Spark变得更高效。

</div>

<br>

**_老刘解读_**

在[Transformations Basic](course://Spark Core/Transformations Basic/Intro)课程中已经讲了Transformation的Lazy机制，Action相当于Transformation的触发器。

对于任意一组运算，无论有再多的Transformations，都只会有一个Action，所以学习起来要容易得多。

巧记Action算子：

对于一个标准的Spark运算过程，

- 最后一个算子必须是Action
- Action只能出现在最后一步
- 有且仅有一个Action
- Action返回类型不固定，但不是RDD
- Action标志本阶段运算结果，要计算其他过程需要重新生成RDD

<br>

---

<br>

**_小CASE_**

**1. 某菜鸟定义了一系列的Transformation，在运行时始终得不到任何结果输出，最可能是什么原因？**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 