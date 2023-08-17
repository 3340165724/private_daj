# zip 拉链

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
                <li>Zips this RDD with another one, returning key-value pairs with the first element in each RDD, second element in each RDD, etc.</li>
                <li>Assumes that the two RDDs have the same number of partitions and the same number of elements in each partition (e.g. one was made through a map on the other).</li>
            </ul>
        </td>
        <td>
            <ul>
                <li>与mapPartitions相似，但这个算子多了一个整数值作为分区的索引。当处理一个<T>类型的RDD时，传入的函数类型必须是(Int, Iterator< T >) => Iterator< U >。</li>
                <li>要使用拉链，必须确保两个RDD的分区数和每个分区的元素个数一致（比如，一个由另一个RDD通过map生成的新RDD，两个RDD长度就是一致的)  </li>
            </ul>
        </td>
        <td>Zips this RDD with another one, returning key-value pairs with the first element in each RDD second element in each RDD, etc.</td>
        <td>与mapPartitions相似，但这个算子多了一个整数值作为分区的索引。当处理一个类型的RDD时，传入的函数类型必须是(Int, Iterator) => Iterator</td>
    </tr>
</table>




### 拉链函数
- zip 拉链，是Spark RDD的一种特有操作，功能是将两个RDD的元素按照索引值一一映射，形成新的(K, V)Tuple类型的RDD
- 例如：


| rdd_name | rdd_index | rdd_index.zip(rdd_name) |
|----------|-----------|-------------------------|
| Tom      | 1         | (4,Lily)                |
| Jerry    | 2         | (7,Taylor)              |
| Marry    | 3         | (1,Tom)                 |
| Lily     | 4         | (8,Nathan)              |
| Matthew  | 5         | (5,Matthew)             |
| Nicholas | 6         | (9,Dave)                |
| Taylor   | 7         | (2,Jerry)               |
| Nathan   | 8         | (6,Nicholas)            |
| Dave     | 9         | (10,Judy)               |
| Judy     | 10        | (11,Max)                |
| Max      | 11        | (12,Tez)                |
| Tez      | 12        | (13,Vivian)             |
| Vivian   | 13        | (3,Marry)

### 使用原则
- 两个RDD的元素类型可以一致或不一致；
- 两个RDD的分区数量必须一致；
- 两个RDD的每个分区内的元素个数必须一致。

### 注意
- rdd位置的顺序不一样，结果也会不一样
- rdd_index.zip(rdd_name) ==》（rdd_index，rdd_name）
- 谁在前谁作为key