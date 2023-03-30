import csv

# 读取数据
file = csv.reader(open("../Resource/address.csv", 'r'))

# 创建空列表，用于存储处理后的单个数据
datas = ""
for line in file:
    # print(line)
    for item in line:
        datas += item + " "
print(datas)

# 将所有字母转换为小写以便进行不区分大小写的统计
datas = datas.lower()

# 数据切片
words = datas.split(" ")

# 使用Python字典来存储每个单词的出现次数
word_count = {}

for word in words:
    if word in word_count:
        word_count[word] += 1
    else:
        word_count[word] = 1
print("word_count:", word_count)


# 打印结果
# word_count字典的items()方法来遍历所有的键值对
for word, count in word_count.items():
    print(word, count)

