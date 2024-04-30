## I/O 主要有三种类型

> 文本 I/O、二进制 I/O 和原始 I/O

<br>

---

<br>

### 文本 I/O

```
创建文本流的最简单方法是使用 open（），可选 指定编码：
f = open([file_name] [,access_mode] [, buffering])

内存中的文本流也可用作 StringIO 对象
f = io.StringIO("some initial text data")
```

> open()是Python内部设置函数，用于打开一个文件，并返回一个文件对象
>> file_name：文件名
>>
>>access_mode：文件打开模式
>>> r：以只读方式打开文件
>>>
>>>w：打开一个文件只用于写入
>>>
>>> w+：用于读写，如果有则直接覆盖
>>>
>>>a+：打开一个文件用于读写
>>>
>>> a：打开一个文件用于追加
>>
>>buffering：文件的编码方式

