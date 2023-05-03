# collect 收集

---

<br>

### 官网
| Scala                                                                                                                                                                                    | 翻译                                                  | python                                              | 翻译                    |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------|----|-----------------------|
| Return all the elements of the dataset as an array at the driver program. This is usually useful after a filter or other operation that returns a sufficiently small subset of the data. | 把所有数据集的元素作为一个集合返回到Driver程序。通常用于在一个filter或其他算子执行之后高效地得到一个小的子集。|Return a list that contains all the elements in this RDD.| 返回一个包含此 RDD 中所有元素的列表。 |



<br>

----

### 分析
- 将RDD或DataFrame中的数据收集到driver节点中，以便在本地进行处理
- collect算子，简而言之就是把分布在集群上的RDD结果"回收"到本地
- RDD是一种数据集，相当于集合类型，在本地也是集合
- RDD回收到本地以后，就成了一个本地的集合，可以执行任何集合相关操作
- 在Spark中出现的Driver，其实就是指本地应用程序



