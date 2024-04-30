print("------------------------------创建字典----------------------------------")
my_dict1 = {}
my_dict = {'key1': 'value1', 'key2': 'value2'}
print()

print("----------------------------根据key获取到value值--------------------------")
value = my_dict['key2']
print("根据key获取到value值:", value)
print()

print("------------------------------修改字典元素---------------------------------")
my_dict['key1'] = 'new_value1'
print("通过key修改：", my_dict)
print()

print("-------------------------------添加新元素---------------------------------")
my_dict["key3"] = "valus3"
print("添加后的字典：", my_dict)
print()


print("-------------------------------删除元素----------------------------------")
del my_dict['key1']
print("删除元素:", my_dict)
print()


print("-------------------------------检查键是否存在----------------------------------")
if 'key1' in my_dict:
    print('Key exists')
print()


print("-------------------------------迭代字典----------------------------------")
for key in my_dict:
    print(key, my_dict[key])
print()


print("-------------------------------获取所有键----------------------------------")
keys = my_dict.keys()
print("获取所有键:", keys)
print()


print("-------------------------------获取所有值----------------------------------")
values = my_dict.values()
print("获取所有值:", values)
print()


print("-------------------------------获取所有键值对----------------------------------")
items = my_dict.items()
print("获取所有键值对:", items)
print()


print("-------------------------------获取字典中最小最大的键值对----------------------------------")
min_pair = min(my_dict.items(), key=lambda x: x[1])
print("获取字典中最小的键值对:", min_pair)
max_pair = max(my_dict.items(), key=lambda x: x[1])
print("获取字典中最大的键值对:", max_pair)
print()


print("--------------------------------迭代得到单个key和单个value值---------------------------------")
# 在迭代字典时，可以使用items()方法，该方法返回一个包含所有键值对的元组列表。可以使用tuple解包语法来遍历每个键值对
for key, value in my_dict.items():
    print(key, value)
print()


print("---------------------------------字典排序----------------------------------------------------")
# 使用sorted()函数和lambda表达式来排序字典中的键值对
# reverse=True：表示降序排序
# reverse=False：表示升序排序
sorted_pairs = sorted(my_dict.items(), key=lambda x: x[1], reverse=True)
print("按值降序排序", sorted_pairs)
sorted_key = sorted(my_dict.items())
print("按值降序排序", sorted_key)
print()


print("---------------------------------字典中的值作为键，键作为值---------------------------------------")





