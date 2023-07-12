from pandas import Series

series = Series([4.5, 7, 5, "zhangsan", 'tom', 50])
series1 = Series([4.5, 7, 5, "zhangsan", 'tom', 50], index=["a", "b", "c", "d", "e", "f"])
print("自动索引：\n", series)
print("手动索引：\n", series1)

# 重新索引
# todo NaN:表示数据中的缺失值, fill_value=0表示填充值为0
print(series1.reindex(["a", "b", "c", "d", "e", "f", "g", "h"]))
print(series1.reindex(["a", "b", "c", "d", "e", "f", "g", "h"], fill_value=0))

print("删除数据：\n",series1.drop("a"))
