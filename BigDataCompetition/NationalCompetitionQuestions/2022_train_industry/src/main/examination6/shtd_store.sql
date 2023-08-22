# 1、编写Scala工程代码，根据dwd层表统计每个月、每个设备、每种状态的时长，
#    存入MySQL数据库shtd_store的表（表结构如下）中，
#    然后在Linux的MySQL命令行中根据设备id、状态持续时长均为逆序排序，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中;
#       字段	        类型	中文含义	备注
#       MachineID	int	设备id
#       ChangeRecordState	string	状态
#       duration_time	string	持续时长	当月该状态的时长和
#       Year	int	年	状态产生的年
#       Month	int	月	状态产生的月
select *
from






# 2、编写Hive SQL代码，根据dwd层MachineData表统计设备运行时各种状态（报警、离线、待机、运行）的连续日期。
# 查询2021年10月各个设备连续同状态period_state的起止日期（ChangeStartTime和ChangeEndTime）。
# 即如果机器运行了，就是运行状态的起止日期，如果机器待机了，就是待机状态的起止日期。最后结果各个设备内部按照起始日期排序。然后在Linux的MySQL命令行中根据machineID倒序排序，查询出前5条，将SQL语句与执行结果截图粘贴至对应报告中。
# 下框中有点bug：
# SELECT mn machineID, ms period_state, from_unixtime(mis) StartTime, c.endtime EndTime
# FROM
#     (FROM
#         (FROM
#             (SELECT machine_no mn, machine_state ms, UNIX_TIMESTAMP(starttime) st,
#                 UNIX_TIMESTAMP(endtime) et, unix_timestamp(endtime) - unix_timestamp(starttime) + 1 dif
#             FROM changerecord) t1
#         SELECT mn, ms, st, et, dif,
#             SUM(dif) over(PARTITION BY mn, ms ORDER BY st ROWS BETWEEN unbounded preceding AND 1 PRECEDING) dif2) t2
#     SELECT mn, ms, MIN(st) mis, MAX(st) mas GROUP BY (st - dif2), mn, ms ORDER BY mn, ms) t3
# INNER JOIN changerecord c ON mn = c.machine_no AND ms = c.machine_state AND mas = c.starttime AND mis < mas
# and mn='Equipment#9' and ms='waiting'
# ORDER BY machineID, StartTime LIMIT 100;
#
# 直接排序取前两个组合为一条记录，不使用排名函数：
# select t3.riqi,collect_list(t3.machine_no)[0],collect_list(t3.machine_no)[1],collect_list(t3.sc)[0],collect_list(t3.sc)[1],t3.machine_factoryId from (
# select distinct machinedata.machine_factoryId,machinedata.machine_no,SUBSTR(startTime,1,10) as riqi,
# sum(UNIX_TIMESTAMP(endTime)-UNIX_TIMESTAMP(startTime)) over(partition by SUBSTR(startTime,1,10),machinedata.machine_factoryId,machinedata.machine_no ) as sc from machinedata
# inner join changerecord on machinedata.machine_no = changerecord.machine_no
# where changerecord.machine_state='running'
# order by sc desc) t3 group by t3.riqi,t3.machine_factoryId
#
# 字段	类型	中文含义	备注
# machineID	int
# period_state	string		如：“待机” “运行”
# StartTime	timestamp
# EndTime	timestamp