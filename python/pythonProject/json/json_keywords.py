import json

jsonData= '{"name":"张三","age":"20","address":"USA"}'
obj = json.loads(jsonData)
for item in obj:
    print("%s=>%s" % (item, obj[item]), end="\n")
    print("item:", item)
    print(obj[item])


print("-----------------------------------------")

ages = []
jsonData1= '''
            [
            {"name":"张三","age":"20","address":"USA"},
            {"name":"李四","age":"201","address":"USA"}
            ]
            '''
obj1 = json.loads(jsonData1)
list_value = []
print("---------------------------------------")
for items in obj1:
    # items["age"]:获取到key为age的所有value值
    ages.append(items["age"])
    for key in items:
        # 添加所有value值到列表
        list_value.append(items[key])
        print("key", key)
        print("key:value", items)
print("-----------------------------------------")
print(list_value)
print(ages)
