print("--------------------创建集合的两种方式-----------------------------")
# 创建字典
# 用花括号创建字典
my_set = {1, 2, 3, 4, 5}
print(my_set)  # {1, 2, 3, 4, 5}
print()


# 使用set()函数创建字典
my_set = set([1, 2, 3, 4, 5])
print(my_set)  # {1, 2, 3, 4, 5}


print("---------------------------添加元素---------------------------------")
# 添加元素
my_set.add(6)
print("添加元素", my_set)  # {1, 2, 3, 4, 5, 6}
print()


print("-------------------------删除元素------------------------------")
# 删除元素
my_set.remove(2)
print("删除元素", my_set)      # {1, 3, 4, 5, 6}
print()


# 并集
print("--------------------------求并集union-------------------------------")
set1 = {1, 2, 3}
set2 = {2, 3, 4}
union_set = set1.union(set2)
print("求并集union:", union_set)  # {1, 2, 3, 4}
print()


# 交集
print("-------------------------求交集intersection-----------------------------------")
intersection_set = set1.intersection(set2)
print("求交集intersection", intersection_set)  # {2, 3}
print()


# 差集（即只出现在其中一个集合中的元素）
print("-------------------------求差集difference------------------------------")
difference_set = set1.difference(set2)
print("求差集difference", difference_set)      # {1}
print()

# 清空集合中的所有元素
print("-----------------------清空集合中的所有元素-----------------------------")
my_set.clear()




