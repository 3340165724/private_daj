import keyword

print("---------------------------创建一个空列表--------------------------------")
# 创建一个空列表
my_list = []
print("创建一个空列表:", my_list)
print()


print("----------------------------创建一个带有初始值的列表-----------------------------------")
# 创建一个带有初始值的列表
my_list = [1, 2, 3, 4, 5]
print(my_list[1:3])     # 输出：[2, 3]
print(my_list[:3])      # 输出：[1, 2, 3]
print(my_list[3:])      # 输出：[4, 5]
print(my_list[::3])     # [1, 3, 5]
print()


print("------------------------------range()对象-------------------------------------")
num_list = list(range(1, 6))
print("list()函数，它可以将range()对象、字符串、元组或其他可迭代类型的数据转换为列表:", num_list)
num_list1 = list(range(1, 10, 2))
print("创建一个包含1到10中的奇数的列表", num_list1)
print()


print("----------------------------向列表中添加元素---------------------------------------")
# 末尾追加元素
my_list.append(6)
print("末尾追加元素:", my_list)      # 输出：[1, 2, 3, 4, 5, 6]
my_list.insert(2, "456657")
print("指定位置添加", my_list)
print()

print("---------------------------列表中删除元素----------------------------------------")
# 使用del语句或remove()方法删除列表中的元素
del my_list[2]
print("列表中删除元素:", my_list)      # 输出：[1, 2, 4, 5, 6]
my_list.remove(1)
print("列表中删除元素:", my_list)
print()


print("-----------------------------获取列表的长度---------------------------------")
# 获取列表的长度
print(len(my_list))
print()


print("----------------------------判断元素是否在列表中------------------------------------")
# 判断元素是否在列表中
print(3 in my_list) # 输出：False
print(5 in my_list) # 输出：True
print()


print("---------------------------------输出关键字--------------------------------------")
print("输出关键字:", keyword.kwlist)
print()


print("----------------------------------统计数值列表的元素和------------------------------------------------")
print("统计数值列表的元素和:", sum(my_list))
print()


print("---------------------------------------排序------------------------------------------------")
my_list.sort(reverse=True)
print(my_list)
print()