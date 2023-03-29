import time

import requests
from bs4 import BeautifulSoup

# 定义要爬取的网页 URL
url = "http://47.94.39.99:8001/#/coursePlay?directoryId=573&courseId=10"

# 获取网页内容
response = requests.get(url)
html_content = response.text

# 使用 BeautifulSoup 解析 HTML
soup = BeautifulSoup(html_content, 'html.parser')

# 获取网页标题
# title = soup.title.string

# 获取所有的链接
links = []
# 使用 soup.find_all("li") 查找所有的 li 标签
for link in soup.find_all('li'):
    # link.get("class") 获取每个链接的 class 属性
    links.append(link.get('class'))
    time.sleep(10)

# 输出网页标题和链接
# print("网页标题：", title)
print("所有链接：", links)


