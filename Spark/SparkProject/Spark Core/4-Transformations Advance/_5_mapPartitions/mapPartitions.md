# mapPartitions 分区为单位

---

<br>


### 官网
| Scala | 翻译  | python | 翻译  |
|-------|-----|--------|-----|
|Similar to map, but runs separately on each partition (block) of the RDD, so func must be of type Iterator<T> => Iterator<U> when running on an RDD of type T.|与map相似，但是是在RDD的每一个分区（块）上运行任务，所以，当处理一个<T>类型的RDD时，传入的函数必须是Iterator<T> => Iterator<U>形式。|Return a new RDD by applying a function to each partition of this RDD.|通过对RDD的每个分区应用一个函数返回一个新的RDD|

<br>

---

### 与map的区别
- 可以简单地把map看作对数据一条一条做处理，mapPartitions是先分成一份一份，在每一份中按条处理数据。






