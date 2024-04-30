import requests

res = requests.get("https://v1.kwaicdn.com/upic/2023/07/27/00/BMjAyMzA3MjcwMDQ0MTlfMTQ1ODcyNzk5NV8xMDg5NjM4NjgwMThfMV8z_b_B528b085d8496d614a6703b3c658561dd.mp4?pkey=AAXBASPINzX1mQS2l_QkjL-qezm3MHHS3ofRc55jFw3Odgq_Pci1Y1Bf2riNXaKbdL5slXDDS0plJ4tcZN_SrzZ1R_cvqB1yKXZtaZp2vwE737QWbavPqf4KOeo5e-bT6UQ&tag=1-1690789414-unknown-0-f2islbsjmw-be03856f4c284009&clientCacheKey=3x4z4kds5n3rzs4_b.mp4&di=ddd52a53&bp=14944&tt=b&ss=vp")
open("D:\pro_redskirt\private_daj\python\pythonProject\crawler/vi.mp4", "wb").write(res.content)
