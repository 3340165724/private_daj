# zipWithUniqueld 唯一索引

----

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
                    <li>Zips this RDD with generated unique Long ids.</li>
                    <li>Items in the kth partition will get ids k, n+k, 2*n+k, ..., where n is the number of partitions.</li>
                    <li>So there may exist gaps, but this method won't trigger a spark job, which is different from #zipWithIndex.</li>
                </ul>
            </td>
            <td>
                <ul>
                    <li>生成一个唯一的Long值为该RDD编码。</li>
                    <li>第k个分区的元素将编码为k, n+k, 2*n+k...n为分区号。</li>
                    <li>这种编号方式会存在空隙，与zipWithIndex不同，该方法不会触发Spark Job。</li>
                    <li></li>
                </ul>
            </td>
        </tr>
    </tr>
</table>

----

<br>


### 分析
- 以分区数 n=3、分区索引 k、元素索引 i、UniqueId = i * n + k为例，zipWithUniqueId的编码过程如下表所

| 元素索引/i | 分区数/n=3 | 分区索引/k | 算式/i*n+k | UniqueId | 结果值  |
|--------|---------|--------|----------|----------|---------------|
| 0      | 3       | 0      | 0*3+0    | 0        | (Tom, 0)      |
| 1      | 3       | 0      | 1*3+0    | 3        | (Jerry, 3)    |
| 2      | 3       | 0      | 2*3+0    | 6        | (Marry, 6)    |
| 3      | 3       | 0      | 3*3+0    | 9        | (Lily, 9)     |
| 0      | 3       | 1      | 0*3+1    | 1        | (Matthew, 1)  |
| 1      | 3       | 1      | 1*3+1    | 4        | (Nicholas, 4) |
| 2      | 3       | 1      | 2*3+1    | 7        | (Taylor, 7)   |
| 3      | 3       | 1      | 3*3+1    | 10       | (Nathan, 10)  |
| 0      | 3       | 2      | 0*3+2    | 2        | (Dave, 2)     |
| 1      | 3       | 2      | 1*3+2    | 5        | (Judy, 5)     |
| 2      | 3       | 2      | 2*3+2    | 8        | (Max, 8)      |
| 3      | 3       | 2      | 3*3+2    | 11       | (Tez, 11)     |
| 4      | 3       | 2      | 4*3+2    | 14       | (Vivian, 14)  |
