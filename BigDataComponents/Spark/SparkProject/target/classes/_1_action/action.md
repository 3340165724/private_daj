# Action 执行算子

<br>

---

<br>

### 官网
| Official website| 翻译                                              |
|---|-------------------------------------------------|
|Return a value to the driver program after running a computation on the dataset.| 执行数据集上的运算，最终返回运算结果                          |
|The transformations are only computed when an action requires a result to be returned to the driver program. This design enables Spark to run more efficiently| 只有当Action需要执行结果的时候，Transformations才会执行计算过程。这种设计使得Spark变得更高效。|


### 总结
- Action相当于Transformation的触发器
- 最后一个算子必须是Action
- Action返回类型不固定，但不是RDD




