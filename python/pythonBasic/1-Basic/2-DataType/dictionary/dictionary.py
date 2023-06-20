# TODO 定义空字典
my_dict1 = {}
my_dict2 = dict()

# TODO 创建字典
my_dict3 = {'key1': 'value1', 'key2': 'value2'}

# 输出类型
print(f"字典1的内容是：{my_dict1}，类型是：{type(my_dict1)}")
print("字典2的内容是：{}，类型是：{}".format(my_dict2, type(my_dict2)))
print("字典2的内容是：%s，类型是：%s" % (my_dict3, type(my_dict3)))


# todo 根据key获取到value值
value = my_dict3['key2']
print("根据key获取到value值:", value)



print("---------------------------------嵌套------------------------------------")

# todo value为字典型（嵌套）
my_dict4 = {
    "zhangsan":{"语文":70,"数学":80,"英语":90},
    "lisi":{"语文":75,"数学":85,"英语":95},
    "wangwu":{"语文":77,"数学":99,"英语":98}
}
print(f"考试成绩：{my_dict4}")

# todo 从嵌套字典中获取某个数据
my_dict4["zhangsan"]["语文"]
print(f"取出zhangsan的全部成绩：{my_dict4['zhangsan']}")
print("取出zhangsan的语文成绩：%d" % my_dict4["zhangsan"]["语文"])
