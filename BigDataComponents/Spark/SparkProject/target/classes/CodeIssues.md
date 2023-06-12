# 问题及解决

<br>

---

<br>

> 问题: 运行pyspark时出现以下错误
>>org.apache.spark.SparkException: Python worker failed to connect back. <br> 或者 <br>
>> py4j.protocol.Py4JJavaError: An error occurred while calling z:org.apache.spark.api.python.PythonRDD.collectAndServe
>
> <br>
>
> 原因：因为转载时间过长找不到spark所以报错
> 
> <br>
> 
>解决
>
>> 方法1、
>>> 安装findspark
>>>> pip install findspark <br>
>> 
>>> 添加下面代码到文件最上面
>>>> import findspark  <br>
>>>> findspark.init()
>
>> 方法2、
>>> 添加python解释器的路径 (文件最上面)
>>>> os.environ['PYSPARK_PYTHON'] = "D:\Python\Python37\python.exe"
>


<br>

---

<br>

> 问题
>> File "D:\Python\Python37\lib\site-packages\pyspark\util.py", line 81, in wrapper <br>
return f(*args, **kwargs)<br>
TypeError: 'NoneType' object is not callable
>
> <br>
>
> 原因 :
>>  rdd.foreach(print()) <br>
>>  将print()改为print
> 
> <br>
>
> 解决
>> 去掉print() 的 括号 
>> rdd.foreach(print) <br>








