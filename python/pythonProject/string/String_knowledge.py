print("-----------------------------列表----------------------------------")
# 列表
my_string = 'Hello, world!'
print("输出第一个字符：", my_string[0])    # 输出 'H'
print("从头开始全部输出：", my_string[0:])    # 输出 'Hello, world!'
print("从下标为7的字符开始输出：", my_string[7:])   # 输出 'world!'
print("实现字符串切片:" + my_string[0:5])   # 输出 "Hello"
print("输出最后一个字符:" + my_string[-1])
print()


print("----------------------------转换-----------------------------------")
# 转换
'''
Python中常见的字符串方法包括
    upper()：将字符串中的所有字母转换为大写
    lower()：将字符串中的所有字母转换为小写
    strip()：删除字符串开头和结尾的空白字符
    replace(old, new)：用新字符串替换旧字符串
    split()：将字符串拆分为一个字符串列表
    string.replace(old, new)：将字符串中的所有 old 替换为 new。
    string.count(substring)：返回子字符串在字符串中出现的次数。
    string.find(substring)：返回子字符串在字符串中第一次出现的位置，如果没有找到则返回 -1。
    string.startswith(prefix)：检查字符串是否以指定的前缀开头。
    string.endswith(suffix)：检查字符串是否以指定的后缀结尾。
    string.join(iterable)：将可迭代对象中的元素连接成一个字符串，每个元素之间用字符串分隔符连接。
'''
print("将字符串中的所有字母转换为大写:", my_string.upper())          # 输出 '   HELLO, WORLD!   '
print("将字符串中的所有字母转换为小写:", my_string.lower())
print("删除字符串开头和结尾的空白字符:", my_string.strip())          # 输出 'Hello, world!'
print("用新字符串替换旧字符串:", my_string.replace('world', 'Python'))  # 输出 '   Hello, Python!   '
print("将字符串拆分为一个字符串列表:", my_string.split(','))       # 输出 ['   Hello', ' world!   ']
print("返回子字符串在字符串中出现的次数:", my_string.count("o"))
print("所有单词都以大写开头，其余小写：", my_string.title())
print("返回子字符串在字符串中第一次出现的位置，如果没有找到则返回 -1:", my_string.find("o"))
print()

print("----------------------------连接字符串-----------------------------------")
# 连接字符串
string1 = "Hello"
string2 = "world"
print("用加号运算符（+）将两个字符串连接起来：:" + string1 + " " + string2)    # 输出 "Hello world"

my_list = ["Hello", "world"]
print("用字符串的join()方法来连接一个字符串列:" + "".join(my_list))    # 输出"Hello world"
print("用字符串的join()方法来连接一个字符串列:" + "_".join(my_list))    # 输出"Hello world"
print()


print("----------------------------格式化字符串-----------------------------------")
# 格式化字符串
name = "Alice"
age = 25
print("方式一：" + "My name is {} and I'm {} years old.".format(name, age)) # 输出 "My name is Alice and I'm 25 years old."
print("方式二：" + "My name is {name} and I'm {age} years old.")
print("方式三：" + "My name is %s and I'm %d years old." % (name, age))
# %s表示字符串占位符，%d表示整数占位符。可以在%操作符后面使用括号来传递变量值
print()


print("-----------------------------字符串的遍历-------------------------------------------")
# 字符串的遍历
for char in my_string:
    print(char)

for i, char in enumerate(my_string):
    print(i, char)


# 使用三重引号（''' 或 """）来创建多行字符串
my_string_ = '''Hello,
world!
klhjfgm
'''
print(my_string_)

