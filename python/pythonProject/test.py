import random

num1 = int(random.uniform(0, 1000))
num2 = int(random.uniform(0, 1000))
print(num1)
print(num2)

if num1 < num2:
    print("最小值：", num1)
else:
    print(num2)


value = min(num1, num1)
print("最小值：", value)

