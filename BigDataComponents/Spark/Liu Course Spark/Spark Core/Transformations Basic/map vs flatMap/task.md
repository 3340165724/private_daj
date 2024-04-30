

---

<br>

**_老刘解读_**

学习Spark RDD时，最初的两个算子总能让菜鸟一头雾水。其实只要自主推理，很快能发现`map`与`flatMap`明显的不同。

是在WordCount案例中，对每一行输入分词时，要用`flatMap(_.split(" "))`对切分过的词进行再次打散，以便后续进行全局统计。

<br>

---

<br>

**_小CASE_**

**1. 试一试，WordCount案例中，如果使用`map(_.split(" "))`分词会出现什么情况？**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 