# zipWithIndex 元素索引

---

<br>

### 官网
<table>
    <tr>
        <th>scala/python</th>
        <th>翻译</th>
    </tr>
    <tr>
        <tr>
            <td>
                <ul>
                    <li>Zips this RDD with its element indices.</li>
                    <li>The ordering is first based on the partition index and then the ordering of items within each partition.</li>
                    <li>So the first item in the first partition gets index 0, and the last item in the last partition receives the largest index.</li>
                    <li>This is similar to Scala's zipWithIndex but it uses Long instead of Int as the index type.</li>
                    <li>This method needs to trigger a spark job when this RDD contains more than one partitions.</li>
                    <li>Note：Some RDDs, such as those returned by groupBy(), do not guarantee order of elements in a partition. The index assigned to each element is therefore not guaranteed, and may even change if the RDD is reevaluated. If a fixed ordering is required to guarantee the same index assignments, you should sort the RDD with sortByKey() or save it to a file.</li>
                    <li></li>
                </ul>
            </td>
            <td>
                <ul>
                    <li>zip当前RDD和其本身的索引值。</li>
                    <li>索引的顺序基于分区的顺序以及元素在分区内部的顺序。</li>
                    <li>第一个分区的第一个元素索引为0，末位元素为最后的分区的索引的最大值。</li>
                    <li>该算子与Scala的zipWithIndex相同，但是索引使用Long类型代替Int类型</li>
                    <li>当RDD有多个分区时，该算子会触发一个Spark Job任务。</li>
                    <li>注意：有些rdd，比如groupBy()返回的rdd，不保证分区中元素的顺序。因此，不能保证分配给每个元素的索引，甚至可能在重新评估RDD时发生变化。如果需要固定的排序来保证相同的索引分配，则应该使用sortByKey()对RDD进行排序，或者将其保存到文件中。</li>
                </ul>
            </td>
        </tr>
    </tr>
</table>


### zongjie
- 自动从0开始索引

