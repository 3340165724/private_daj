# 练习

---

<br>

## 1、分别统计男、女生人数
- 使用map算子得到集合中的数据
  - .map(_._4) ：得到第四位元素
- 使用filter算子过滤出需要的数据
  - .filter(_.equalse("F")) ：元素等于F的数据
- 使用count算子统计元素个数
  - .count()


## 2、根据当前日期计算每个人的年龄，按"(Tom, $age)"格式输出
- 得到系统当前时间
  - new Date() ：
  - System.currentTimeMillis() ：以毫秒为单位
- 格式化
  - new SimpleDateFormat("yyyy-MM-dd")
-Date格式转化为String对象
  - sdf.format(Data格式的时间)
- String对象转化为ate格式
  - sdf.parse(String类型的时间)
- 以毫秒为单位
  - sdf.parse(String类型的时间).getTime

## 3、找出男、女生年龄最大和最小的的人，按"(Tom, $age)"格式输出


## 4、计算男、女生平均年龄$age_average


## 5、分别计算男、女生年龄总和，按 <br>“age_sum_M=$age_sum_M<br>age_sum_F=$age_sum_F”格式输出