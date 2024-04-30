

---

<br>

## isEmpty

**_官方释义_**

- true if and only if the RDD contains no elements at all. 
- Note that an RDD may be empty even when it has at least 1 partition. 
- Due to complications in the internal implementation, this method will raise an exception if called on an RDD of Nothing or Null. 
- This may be come up in practice because, for example, the type of parallelize(Seq()) is RDD[Nothing]. (parallelize(Seq()) should be avoided anyway in favor of parallelize(Seq\[T\]()).)

<div class="hint">

- 只有当且仅当RDD没有任何元素时才返回`true`
- 当RDD包含至少一个分区的时候也有可能是空的
- 由于内部实现的复杂性，在调用`Nothing`或`Null`类型的RDD时会引发异常。
- 在实际情况中可能发生，比如，`parallelize(Seq())`的类型是`RDD[Nothing]`。在使用`parallelize(Seq[T]())`时应该极力避免`parallelize(Seq())`情况。

</div>

<br>

---

<br>

**_小CASE_**

**1. 使用`Seq[Int]`类型，对案例代码几种情况进行判断，观察结果并总结。**

<br>

---

_教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn_

© 2021 Matthew Liu，All rights reserved. 