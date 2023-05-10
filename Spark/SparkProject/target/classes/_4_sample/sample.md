# sample

---

<br>

### 官网

| Scala | 翻译  |Python|翻译|
|-------|-----|----|----|
|Sample a fraction fraction of the data, with or without replacement, using a given random number generator seed.||||

<table>
    <tr>
        <th>Scala</th>
        <th>翻译</th>
        <th>Python</th>
        <th>翻译</th>
    </tr>
    <tr>
        <td>
            <ul>
                <li>Sample a fraction of the data, with or without replacement, using a given random number generator seed.</li>
                <ul>
                    <li>withReplacement – can elements be sampled multiple times (replaced when sampled out)</li>
                    <li>fraction – expected size of the sample as a fraction of this RDD's size without replacement: probability that each element is chosen; fraction must be [0, 1] with replacement: expected number of times each element is chosen; fraction must be greater than or equal to 0</li>
                    <li>seed – seed for the random number generator</li>
                </ul>
            </ul>
        </td>
        <td>
            <ul>
                <li>Sample，样本数据，是一个小的数据片段，元素可重复或不重复，根据指定的随机因子生成</li>
                <ul>
                    <li>withReplacement 一个元素是否能被多次取样</li>
                    <li>fraction 如果取样个数等于这个RDD的size且取样可重复时：所有的元素都会被取到；如果fraction值范围在[0, 1]且不重复时：只取样目标的个数；fraction值必须>=0</li>
                    <li>seed 随机生成数作为取样因子</li>
                </ul>
            </ul>
        </td>
        <td></td>
        <td></td>
    </tr>
</table>




