# Transformation

---
<br>

## Transformation 转换操作

**官方释义**

| Official website      | 翻译                                                    |
| ----------- |-------------------------------------------------------|
| RDDs support two types of operations: transformations, which create a new dataset from an existing one, and actions, which return a value to the driver program after running a computation on the dataset      | RDD 支持两种类型的操作：转换（从现有数据集创建新数据集）和操作（在对数据集运行计算后向驱动程序返回值） |
| All transformations in Spark are lazy, in that they do not compute their results right away   | Spark 中的所有 Transformation 都是惰性的,因为它们不会立即计算结果          |
|   they just remember the transformations applied to some base dataset (e.g. a file). The transformations are only computed when an action requires a result to be returned to the driver program            |     他们只记住应用于某些基本数据集（例如文件）的转换。仅当操作需要将结果返回到驱动程序时，才会计算转换      |

