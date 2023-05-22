# sample

---

<br>

### 官网
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
        <td>
            <ul>
                <li>Return a sampled subset of this RDD.</li>
                <li>Parameters:</li>
                <ul>
                    <li>withReplacement:bool -> can elements be sampled multiple times (replaced when sampled out)</li>
                    <li>fraction:float -> expected size of the sample as a fraction of this RDD’s size without replacement: probability that each element is chosen; fraction must be [0, 1] with replacement: expected number of times each element is chosen; fraction must be >= 0</li>
                    <li>seed:int, optional -> seed for the random number generator</li>
                </ul>
            </ul>
        </td>
        <td>
            <ul>
                <li>返回该RDD的抽样子集</li>
                <li>参数:</li>
                <ul>
                    <li>withReplacement:bool -> 元素可以被多次采样吗(被采样时替换)</li>
                    <li>fraction:float -> 作为该RDD大小的一部分的样本的预期大小，而不需要替换:每个元素被选择的概率;分数必须为[0,1]，带替换:每个元素被选择的预期次数;分数必须>= 0</li>
                    <li>seed:int, optional -> 随机数生成器的种子</li>
                </ul>
            </ul>
        </td>
    </tr>
</table>

<br>

---
 
### 有种子和无种子的区别
- 有种子是只要针对数据源一样，都是指定相同的参数，那么每次抽样到的数据都是一样的 没有种子是针对同一个数据源，每次抽样都是随机抽样


