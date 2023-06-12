# keys values 键 值

---

<br>

### 官网
- Return an RDD with the keys/values of each tuple.
  - 返回一个包含每个tuple（元组的键值）的RDD。

<br>

---

### 理解
-   要使用keys和values算子，必须构造一个[(K, V)]类型的RDD，即RDD的类型只能是两个元素的Tuple2类型(K, V)。 在一个二元的Tuple2中，第一、二个元素就能分别代表K和V

