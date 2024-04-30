rows = int(input("输入行数（奇数）："))
if rows %2 != 0:
    for i in range(0, rows//2 + 1):     # 控制打印行数
        for j in range(rows-i,0,-1):    # 控制空格个数
            print(" ", end='')          # 打印空格，不换行
        for k in range(0, 2 * i + 1):   # 控制星号0个数
            print("*", end='')          #打印星号，不换行
        print("")                       # 换行
    for i in range(rows//2, 0, -1):             # 控制打印行数
        for j in range(rows-i + 1, 0, -1):      # 控制空格个数
            print(" ", end='')                  # 打印空格，不换行
        for k in range(2 * i -1, 0, -1):        # 控制星号个数
            print("*", end='')                  #打印星号，不换行
        print("")                              # 换行
