# map 来一个处理一个

---

<br>

### 官网

| Official website                                                                | 翻译   |
|---------------------------------------------------------------------------------|-------------|
| Return a new distributed dataset formed by passing each element of the source through a function func   | 根据给定的函数，处理数据源的每一个元素，返回一个新的数据集形式（RDD）       |

<br>

---

<br>

### 使用场景

- 用于数理计算的场景，输入输出类型相同

<br>

---

<br>


### map参数
| Scala                                                               | python                                                                      |     
|---------------------------------------------------------------------|-----------------------------------------------------------------------------|
| sc.parallelize(List(1,2,3,5,6,8))<br>.map(_ * -1)<br> .foreach(println) | rdd1 = sc.parallelize([1, 2, 5, 8, 9])<br>.map(lambda x: x * 10)<br>.foreach(print) |


### 数据类型转换
- 将字符串集合转化为整形集合
- data.map(_.toInt)