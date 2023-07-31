# string 字符串

### 常用字符串
 - upper()：将字符串中的**所有字母转换为大写**
 - lower()：将字符串中的**所有字母转换为小写**
 - strip()：删除字符串开头和结尾的空白字符
 - replace(old, new)：用新字符串替换旧字符串
 - split()：将字符串**拆分**为一个字符串列表
 - string.replace(old, new)：将字符串中的所有 old 替换为 new。
 - string.count(substring)：返回子字符串在字符串中出现的次数。
 - string.find(substring)：返回子字符串在字符串中第一次出现的位置，如果没有找到则返回 -1。
 - string.startswith(prefix)：检查字符串是否以指定的前缀开头。
 - string.endswith(suffix)：检查字符串是否以指定的后缀结尾。
 - string.join(iterable)：将可迭代对象中的元素连接成一个字符串，每个元素之间用字符串分隔符连接。


<br>

### 指定输出
- 输出第一个字符：my_string[0]
- 输出最后一个字符：my_string[-1]
- 截取：my_string[0:5]
- 从下标为7的字符开始输出：my_string[7:]


<br>


### 连接字符串
- 用运算符 “ + ”号连接
- join
  - 只能连接在一个列表中的字符串
  - 两个变量的字符串不能用join



<br>

### 格式化输出
- %s：字符串占位符
- %d：整数占位符
- ”{}“.format(参数)
  - {} 占位
- f"{参数名}"

<br>

### 迭代序列时返回对象的索引和值
- enumerate(可迭代序列)

###多行字符串
- 使用三重引号（''' 或 """）来创建多行字符串