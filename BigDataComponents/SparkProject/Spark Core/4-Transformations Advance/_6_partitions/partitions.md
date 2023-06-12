# partitions 分区

### 官网
| Scala | 翻译  | python | 翻译 |
|-------|-----|--------|----------|
|Get the array of partitions of this RDD, taking into account whether the RDD is checkpointed or not.|以数组形式获取当前RDD的分区，需要考虑RDD是否为一个checkpoint/检查点。|||


### 指定分区
- 预设分区（并行度） 
  - 在初始化SparkSession时，与.master("local[3]")的参数设置有关，参数决定了RDD的默认并行度defaultParallelism
- 创建RDD时手动设置分区
  - 根据初始化RDD的方法，def parallelize[T: ClassTag](seq: Seq[T], numSlices: Int = defaultParallelism): RDD[T]
  - 第二个可选参数numSlices: Int = defaultParallelism可以看出，RDD的分区数等于并行度值defaultParallelism，即1中local[3]参数值

### 总结
- partitions是一个Action算子，可以直接得到分区的列表。
- partitions在IDE开发环境中实现可能与Shell环境不同，本节以开发环境为准讨论分区情况。

