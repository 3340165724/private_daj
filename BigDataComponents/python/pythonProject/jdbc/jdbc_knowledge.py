# 引入pymysql模块。注意小写
import pymysql


# 创建数据库连接(注意：密码参数passwd不要写成password)
conn = pymysql.connect(
    host='127.0.0.1',
    port=3306,
    user='root',
    passwd='123456',
    db='db_student',
    charset='utf8'
)

# 生成游标对象
cursor = conn.cursor()

# 调用游标对象中execute() 函数执行sql语句，该函数返回受影响的行
query = "SELECT * FROM employee WHERE gender ='男'"
cursor.execute(query)

# 处理查询结果
for row in cursor:
    print(row)


# 提交数据库连接，若不提交将无法保存新建或者修改的数据
# 提交到数据库执行，不加commit，则无法提交到数据库
# 只有对数据库进行了增删改时需要提交数据库，查询不需要
conn.commit()

# 关闭游标，释放资源
cursor.close()
# 关闭连接，释放资源
conn.close()
