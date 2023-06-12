# subtract 差集
<br>

---

<br>

### 官网
| Scala | 翻译  | python | 翻译  |
|-------|-----|--------|-----|
|Return an RDD with the elements from this that are not in other.|返回一个 RDD，其中包含其他元素中没有的元素。|Return each value in self that is not contained in other.|返回self中不包含在other中的每个值|

<br>

---

<br>

### 分析
![gather](../../../../../../Image/gather.png "gather")


### 总结
- 返回一个包含只存在于第一个 RDD 中而不存在于第二个 RDD 中的元素的新 RDD
- .mkString(", ")：将集合（如 Array、List、Seq 等）按指定分隔符拼接成一个字符串