import pymysql

# 连接数据库
def getConnection():
    connection = pymysql.connect(
        host='localhost',
        port=3306,
        user='root',                               # 5
        password='123456',
        database='db_student'
    )

    # 创建游标
    cursor = connection.cursor()                  # 5
    return connection, cursor                     # 5


def modify(sql, params=None):
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


def query(sql, params=None):
    dbConn = getConnection()
    conn = dbConn[0]
    cursor = dbConn[1]
    try:
        cursor.execute(sql, params)
        # 获取查询结果
        result = cursor.fetchall()
        return result
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        conn.close()


print(getConnection())
