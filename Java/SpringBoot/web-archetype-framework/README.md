json 说明

# 学生外出数据大屏 out

1 班级学生外出情况 leftTop.vue

[
['计应2001', '罗梦杰', '40', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
['计应2001', '罗梦杰', '49', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
['计应2001', '罗梦杰', '45', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
['计应2001', '罗梦杰', '65', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
['计应2001', '罗梦杰', '38', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
['计应2001', '罗梦杰', '94', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
['计应2001', '罗梦杰', '36', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
['计应2001', '罗梦杰', '84', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
['计应2001', '罗梦杰', '834', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
['计应2001', '罗梦杰', '94', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
['计应2001', '罗梦杰', '24', "<span  class='colorBlue'>40</span>", "<span  cass='colorRed'>439</span>"],
]



2 出校晚归信息 rightBottom.vue

[
['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
]

3 学院发布 rightTop.vue

[
['4月29日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
['4月28日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
['4月27日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
['4月26日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
['4月25日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
['4月24日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
['4月23日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
['4月22日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
['4月21日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
['4月20日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
]

4 统计 center.vue

[
{
title: '学生人数',
number: {
number: [1476],
toFixed: 0,
textAlign: 'left',
content: '{nt}',
style: {
fontSize: 26
}
}
},
{
title: '累计请假',
number: {
number: [759],
toFixed: 0,
textAlign: 'left',
content: '{nt}',
style: {
fontSize: 26
}
}
},
{
title: '累计销假',
number: {
number: [758],
toFixed: 0,
textAlign: 'left',
content: '{nt}',
style: {
fontSize: 26
}
}
},
{
title: '当日请假',
number: {
number: [759],
toFixed: 0,
textAlign: 'left',
content: '{nt}',
style: {
fontSize: 26
}
}
},
{
title: '当日销假',
number: {
number: [758],
toFixed: 0,
textAlign: 'left',
content: '{nt}',
style: {
fontSize: 26
}
}
},
]

5 学生出校分布情况 centerLeft2Chart index.vue

[
{
name: '吕梁市',
value: 298,
},
{
name: '临汾市',
value: 937,
},
{
name: '忻州市',
value: 374,
},
{
name: '运城市',
value: 572,
},
{
name: '晋中市',
value: 831,
},
{
name: '朔州市',
value: 53,
},
{
name: '晋城市',
value: 4,
},
{
name: '长治市',
value: 193,
},
{
name: '阳泉市',
value: 1200,
},
{
name: '太原市',
value: 942,
},
{
name: '大同市',
value: 221,
},
]


7 离校返校对比 BottomLeftChart.vue

[
{
name: '出校人数',
type: 'line',
stack: 'Total',
data: [120, 132, 101, 134, 90, 230, 210],
lineStyle: {
color: "red"
},
},
{
name: '返校人数',
type: 'line',
stack: 'Total',
data: [220, 182, 191, 234, 290, 330, 310],
lineStyle: {
color: "blue"
},
},
]


# 学生返校数据大屏 back
1 学院信息 index.vue
[
    {
        "name": "软件学院",
        "value": 1264
    },
    {
        "name": "信息与计算学院",
        "value": 1402
    },
    {
        "name": "材料科学与工程学院",
        "value": 1263
    },
    {
        "name": "建筑学院",
        "value": 1502
    },
    {
        "name": "土木工程学院",
        "value": 1640
    }
]

3 center

[
{
title: '学生总数',
number: {
number: [5000],
toFixed: 0,
textAlign: 'left',
content: '{nt}',
style: {
fontSize: 26
}
}
},
{
title: '已返校人数',
number: {
number: [4980],
toFixed: 0,
textAlign: 'left',
content: '{nt}',
style: {
fontSize: 26
}
}
},
{
title: '今日返校人数',
number: {
number: [1274],
toFixed: 0,
textAlign: 'left',
content: '{nt}',
style: {
fontSize: 26
}
}
},
]

4 疫情新闻 news index.vue

[
'国务院联防联控机制印发应对近期新冠病毒感染疫情疫苗接种工作方案',
'应对近期新冠病毒感染疫情疫苗接种工作方案',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
'关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
]

5 china index.vue

[
{ name: "南海诸岛", value: 100},
{ name: "北京", value: 540 },
{ name: "天津", value: 130 },
{ name: "上海", value: 400 },
{ name: "重庆", value: 750 },
{ name: "河北", value: 130 },
{ name: "河南", value: 830 },
{ name: "云南", value: 110 },
{ name: "辽宁", value: 19 },
{ name: "黑龙江", value: 150 },
{ name: "湖南", value: 690 },
{ name: "安徽", value: 60 },
{ name: "山东", value: 39 },
{ name: "新疆", value: 452 },
{ name: "江苏", value: 31 },
{ name: "浙江", value: 104 },
{ name: "江西", value: 36 },
{ name: "湖北", value: 52 },
{ name: "广西", value: 33 },
{ name: "甘肃", value: 73 },
{ name: "山西", value: 54 },
{ name: "内蒙古", value: 778 },
{ name: "陕西", value: 22 },
{ name: "吉林", value: 44 },
{ name: "福建", value: 18 },
{ name: "贵州", value: 54 },
{ name: "广东", value: 98 },
{ name: "青海", value: 13 },
{ name: "西藏", value: 0 },
{ name: "四川", value: 44 },
{ name: "宁夏", value: 42 },
{ name: "海南", value: 22 },
{ name: "台湾", value: 23 },
{ name: "香港", value: 25 },
{ name: "澳门", value: 555 }
]