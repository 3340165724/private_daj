###  输出所有关键字
```
import keyword
print(keyword.kwlist)
```

### 创建列表
```
# 创建一个空列表
my_list = []

# 创建一个带有初始值的列表
my_list = [1, 2, 3, 4, 5]
```

> list()函数，它可以将range()对象、字符串、元组或其他可迭代类型的数据转换为列表
> 
> range()函数,三个参数：start、stop 和 step，默认情况下，start是 0 和step1
> > print( list(range(1,6)))

### 列表中添加元素
>1、末尾追加元素
> >列表对象 . append( 要添加的元素 )
> >
> >my_list.append(6)
> 
> 2、指定位置添加
> > 列表对象 . insert( 索引下标 , 要添加的元素)
>>
> >my_list.insert(2, "15646")
> 
> 3、末尾追加另外一个迭代对象的所有元素
> 
> > 列表对象 . extend( 另一个列表对象 )
> >
> >my_list.extend([3,5,6])


### 列表中删除元素
```
1、 del语句
2、 remove()方法删除列表中的元素

del my_list[2]
my_list.remove(1)
```

### 获取列表的长度
```
len( 列表对象 )
len(my_list)
```

### 判断元素是否在列表中
```
要判断的元素 in 列表对象
3 in my_list
```