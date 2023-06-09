### 技术点
> 读取文件
>> file = open("../../Resource/address.csv", encoding="UTF-8").read()
>>
> 注意编码
>> 问题
>>> 运行时出现： 'gbk' codec can't decode byte 0xa2 in position 400: illegal multibyte sequence
>>
>> 解决
>>> open()有一个设置编码的参数，encoding="UTF-8"
> 

> 读取到一行中的单个数据，并存到字符串中
>> 创建空字符串
>>> datas = ""
>>>
>> 赋值
>>> datas += item
>  


> 去除特殊符号
>> 替换
>>> re.sub(pattern, replacement, string)方法，将字符串中所有匹配到的模式替换成指定的字符串。
>>
>>
>>匹配字符串
>>>re.search(pattern, string)方法,返回一个匹配对象，可以使用.group()方法获取匹配到的字符串
>>
>>查找所有符合模式的子字符串
>>>re.findall(pattern, string)方法，返回一个包含所有匹配字符串的列表
>>
>>切割字符串
>>>使用re.split(pattern, string)方法，将字符串按照匹配到的模式进行分割，返回一个分割后的列表
> 

### 正则表达式
>字符匹配
>> . 匹配任意一个字符，除了换行符\n
> 
>> [] 匹配括号内的任意一个字符，如[abc]表示匹配a、b或c
> 
>> [^] 匹配除了括号内的任意一个字符，如[^abc]表示匹配除了a、b和c以外的任
> 
>
> 意字符
>> \d 匹配任意一个数字，相当于[0-9]
> 
>> \D 匹配任意一个非数字字符，相当于[^0-9]
> 
>> \w 匹配任意一个字母或数字或下划线，相当于[a-zA-Z0-9_]
> 
>> \W 匹配任意一个非字母、数字或下划线字符，相当于[^a-zA-Z0-9_]
> 
>> \s 匹配任意一个空白字符，如空格、制表符\t、换行符\n等
> 
>> \S 匹配任意一个非空白字符
>
> 重复匹配
>>     * 匹配前面的字符零次或多次，如a*表示匹配零个或多个a
> 
>>     + 匹配前面的字符一次或多次，如a+表示匹配一个或多个a
> 
>> ? 匹配前面的字符零次或一次，如a?表示匹配零个或一个a
> 
>> {m} 匹配前面的字符恰好m次，如a{3}表示匹配恰好3个a
> 
>> {m,n} 匹配前面的字符至少m次，至多n次，如a{1,3}表示匹配1个到3个a\
> 
> 定位符
>>  ^ 匹配字符串的开头，在[]中使用表示匹配除了括号内的任意一个字符
> 
>> $ 匹配字符串的结尾
> 
>> \b 匹配单词的边界，如\bhello\b可以匹配单独的hello单词，而不是在其他单词中出现的hello
> 
>> \B 匹配非单词边界的位置
