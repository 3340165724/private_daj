

---

<br>

## sum

**_官方释义_**

- Add up the elements in this RDD.

<div class="hint">

- 将RDD中的元素累加。

</div>

<br>

**_老刘解读_**

当元素为数值类型（如`Int`、`Long`、`Float`、`Double`等）时，`sum`的结果等价于`reduce(_ + _)`，还记得`reduce(_ + _)`的功能吗？

<br>

---

<br>

**_小CASE_**

**1. 使用`reduce(_ + _)`算子再次实现案例的功能。**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 