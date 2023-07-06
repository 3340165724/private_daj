import pandas

df = pandas.DataFrame(
    {'name': ['zhangsan', "lis", 'tom'],
     'age': [25, 29, 26],
     'sex': ['男', '女', '男']
     })
print(df)



df1 = pandas.DataFrame(
    data={'name': ['zhangsan', "lis", 'tom'],
     'age': [25, 29, 26],
     'sex': ['男', '女', '男']
     },
    columns=["anme","age","sex"]  # 确定列的顺序的
)
print(df)


