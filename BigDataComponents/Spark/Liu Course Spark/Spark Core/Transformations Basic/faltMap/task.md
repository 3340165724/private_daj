

---

<br>

## `flatMap(func)`

**_官方释义_**

- Similar to map, but each input item can be mapped to 0 or more output items (so func should return a Seq rather than a single item).

<div class="hint">

- `flatMap`与`map`相似，但是每一个输出项可能被映射为0个或多个输出项，所以func/操作函数必须是返回Seq（序列）类型而不是返回单个元素

</div>

**_老刘解读_**

有一个箱子，里边有若干个袋子，每个袋子装着若干个苹果，也有一些袋子是空的，执行以下过程

1. 把每个袋子的苹果切块
2. 把袋子"扔掉"，切成的块收集放到一个新的容器，可以是箱子、碗、罐子

`flatMap`由两个步骤组成，先`map`再`flatten`（摊平）。注意`摊平`的处理，相当于丢弃了一个里层的容器，把处理完的元素从里层容器"倒出来"。

<br>

---

<br>

**_小CASE_**

**1. `flatMap`的输入元素在什么情况下输出0个项？**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 