# filter 过滤出想要的数据

| Scala | 翻译  | python | 翻译  |
|-------|-----|--------|-----|
| Return a new dataset formed by selecting those elements of the source on which func returns true      |  把条件表达式中返回true的元素保留，返回这些元素的数据集   |    Return a new RDD containing only the elements that satisfy a predicate.    |  返回一个新的 RDD，仅包含满足谓词的元素。   |


# 总结

- filter的参数是一个返回为Boolean的函数
- 可能出现数据倾斜
  -  符合规则的数据保留，不符合规则的数据丢弃当数据进行筛选过滤后，分区不变，但是分区内的数据可能不均衡
