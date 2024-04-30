

---

<br>

## `map(func)`

**_官方释义_**

- Return a new distributed dataset formed by passing each element of the source through a function func.

<div class="hint">

- 根据给定的函数，处理数据源的每一个元素，返回一个新的数据集形式（RDD）

</div>

**_老刘解读_**

有一箱苹果，执行以下任意一个过程，

- CASE1 把每一个苹果削皮：输入苹果类型，输出苹果类型
- CASE2 把每一个苹果换成梨：输入苹果类型，输出梨类型
- CASE3 把每一个苹果贴个数字1的标签：输入苹果类型，输出Tuple[(苹果，Int)]类型

完成以上操作后用一个新的箱子封装输出结果。

<br>

---

<br>

**_小CASE_**

**1. `map`的原意是一对一映射，请从这个角度深入思考`map`算子，哪些场景可以使用映射？**

**2. 在`Quiz`中补全代码，实现数字的字符串转化为整形数字。**

<div class="hint">

字符串转整形用 toInt 方法

</div>

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 