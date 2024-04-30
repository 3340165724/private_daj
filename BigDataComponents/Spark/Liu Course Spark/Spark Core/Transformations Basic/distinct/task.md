

---

<br>

## `distinct([numPartitions]))`

**_官方释义_**

- Return a new dataset that contains the distinct elements of the source dataset.

<div class="hint">

- 对原数据集去重后返回一个新的数据集。

</div>

<br>

**_老刘解读_**

将数据集中的重复元素仅保留一个，很好理解。

<br>

---

<br>

**_小CASE_**

**1. 参照案例，完成以下集合的元素去重，观察结果。**

`Seq("I", "Love", "love," "Love", "China", "China", "CHINA")`

<br>

**2. 使用`distinct`算子，实现WordCount案例中的元素去重。**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 