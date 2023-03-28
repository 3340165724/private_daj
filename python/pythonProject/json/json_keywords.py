import json

print("-------------------------JSON 编码(将 Python 对象编码为 JSON 字符串)------------------------------------------")
list = [4, 6, 13, 10, 12]
jsonStr = json.dumps(list)
print("转换后类型为%s，值为%s" % (type(jsonStr), jsonStr))
dict = {"name": "张三", "age": "20", "address": "USA"}
jsonStr2 = json.dumps(dict)
print("转换后类型为%s，值为 %s" % (type(jsonStr2), jsonStr2))
print()
print()


print("--------------------------JSON 解码(将 JSON 字符串解码为 Python 对象)-------------------------------------------")
jsonData= '{"name":"张三","age":"20","address":"USA"}'
obj = json.loads(jsonData)
for item in obj:
    print("%s=>%s" % (item, obj[item]), end="\n")
    print("item:", item)
    print(obj[item])


print("-----------------------------------------")

ages = []
list_value = []
jsonData1= '''
            [
            {"name":"张三","age":"20","address":"USA"},
            {"name":"李四","age":"201","address":"USA"}
            ]
            '''
obj1 = json.loads(jsonData1)
for items in obj1:
    # items["age"]:获取到key为age的所有value值
    ages.append(items["age"])
    for key in items:
        # 添加所有value值到列表
        list_value.append(items[key])
        # print("key", key)
        # print("key:value", items)
print("-----------------------------------------")
print("全部输出", list_value)
print("只取age的value值", ages)
