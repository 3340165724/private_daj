# 进入数据库
use ds_pub;


# 2、根据dwd层表统计每个省份、每个地区、每个月下单的数量和下单的总金额，
#   存入MySQL数据库shtd_result的provinceeverymonth表中（表结构如下），
#   然后在Linux的MySQL命令行中根据订单总数、订单总金额、省份表主键均为降序排序，查询出前5条，
#   将SQL语句复制粘贴至客户端桌面【Release\任务B提交结果.docx】中对应的任务序号下，
#   将执行结果截图粘贴至客户端桌面【Release\任务B提交结果.docx】中对应的任务序号下;
#       字段	类型	中文含义	备注
#       provinceid	int	省份表主键
#       provincename	text	省份名称
#       regionid	int	地区表主键
#       regionname	text	地区名称
#       totalconsumption	double	订单总金额	当月订单总金额
#       totalorder	int	订单总数	当月订单总数
#       year	int	年	订单产生的年
#       month	int	月	订单产生的月
# 统计每个省份、每个地区、每个月下单的数量和下单的总金额
select bp.id, bp.name, br.id, br.region_name, year(oi.create_time), month(oi.create_time), count(*), sum(oi.final_total_amount)
from base_province as bp
inner join base_region as br on bp.region_id = br.id
inner join order_info as oi on bp.id = oi.province_id
group by bp.id, bp.name, br.id, br.region_name, year(oi.create_time), month(oi.create_time)



# 3、请根据dwd层表计算出2020年4月每个省份的平均订单金额和所有省份平均订单金额相比较结果（“高/低/相同”）,
#   存入MySQL数据库shtd_result的provinceavgcmp表（表结构如下）中，
#   然后在Linux的MySQL命令行中根据省份表主键、该省平均订单金额均为降序排序，查询出前5条，
#   将SQL语句复制粘贴至客户端桌面【Release\任务B提交结果.docx】中对应的任务序号下，
#   将执行结果截图粘贴至客户端桌面【Release\任务B提交结果.docx】中对应的任务序号下;
#           字段	类型	中文含义	备注
#        provinceid	int	省份表主键
#        provincename	text	省份名称
#        provinceavgconsumption	double	该省平均订单金额
#        allprovinceavgconsumption	double	所有省平均订单金额
#        comparison	text	比较结果	该省平均订单金额和所有省平均订单金额比较结果，值为：高/低/相同


# 4、根据dwd层表统计在两天内连续下单并且下单金额保持增长的用户，
#   存入MySQL数据库shtd_result的usercontinueorder表(表结构如下)中，
#   然后在Linux的MySQL命令行中根据订单总数、订单总金额、客户主键均为降序排序，查询出前5条，
#   将SQL语句复制粘贴至客户端桌面【Release\任务B提交结果.docx】中对应的任务序号下，
#   将执行结果截图粘贴至客户端桌面【Release\任务B提交结果.docx】中对应的任务序号下；
#       字段	类型	中文含义	备注
#        userid	int	客户主键
#        username	text	客户名称
#        day	text	日	记录下单日的时间，格式为yyyyMMdd_yyyyMMdd 例如： 20220101_20220102
#        totalconsumption	double	订单总金额	连续两天的订单总金额
#        totalorder	int	订单总数	连续两天的订单总数