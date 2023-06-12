# idea环境配置

---
<br>

## 设置缩放字体快捷键
![increase](../../../../Image/increase.png "increase ")

---

<br>


## 配置 Scala 环境
> 1、下载安装Scala
>> Scala官网下载
>>>![ScalaDownload](../../../../Image/ScalaDownload.png "ScalaDownload ")
>
> ---
>
> 2、配置环境变量
> 
> ---
> 
> 3、idea中安装Scala插件
>>![Plugins](../../../../Image/Plugins.png "Plugins ")
> 
> ---
> 
> 4、引入scala 框架
>>![ImportFramework](../../../../Image/ImportFramework.png "ImportFramework ")
>
>  ---
>
> 5、创建Scala源目录
>> ![SourceDirectory](../../../../Image/SourceDirectory.png "SourceDirectory ")
> 
> ---
> 
> 6、添加ScalaSDK，否则会报找不到或无法加载主类
>>![ScalaSDK](../../../../Image/ScalaSDK.png "ScalaSDK ")
> 
> ---
>
> 7、创建Scala文件
> 


<br>

---

<br>

## Python环境
>1、下载python插件（和Scala步骤一样）
> 
> ---
> 
> 2、引入python框架（和Scala步骤一样）
> 
> ---
> 
> 3、下载 pyspark 和 findspark
>> pip install pyspark==2.3.2 (指定pyspark的版本)
> 
>>pip install findspark 
>>> 问题
>>>>不初始化会报错（py4j.protocol.Py4JError: org.apache.spark.api.python.PythonUtils.isEncryptionEnabled does not exist in the JVM） 
>>>
>>> 解决
>>>> findspark.init()
>  <br>
> 作用就是初始化找到本机安装的spark的环境



