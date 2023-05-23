# mapPartitionWithIndex 知道分区数

----

<br>

| Scala | 翻译  | python | 翻译  |
|-------|-----|--------|-----|
|Similar to mapPartitions, but also provides func with an integer value representing the index of the partition, so func must be of type (Int, Iterator<T>) => Iterator<U> when running on an RDD of type T.|与mapPartitions相似，但这个算子多了一个整数值作为分区的索引。当处理一个<T>类型的RDD时，传入的函数类型必须是(Int, Iterator<T>) => Iterator<U>。|Return a new RDD by applying a function to each partition of this RDD, while tracking the index of the original partition.|通过对RDD的每个分区应用一个函数返回一个新的RDD，同时跟踪原始分区的索引。|

### 分析
- mapPartitionsWithIndex(func)与mapPartitions(func)的内部处理逻辑是一致的，mapPartitionsWithIndex的函数参数中多了一个分区索引，可以应对某些需要知道当前数据位于哪个批次的情况，partition是分组，索引就是组号。 所以，它的实现方式也是一样的，需要遍历内层的迭代器。
- 可以跟踪正在处理的分区


 