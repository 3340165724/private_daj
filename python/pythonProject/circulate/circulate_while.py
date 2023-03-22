print("-----------------------while循环来反转该数字，并将其存储在另一个变量中-----------------------------")
# while循环来反转该数字，并将其存储在另一个变量中
num = int(input("请输入一个数："))
temp = num
reverse_num = 0

while temp > 0:
    remainder = temp % 10
    reverse_num = reverse_num * 10 + remainder
    temp = temp // 10

if num == reverse_num:
    print(num, "是回文数")
else:
    print(num, "不是回文数")
print()


print("--------------------------------用切片操作符[::-1]反转该字符串---------------------------------------")
string = input("请输入一个字符串：")
if string == string[::-1]:
    print(string, "是回文字符串")
else:
    print(string, "不是回文字符串")
print()


print("--------------------------------使用双指针算法---------------------------------------")
# 通过同时从字符串的开头和结尾开始扫描，并在每次扫描中将两个指针向中心移动来判断字符串是否为回文字符串
string = input("请输入一个字符串：")
left = 0
right = len(string) - 1
is_palindrome = True

while left < right:
    if string[left] != string[right]:
        is_palindrome = False
        break
    left += 1
    right -= 1

if is_palindrome:
    print(string, "是回文字符串")
else:
    print(string, "不是回文字符串")
print()