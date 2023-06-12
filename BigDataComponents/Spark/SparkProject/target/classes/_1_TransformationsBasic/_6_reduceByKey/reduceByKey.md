# reduceByKey  分组做运算

##官网
| Scala | 翻译  | python | 翻译  |
|-------|-----|--------|-----|
|When called on a dataset of (K, V) pairs, returns a dataset of (K, V) pairs where the values for each key are aggregated using the given reduce function func, which must be of type (V,V) => V. Like in groupByKey, the number of reduce tasks is configurable through an optional second argument.|在 （K， V） 对的数据集上调用时，返回 （K， V） 对的数据集，其中每个键的值使用给定的 reduce 函数函数函数进行聚合，该函数函数的类型必须是 （V，V） => V。与在 groupByKey 中一样，减少任务的数量可以通过可选的第二个参数进行配置。|Merge the values for each key using an associative and commutative reduce function.|使用关联和交换归约函数合并每个键的值。|

<br/>

---

<br>


## 聚合逻辑
![reduceByKey](../../../../../../../../Image/reduceByKey.png "reduceByKey")

<br/>

---

<br>

# func的等价写法
- Scala
   -  一般写法：reduceByKey((o, o_) => o + o_)
   -  占位符写法：reduceByKey(_ + _)

- python 
   - reduceByKey(lambda x,y: x+y)

<br>

---

<br>

## 总结
- 自动按照key分组，完成组内数据的聚合
- reduceByKey中接收的函数只负责聚合，不会分组，分组是自动按照key来分
- 传入2个参数（类型要一致），返回一个返回值，类型和传入要求一致

<br>

---

<br>


|||||
|---|---|---|----|
|||||
