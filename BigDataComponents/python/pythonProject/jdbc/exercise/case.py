from DatabaseHandler import *

while True:
    print("产品表的操作类型 【1】查询【2】增加【3】修改【4】删除")
    type = input("请选择操作类型：")
    if type == '1':
        query("select * from employee")
    elif type == '2':
        prolnfo = input("请输入产品信息（格式为“产品名称，价格”）：")
        proltem = prolnfo.split(",")
        modify("insert into employee")
        print("产品保存成功了！")
    elif type == '3':
        prolnfo = input("请输入产品信息（格式为“产品名称，价格”）：")
        proltem = prolnfo.split(",")
        modify("insert into employee")
        print("产品保存成功了！")
    elif type == '4':
        prolnfo = input("请输入产品编号（格式为“产品编号”）：")
        modify("insert into employee")
        print("产品删除成功了！")
        exitlnfo = input("是否继续当前操作，输入y表示继续，输入n表示退出")
        if exitlnfo == 'n':  # 如果选择n表示退出循环
            break
