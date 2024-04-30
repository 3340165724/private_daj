

---

<br>

**_官方释义_**

- Return a new dataset formed by selecting those elements of the source on which func returns true.

<div class="hint">

- 把条件表达式中返回`true`的元素保留，返回这些元素的数据集

</div>

<br>

**_老刘解读_**

filter的参数是一个返回为Boolean的函数，表明在该情况下的元素需要被过滤出来并保留。

比如，`Seq(1, 2, 3, 4, 5)`中把选出大于3的数，则条件表达式为`(o => o > 3)`或`(_ > 3)`。

---

<br>

**_小CASE_**

**1. 过滤出以下集合的非空元素，打印输出。**

`Seq("I", "Love", "", "", null, "China", "!")`

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 