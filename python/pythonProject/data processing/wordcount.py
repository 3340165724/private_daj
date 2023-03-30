import json
import os

# 读取数据
import re

file = open("../Resource/address.csv", encoding="UTF-8").read()

# 创建空列表，用于存储处理后的单个数据
datas = ""
for line in file:
    # print(line)
    for item in line:
        datas += item
print("datas:", datas)

# 使用正则表达式替换非字母数字字符为空格
datas = re.sub(r'[^\w\s]', ' ', datas)

# 将所有字母转换为小写以便进行不区分大小写的统计
datas = datas.lower()

# 数据切片
words = datas.split()
print("words", words)

# 使用Python字典来存储每个单词的出现次数
word_count = {}

for word in words:
    if word in word_count:
        word_count[word] += 1
    else:
        word_count[word] = 1
print("word_count:", word_count)


# 打印结果
max_count = 0
max_word = ""
# word_count字典的items()方法来遍历所有的键值对
for word, count in word_count.items():
    # 频率出现最多1
    if count > max_count:
        max_count = count
        max_word = word
print("频率出现最多%s,出现了%d" % (max_word, max_count))

# 频率出现最多2
for item_json in word_count:
    # print("%s=>%s" % (item_json, word_count[item_json]), end="\n")
    if max_count < word_count[item_json]:
        max_count = word_count[item_json]
        max_word = item_json
print("频率出现最多%s,出现了%d" % (max_word, max_count))
    # print("item:", item_json)
    # print(word_count[item_json])

