import pymysql

def getConnection():
    # 连接数据库
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


def modify(sql, params):
    dbconn = getConnection()
    conn = dbconn[0]
    cursor = dbconn[1]

    try:
        # 执行 SQL 语句
        num = cursor.execute(sql, params)
        # 提交事务
        conn.commit()
        return num
    except Exception as e:
        print(e)
        conn.rollback()
        return 0
    finally:
        cursor.close()
        conn.close()


def query(sql, params=None):
    dbconn = getConnection()
    conn = dbconn[0]
    cursor = dbconn[1]

    try:
        # 执行 SQL 语句
        num = cursor.execute(sql, params)
        # 获取查询结果
        result = cursor.fetchall()
        for row in result:
            print(row)
    except Exception as e:
        print(e)
    finally:
        cursor.close()
        conn.close()

