# 读取csv文件
import csv

my_list = []
file = csv.reader(open("../../Resource/address.csv", 'r'))
for line in file:       # 遍历出行
    # print(line)
    my_list.append(line[2])
    for cell in line:     # 遍历出每个字段
        print(cell, end=" ")
    print()
print(my_list)


