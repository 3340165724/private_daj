# isEmpty 判断是不是空的


---

<br>

### 官网
<table>
    <tr>
        <th>Scala</th>
        <th>翻译</th>
        <th>python</th>
        <th>翻译</th>
    </tr>
    <tr>
        <td>
            <ul>
                <li>
                    true if and only if the RDD contains no elements at all.
                </li>
                 <li>
                    Note that an RDD may be empty even when it has at least 1 partition.
                </li>
                <li>
                    Due to complications in the internal implementation, this method will raise an exception if called on an RDD of Nothing or Null.
                </li>
                 <li>
                   This may be come up in practice because, for example, the type of parallelize(Seq()) is RDD[Nothing]. (parallelize(Seq()) should be avoided anyway in favor of parallelize(Seq[T]()).)
                </li>
            </ul>
        </td>
        <td>
            <ul>
                <li>
                   只有当且仅当RDD没有任何元素时才返回true
                </li>
                <li>
                   注意，当RDD包含至少一个分区的时候也有可能是空的
                </li>
                <li>
                   由于内部实现的复杂性，在调用Nothing或Null类型的RDD时会引发异常。
                </li>
                <li>
                    在实际情况中可能发生，比如，parallelize(Seq())的类型是RDD[Nothing]。在使用parallelize(Seq[T]())时应该极力避免parallelize(Seq())情况
                </li>
            </ul>
        </td>
        <td>
            Returns true if and only if the RDD contains no elements at all.
        </td>
        <td>
            当且仅当RDD不包含任何元素时返回true
        </td>
    </tr>
</table>

### 总结
- 只要集合中有元素，值为null也会返回false
- 不能对null值进行序列化


