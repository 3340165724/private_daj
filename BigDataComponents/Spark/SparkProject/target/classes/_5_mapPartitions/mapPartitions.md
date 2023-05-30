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
- map：rdd中有多少条数据，就要读取多少次，计算效率比较低
- mapPartitions：rdd中的数据全部读取出来，然后通过迭代器一次进行计算

### 场景
- 在分区中找到最大值

### 缺点
- 会长时间占用内存，可能导致内存溢出

### 作用
- 将待处理的数据“以分区为单位”发送到计算节点进行处理
- 第一个参数 f: Iterator[T] => Iterator[U]
  - 这里的迭代器对象获取的就是每个分区的对象（这个迭代器对象获取的就是分区的值）






