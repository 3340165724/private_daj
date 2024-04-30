

---

<br>

## Transformation 转换操作

**_官方释义_**

- Create a new dataset from an existing one.

- All transformations in Spark are **lazy**, in that they do not compute their results right away.

<div class="hint">

- Transformation可以由一个数据集生成一个新的数据集
- 所有的Transformation都具有**Lazy/懒加载机制**，它们不会立即计算结果

</div>

**_老刘解读_**

Transformation描述了一组特定的RDD转换过程，它的输入输出都是RDD[_]类型（但RDD的泛型可能不同）。这个过程描述之后并不会立即被执行，要"等待"一个触发条件统一执行。所以说，一组连续的Transformation要么全部执行或者都不执行，不存在执行到一半的情况。Spark的这种Lazy机制，避免了数据处理过程中的空操作，减少资源浪费。

Transformation是操作RDD的必备技能，接下来将带大家深入浅出。

<br>

---

<br>

**_小CASE_**

**1. 思考Transformation为什么设计成Lazy机制，有什么好处？**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 