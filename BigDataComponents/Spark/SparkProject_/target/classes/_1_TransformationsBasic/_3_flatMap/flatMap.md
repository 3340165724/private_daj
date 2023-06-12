# flatMap

---

<br>


### 官网
| Scala                                                                                                                                | 翻译     | python | 翻译  |
|--------------------------------------------------------------------------------------------------------------------------------------|--------|--------|-----|
| Similar to map, but each input item can be mapped to 0 or more output items (so func should return a Seq rather than a single item). | 与 map 类似，但每个输入项可以映射到 0 个或多个输出项（因此 func 应返回 Seq 而不是单个项）。 |Return a new RDD by first applying a function to all elements of this RDD, and then flattening the results.| 通过首先将函数应用于此 RDD 的所有元素，然后平展结果来返回新的 RDD。    |

<br>

---

<br>

### 使用场景
> 用于拆分单词

<br>

---

<br>

### flatMap参数
| Scala                                                                                              | Python                                                     |
|----------------------------------------------------------------------------------------------------|------------------------------------------------------------|
| sc.parallelize(Seq("hello world", "goodbye world"))<br>.flatMap(_.split(" "))<br>.foreach(println) | rdd.flatMap(<br>lambda x: x.split(' '))<br>.foreach(print) |


### 作用
- 将集合中的数据进行扁平化处理，将集合内容的数据取出来返回一个新的RDD
- 