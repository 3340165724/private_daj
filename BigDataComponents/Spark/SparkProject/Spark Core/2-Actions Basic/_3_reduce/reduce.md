# reduce   质量不变，数量减少

<br>

---

<br>


### 官网
| Scala |官网|python|官网|
|-------|---|---|---|
|	Aggregate the elements of the dataset using a function func (which takes two arguments and returns one). The function should be commutative and associative so that it can be computed correctly in parallel.|	使用函数 func（接受两个参数并返回一个参数）聚合数据集的元素。该函数应该是可交换的和关联的，以便可以并行正确计算。|Reduces the elements of this RDD using the specified commutative and associative binary operator.|使用指定的交换和关联二元运算符减少此 RDD 的元素。|


<br>

---

<br>

### 对RDD数据集的处理过程
![reduceByKey](../../../../../../Image/reduceByKey.png "reduceByKey")

| Scala                                                                                | python                                                                                                        |
|--------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------|
| println(s"sum: ${<br>**sc.parallelize(Seq(1, 2, 3, 4, 5))<br>.reduce(_ + _)**<br>}") | print("sum:%s" % **<br>sc.parallelize([1, 0, 8, 5, 6, 4, 5, 2, 457, 7])<br>.reduce(lambda x, y: x + y)<br>**) |


<br>

---

<br>


### 总结
- 特定函数：输入两个相同类型的参数，输出一个结果
- 注意reduce是Action算子，reduceByKey是Transformation算子
- reduce是一种汇总，并不是简单的"加法"，可以是任意一种实现同类型元素两两"汇总"起来的方法。

