# cogroup 共组

---------

<br>

### 官网
|||||
|---|----|----|----|
|When called on datasets of type (K, V) and (K, W), returns a dataset of (K, (Iterable , Iterable )) tuples. This operation is also called groupWith.|应用在(K, V)、(K, W)键值类型的数据集，返回(K, (Iterable , Iterable ))元组类型的数据集。这个算子也叫 groupWith|For each key k in self or other, return a resulting RDD that contains a tuple with the list of values for that key in self as well as other.|对于self或other中的每个键k，返回一个结果RDD，该RDD包含一个元组，其中包含self和other中该键的值列表|




### 分析
![join vs cogroup](../../../../../../../../Image/join vs cogroup.png "join vs cogroup")


### 总结
- cogroup其实是一种预分组操作。
- 对于输入的(K, V)键值对类型的RDD，根据K相同的元素聚合到一起，与直接分组不同的是，即使该组没有元素，也会产出一个空的的集合