import pymysql

# 连接数据库
def getConnection():
    conn = pymysql.connect(
        host='localhost',  # 数据库地址
        port=3306,  # 数据库端口
        user='root',  # 数据库用户名
        password='123456',  # 数据库密码
        database='db_student'  # 数据库名
    )
    # 创建游标
    cursor = conn.cursor()
    return (conn, cursor)

# 增、删、改
def modify(sql, params):
    dbConn = getConnection()
    conn = dbConn[0]
    cursor = dbConn[1]

    try:
        num = cursor.execute(sql, params)
        conn.commit()
        return num
    except Exception as e:
        print(e)
        # 数据回滚操作
        conn.rollback()
        return 0
    finally:
        cursor.close()
        conn.close()

# 查询
def query(sql, params=None):
    dbConn = getConnection()
    conn = dbConn[0]
    cursor = dbConn[1]

    try:
        # 执行 SQL 语句
        cursor.execute(sql, params)
        # 获取查询结果
        result = cursor.fetchall()
        for row in result:
            print(row)
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        conn.close()
