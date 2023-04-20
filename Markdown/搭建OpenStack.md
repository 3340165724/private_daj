# 搭建OpenStack （M版）



### 一 、基本环境（新建两台虚拟机）

#### 1、虚拟机规划

----

|   节点   |   主机名   | 内存 |       IP        |    作用    |         cpu          | 磁盘空间 |
| :------: | :--------: | :--: | :-------------: | :--------: | :------------------: | :------: |
| 控制节点 | controller |  4G  | 192.168.125.128 |    管理    | 打开虚拟化（处理器） |   50G    |
| 计算节点 |  compute   |  2G  | 192.168.125.129 | 运行虚拟机 |      打开虚拟化      |   50G    |

#### 2、设置IP

```
vim /etc/sysconfig/network-scripts/ifcfg-ens33

# 修改配置文件
BOOTPROTO=dhcp # 将dhcp（动态的）改为static（静态的）
ONBOOT=no	# 将no改为yes
# 在配置文件最下面添加
IPADDR=92.168.125.128 # IP
NETMASK=255.255.255.0  # 子网掩码
GATEWAY=192.168.125.2  # 网关
DNS1=114.114.114.114   # DNS
# 保存并退出
:wq
```

#### 3、重启

```
systemctl restart network
```

#### 3、修改主机名

```
#不重启会生效
hostnamectl set-hostname controller
hostnamectl set-hostname compute
# 按ctrl+d 退出  重新登陆


#必须重启
1、修改主机名
vim /etc/hostname
	controller
	
2、重启
 reboot
```

#### 4、域名解析（映射）

```
vim /etc/hosts
	192.168.125.128 controller
	192.168.125.129 compute
```

#### 5、关闭防火墙

```
# 查看防火墙
systemctl status firewalld

# 暂时关闭防火墙
systemctl stop firewalld

# 永久关闭防火墙
systemctl disable firewalld
```

#### 6、验证是否可以ping通

```
# 测试能否连接到 Internet
ping -c 4 openstack.org

# 从 controller 节点，测试到*compute* 节点管理网络是否连通
ping -c 4 compute

# 从 compute 节点，测试到*controller* 节点管理网络是否连通
ping -c 4 controller
```





### 二、上传并解压openstack

#### 1、上传

#### 2、解压到指定目录

```
tar -zxvf openstack_rpm.tar.gz -C /opt/soft/openstack
```



### 三、挂载

#### 1、挂载本地镜像

```
mount /dev/cdrom /mnt/


# 可能存在的问题
mount: 在 /dev/sr0 上找不到媒体

# 解决
到VM上连接光盘（右下角，鼠标放上边出现(CD/DVD)，右击连接）
```

#### 2、设置开机自启

```
echo mount /dev/cdrom /mnt/ >> /etc/rc.local;chmod +x /etc/rc.local 
```



### 四、配置yum源

#### 1、配置本地yum 源  < 所有节点 >

```
vim /etc/yum.repos.d/local.repo
```

```
[local]
name=local
gpgcheck=0
baseurl=file:///mnt


[openstack]
name=openstack
gpgcheck=0
baseurl=file:///opt/soft/openstack/repo
```

#### 2、检测

```
yum clean all;yum repolist 

# 出现
Loaded plugins: fastestmirror
Loading mirror speeds from cached hostfile
repo id               repo name                           status
local                    local                             4,070
openstack               openstack                           598
```



#### 3、关闭selinux

```
# 暂时关闭
setenforce 0

# 永久关闭 （修改配置文件）
vim /etc/selinux/config
SELINUX=enforcing    //将enforcing改为disabled
```



### 五、设置时间服务

#### 1、所有节点【安装软件包】

```
yum install chrony
```

#### 2、编辑``/etc/chrony.conf`` 文件并注释除``server`` 值的所有内容

```
vim /etc/chrony.conf

# 1、控制节点
server ntp6.aliyun.com iburst     //添加
# 将下面的server注释 （第3行）
# server 0.centos.pool.ntp.org iburst
# server 1.centos.pool.ntp.org iburst
# server 2.centos.pool.ntp.org iburst
# server 3.centos.pool.ntp.org iburst
#将第22行去除注释
# Allow NTP client access from local network.
#allow 192.168.0.0/16    //去除注释，将内容改为 allow all


#2、计算节点
server 192.168.125.128 iburst     //添加（和控制节点同步）
# 将下面的server注释 （第3行）
# server 0.centos.pool.ntp.org iburst
# server 1.centos.pool.ntp.org iburst
# server 2.centos.pool.ntp.org iburst
# server 3.centos.pool.ntp.org iburst
```

#### 3、启动 NTP 服务并将其配置为随系统启动

```
systemctl enable chronyd.service
systemctl start chronyd.service
```

#### 4、验证时间是否同步

```
date
```



### 六、安装openstack

#### 1、启用OpenStack（看版本7.4以上不用）

```
yum install centos-release-openstack-mitaka

yum install https://repos.fedorapeople.org/repos/openstack/openstack-mitaka/rdo-release-mitaka-6.noarch.rpm
```

#### 2、安装 OpenStack 客户端

```
yum install python-openstackclient
```

#### 3、RHEL和 CentOS 默认启用 [*SELinux*](https://docs.openstack.org/liberty/zh_CN/install-guide-rdo/common/glossary.html#term-selinux) 。安装 `openstack-selinux` 包实现对OpenStack服务的安全策略进行自动管理

```
yum -y install openstack-selinux 
```



### 七、SQL数据库（ 数据库运行在控制节点上）

#### 1、安装软件包

```
yum -y install mariadb mariadb-server python2-PyMySQL
```

#### 2、创建并编辑 `/etc/my.cnf.d/openstack.cnf`

```
vim /etc/my.cnf.d/openstack.cnf

# 在 [mysqld] 部分，设置[ bind-address ]值为 本机监听的 IP 地址；以使得其它节点可以通过IP地址访问数据库
[mysqld]
...
bind-address = 192.168.125.128

#在[mysqld] 部分，设置如下键值, 来启用一些必要的选项和 UTF-8 字符集
default-storage-engine = innodb         #  默认存储引擎
innodb_file_per_table                   #  独立表空间文件
max_connections = 4096                  #  最大连接数
collation-server = utf8_general_ci 
character-set-server = utf8             #  默认字符集 utf-8



[mysqld]
bind-address = 192.168.125.128
default-storage-engine = innodb
innodb_file_per_table 
max_connections = 4096
collation-server = utf8_general_ci 
character-set-server = utf8     
```

#### 3、重启服务

```
systemctl enable mariadb.service  //开机自启
systemctl start mariadb.service  //启动数据库服务
```

#### 4、执行 `mysql_secure_installation` 脚本来对数据库进行安全加固

```
mysql_secure_installation

# 输入root用户的当前密码
Enter current password for root (enter for none):   回车
…… 
# 设置 root 密码？[y/n]
Set root password? [Y/n] n       // 这里没有设置密码，因为的访问较快，如果在企业就必须设置
……
# 删除匿名用户？[Y/n]
Remove anonymous users? [Y/n] y
…… 
# 不允许 root 用户远程登录？[Y/n]
Disallow root login remotely? [Y/n] y
…… 
# 是否删除测试数据库并访问它？[Y/n]
Remove test database and access to it? [Y/n] y
……
# 现在重新加载特权表吗？[Y/n]
Reload privilege tables now? [Y/n] y

# 成功标准
Thanks for using MariaDB!
```



### 八、消息队列（消息队列服务一般运行在控制节点上）

#### 1、安装包

```
yum install rabbitmq-server
```

#### 2、启动消息队列服务并将其配置为随系统启动（ 重启 并 开机自启）

```
systemctl enable rabbitmq-server.service;systemctl start rabbitmq-server.service
```

### 3、添加 `openstack` 用户

```
# rabbitmqctl add_user openstack RABBIT_PASS
Creating user "openstack" ...
...done.

# 用合适的密码替换 RABBIT_DBPASS,不建议改
```

#### 4、给``openstack``用户配置写和读权限

```
# rabbitmqctl set_permissions openstack ".*" ".*" ".*"
Setting permissions for user "openstack" in vhost "/" ...
...done.
```

#### 5、# 查看端口

```
netstat -ntulp | grep 5672

// 集群之间同步数据 用的端口
tcp        0      0 0.0.0.0:25672           0.0.0.0:*               LISTEN      29675/beam.smp   
// 客服端使用
tcp6       0      0 :::5672                 :::*                    LISTEN      29675/beam.smp        
```

#### 5、启用 rabbitmq 的管理插件 < 可省略 >

```

rabbitmq-plugins enable rabbitmq_management    // 执行后会产生 15672 端口< 插件的 >
The following plugins have been enabled:
  mochiweb
  webmachine
  rabbitmq_web_dispatch
  amqp_client
  rabbitmq_management_agent
  rabbitmq_management

Applying plugin configuration to rabbit@controller... started 6 plugins.

# 检查端口
netstat -ntulp | grep 5672                   
tcp        0      0 0.0.0.0:25672           0.0.0.0:*               LISTEN      29675/beam.smp      
tcp        0      0 0.0.0.0:15672           0.0.0.0:*               LISTEN      29675/beam.smp      
tcp6       0      0 :::5672                 :::*                    LISTEN      29675/beam.smp

# 访问
IP：15672
# 默认密码
用户：  guest
密码：  guest
```



### 九、Memcached（运行在控制节点）

- 认证服务认证缓存使用Memcached缓存token。缓存服务memecached运行在控制节点。

- token： 用于验证用户登录信息， 利用memcached将token缓存下来，那么下次用户登录时，就不需要验证了[提高效率]

#### 1、安装软件包

```
yum install -y memcached python-memcached
```

#### 2、修改配置文件

```
vim /etc/sysconfig/memcached

OPTIONS="-l 172.0.0.1,::1" 
# 将改为
OPTIONS="-l 192.168.125.128,::1"


或者 
sed -i 's/127.0.0.1/10.0.0.11/g' /etc/sysconfig/memcached
```

#### 3、重启 并 开机自启

```
systemctl enable memcached.service;systemctl restart memcached.service
```

#### 4、查看

```
netstat -ntulp | grep memcached
```



### 十、认证服务（在控制节点上）

- 认证管理，授权管理和服务目录
- 服务目录 ：用户创建镜像[9292]，虚拟机[nova:8774]，网络[9696]等服时，都要访问该服务的服务端口，而openstack的服务较多，用户记起来很麻烦，即keystone提供的服务目录解决了这一问题

#### 1、前提条件

- 在你配置 OpenStack 身份认证服务前，你必须创建一个数据库和管理员令牌(token)

#### 2、用数据库连接客户端以 `root` 用户连接到数据库服务器

```
mysql -u root -p

mysql   # 这里直接使用 mysql 命令即可 [应为我们再初始化的时候没有设置密码]
```

#### 3、创建 `keystone` 数据库

```
CREATE DATABASE keystone;
```

#### 4、对''keystone''数据库授予恰当的权限

```
GRANT ALL PRIVILEGES ON keystone.* TO 'keystone'@'localhost' \
  IDENTIFIED BY 'KEYSTONE_DBPASS';
GRANT ALL PRIVILEGES ON keystone.* TO 'keystone'@'%' \
  IDENTIFIED BY 'KEYSTONE_DBPASS';
  
# 用合适的密码替换 KEYSTONE_DBPASS
```

#### 5、退出数据库客户端

```
quit / exit
```

#### 6、安装相关软件包

```
yum -y install openstack-keystone httpd mod_wsgi
```

#### 7、修改配置文件

- 编辑文件 `/etc/keystone/keystone.conf` 并完成如下操作（行数很多需要过滤）

  ##### （1）过滤配置文件

  ```
  # 先拷贝一份
  cp /etc/keystone/keystone.conf{,.bak}
  
  # 过滤
  egrep -v '^$|#' /etc/keystone/keystone.conf.bak > /etc/keystone/keystone.conf 
  #意思是：将keystone.conf.bak文件里面的内容过滤出来，替换keystone.conf 里面的内容
  
  # 补充
  vim /etc/keystone/keystone.conf
  	:set nu  # 显示行数
  ```

  ##### （2）在`[DEFAULT]`部分，定义初始管理令牌( token )的值

  ```
  [DEFAULT]  # 在这个下面加入
  admin_token = ADMIN_TOKEN   # 添加
  ```

  ##### （3）在 `[database]` 部分，配置数据库访问

  ```
  [database]
  connection = mysql+pymysql://keystone:KEYSTONE_DBPASS@controller/keystone
  ```

  ##### （4）在`[token]`部分，配置Fernet UUID令牌的提供者

  ```
  [token]
  provider = fernet
  ```

  ##### （5）检测

  ```
  md5sum /etc/keystone/keystone.conf
  # 出现d5acb3db852fe3f247f4f872b051b7a9  /etc/keystone/keystone.conf 配置成功
  ```

#### 8、同步数据库

- 初始化身份认证服务的数据库

  ##### （1）同步数据库前

  ```
  mysql keystone -e "show tables;"         #  不会有表
  ```

  ##### （2）同步数据库

  ```
  su -s /bin/sh -c "keystone-manage db_sync" keystone
  
  # 补充
  su:  切换用户
  -s:  指定 shell     +    **shell
  -c:  指定执行的命令   +   命令
  keystone： 用户
  # 意思： 切换到  keystone 用户执行 /bin/shell < keystone-manage db_sync > 命令
  ```

  ##### （3）同步数据库后

  ```
  mysql keystone -e "show tables;"         # 会出现表
  ```

#### 9、初始化Fernet keys

```
keystone-manage fernet_setup --keystone-user keystone --keystone-group keystone

# 初始化之后会在 /etc/keystone 目录下会多一个  Fernet keys 目录
```



### 十一、配置 Apache HTTP 服务器（在控制节点）

#### 1、编辑`/etc/httpd/conf/httpd.conf` 文件，配置`ServerName` 选项为控制节点： [大约在95行]

```
echo 'ServerName controller' >> /etc/httpd/conf/httpd.conf   # 提高启动 http 速度
```

#### 2、创建文件并编辑 `/etc/httpd/conf.d/wsgi-keystone.conf`

```
vim /etc/httpd/conf.d/wsgi-keystone.conf

Listen 5000
Listen 35357

<VirtualHost *:5000>
 WSGIDaemonProcess keystone-public processes=5 threads=1 user=keystone group=keystone display-name=%{GROUP}
 WSGIProcessGroup keystone-public
 WSGIScriptAlias / /usr/bin/keystone-wsgi-public
 WSGIApplicationGroup %{GLOBAL}
 WSGIPassAuthorization On
 ErrorLogFormat "%{cu}t %M"
 ErrorLog /var/log/httpd/keystone-error.log
 CustomLog /var/log/httpd/keystone-access.log combined

 <Directory /usr/bin>
     Require all granted
 </Directory>
</VirtualHost>

<VirtualHost *:35357>
 WSGIDaemonProcess keystone-admin processes=5 threads=1 user=keystone group=keystone display-name=%{GROUP}
 WSGIProcessGroup keystone-admin
 WSGIScriptAlias / /usr/bin/keystone-wsgi-admin
 WSGIApplicationGroup %{GLOBAL}
 WSGIPassAuthorization On
 ErrorLogFormat "%{cu}t %M"
 ErrorLog /var/log/httpd/keystone-error.log
 CustomLog /var/log/httpd/keystone-access.log combined

 <Directory /usr/bin>
     Require all granted
 </Directory>
</VirtualHost>
```

#### 3、启动 Apache HTTP 服务并 开机自启

```
systemctl enable httpd.service;systemctl start httpd.service
```

#### 4、检测

```

md5sum /etc/httpd/conf.d/wsgi-keystone.conf
```



### 十二、创建服务实体和API端点（在控制节点上）

#### 1、配置环境变量

##### （1）配置认证令牌

```
export OS_TOKEN=ADMIN_TOKEN
```

##### （2）配置端点URL

```
export OS_URL=http://controller:35357/v3
```

##### （3）配置认证 API 版本

```
export OS_IDENTITY_API_VERSION=3
```

#### 2、查看环境变量

```
# env | grep OS
……
OS_IDENTITY_API_VERSION=3
OS_TOKEN=ADMIN_TOKEN
OS_URL=http://controller:35357/v3
```

#### 3、创建服务实体和身份认证服务

##### （1）创建一个 keystone 服务，描述为 "OpenStack Identity" 

```
openstack service create --name keystone --description "OpenStack Identity" identity
```

##### （2）创建认证服务的 API 端点

```
openstack endpoint create --region RegionOne identity public http://controller:5000/v3

openstack endpoint create --region RegionOne identity internal http://controller:5000/v3

openstack endpoint create --region RegionOne identity admin http://controller:35357/v3
```

#### 4、检测

##### （1）查看服务

```
openstack service list   
```

##### （2）查看API

```
openstack endpoint list
```

#### 5、创建域、项目、用户和角色

##### （1）创建域default

```
openstack domain create --description "Default Domain" default
```

##### （2）创建 admin 项目

```
openstack project create --domain default --description "Admin Project" admin
```

##### （3）创建 admin 用户：  //将-prompt 替换为 123456（登录的密码）

```
openstack user create --domain default --password-prompt admin  
```

##### （4）创建 admin 角色

```
openstack role create admin
```

##### （5）在 admin 的项目上; 给 admin 的用户添加 admin 角色

```
openstack role add --project admin --user admin admin
```

##### （6）创建``service``项目

```
openstack project create --domain default --description "Service Project" service
```

#### 6、认证测试

##### （1）创建 OpenStack 客户端环境脚本 [root目录下创建]

```
vim  admin-openrc


export OS_PROJECT_DOMAIN_NAME=default
export OS_USER_DOMAIN_NAME=default
export OS_PROJECT_NAME=admin
export OS_USERNAME=admin
export OS_PASSWORD=123456
export OS_AUTH_URL=http://controller:35357/v3
export OS_IDENTITY_API_VERSION=3
export OS_IMAGE_API_VERSION=2
```

##### （2）加载环境变量

```
source admin-openrc
```

##### （3）查看是否可以形成token

```
openstack token issue


# 错误
The request you have made requires authentication. (HTTP 401) (Request-ID: req-15d232f4-9dbb-46ab-9037-25d428b6ec04)

# 原因
创建 OpenStack 客户端环境脚本时，登录密码错误
export OS_PASSWORD=123456
```

##### （4）开机自动挂载

```
echo 'source admin-openrc' >> /root/.bashrc
```

#### 7、测试

##### （1）退出登录

```
logout （快捷键 ctrl + d）
```

##### （2）查看

```
openstack token issue

+------------+----------------------------------------------------------------------------------------------
| Field      | Value                                                                                                         |
+------------+----------------------------------------------------------------------------------------------
| expires    | 2022-01-17T04:09:08.000000Z                                                                                   |
| id         | gAAAAABh5N3UrN738ClBL5plEMwNjfYm3mBCAJW5W_FufDp6IP-wkxrgQ_-                                                   |
|            | W68JYHMD9RC7h3dqvVdgWB0eL3yfxVZqbInJyZylRLwhxQWqVelwwjErcXOtM6LNXnh4SfPCHZtY6kMaCiGfY7Os-                     |
|            | suTB4ZFZlElFZTj8Cxqt_bNGQ7w_35lec-s                                                                           |
| project_id | b4b4a61b746748a99b2f5d97bcf9ef77                                                                             
|
| user_id    | d57a3900a26a428cb2de164c10615105                                                                             
|
+------------+----------------------------------------------------------------------------------------------
```



### 十三、Glance 服务（在控制节点上）

- #### 组件：

  - glance-api 作用： 接收镜像API的调用，比如镜像发现、恢复、存储
  - glance-registry 作用： 存储、处理和恢复镜像的元数据 [镜像的属性]

#### 1、创库授权

##### （1）登录数据库

```
mysql -u root -p
```

##### （2）创建 glance 数据库

```
CREATE DATABASE glance;
```

##### （3）对 glance 数据库授予恰当的权限

```
GRANT ALL PRIVILEGES ON glance.* TO 'glance'@'localhost' \
  IDENTIFIED BY 'GLANCE_DBPASS';
GRANT ALL PRIVILEGES ON glance.* TO 'glance'@'%' \
  IDENTIFIED BY 'GLANCE_DBPASS';
```

##### （4）退出数据库客户端

```
quit
```

#### 2、创建用户关联角色（在 keystone 上）

##### （1）创建 glance 用户

```
openstack user create --domain default --password GLANCE_PASS glance
```

##### （2）在 service 项目上给 glance 用户添加 admin 角色

```
openstack role add --project service --user glance admin
```

#### 3、创建服务并注册 API（在 keystone 上）

##### （1）创建 glance 服务实体

```
openstack service create --name glance --description "OpenStack Image" image
```

##### （2）创建镜像服务的 API 端点

```
openstack endpoint create --region RegionOne image public http://controller:9292
openstack endpoint create --region RegionOne image internal http://controller:9292
openstack endpoint create --region RegionOne image admin http://controller:9292
```

#### 4、安全并配置组件

##### （1）安装相关软件

```
yum -y install openstack-glance
```

##### （2）修改配置OpenStack镜像服务包括以下组件

- - ###### glance-api

  - 接收镜像API的调用，诸如镜像发现、恢复、存储

    

    ##### 1）`编辑文件 /etc/glance/glance-api.conf 并完成如下操作`

    ```
    # 备份文件
    cp /etc/glance/glance-api.conf{,.bak}
    egrep -v '^$|#' /etc/glance/glance-api.conf.bak > /etc/glance/glance-api.conf
    
    vim /etc/glance/glance-api.conf
    ```

    #####  2）在 [database] 部分，配置数据库连接

    ```
    connection = mysql+pymysql://glance:GLANCE_DBPASS@controller/glance
    ```

    ##### 3）在 [keystone_authtoken] 和 [paste_deploy] 部分，配置认证服务连接

    ```
    [keystone_authtoken]
    ...
    auth_uri = http://controller:5000
    auth_url = http://controller:35357
    memcached_servers = controller:11211
    auth_type = password
    project_domain_name = default
    user_domain_name = default
    project_name = service
    username = glance
    password = GLANCE_PASS
    ```

    ##### 4）认证方案为   keystone

    ```
    [paste_deploy]  
    ...
    flavor = keystone
    ```

    ##### 5）在 [glance_store] 部分，配置本地文件系统存储和镜像文件位置< 存储路径 >

    ```
    [glance_store]
    ...
    stores = file,http      # 存储方式  文件[file]，对象[http]存储
    default_store = file    
    filesystem_store_datadir = /var/lib/glance/images/
    ```

    

- ###### glance-registry

  - 存储、处理和恢复镜像的元数据，元数据包括项诸如大小和类型

    ##### 1）编辑文件 /etc/glance/glance-registry.conf 并完成如下操作

    ```
    # 备份文件
    cp /etc/glance/glance-registry.conf{,.bak}
    egrep -v "^$|#" /etc/glance/glance-registry.conf.bak > /etc/glance/glance-registry.conf
    
    vim /etc/glance/glance-registry.conf
    ```

    ##### 2）在 [database] 部分，配置数据库连接

    ```
    [database]
    ...
    connection = mysql+pymysql://glance:GLANCE_DBPASS@controller/glance
    ```

    ##### 3）在 [keystone_authtoken] 和 [paste_deploy] 部分，配置认证服务连接

    ```
    [keystone_authtoken]
    ...
    auth_uri = http://controller:5000
    auth_url = http://controller:35357
    memcached_servers = controller:11211
    auth_type = password
    project_domain_name = default
    user_domain_name = default
    project_name = service
    username = glance
    password = GLANCE_PASS
    
    
    [paste_deploy]
    ...
    flavor = keystone
    ```

    ##### 4）查看

    ```
    md5sum /etc/glance/glance-registry.conf
    46acabd81a65b924256f56fe34d90b8f  /etc/glance/glance-registry.conf
    ```

#### 5 、同步数据库

```
su -s /bin/sh -c "glance-manage db_sync" glance
```

#### 6、检测

```
mysql glance -e 'show tables;' 
```

#### 7、启动服务（启动镜像服务、配置他们开机自启）

```
systemctl enable openstack-glance-api.service openstack-glance-registry.service 
systemctl start openstack-glance-api.service openstack-glance-registry.service
```

#### 8、验证操作

##### （1）下载源镜像

```
wget http://download.cirros-cloud.net/0.3.4/cirros-0.3.4-x86_64-disk.img
```

##### （2）若出现 若wget命令用不了

```
yum install wget -y
```

##### （3）使用 [*QCOW2*](https://docs.openstack.org/mitaka/zh_CN/install-guide-rdo/common/glossary.html#term-qemu-copy-on-write-2-qcow2) 磁盘格式， [*bare*](https://docs.openstack.org/mitaka/zh_CN/install-guide-rdo/common/glossary.html#term-bare) 容器格式上传镜像到镜像服务并设置公共可见，这样所有的项目都可以访问它

```
openstack image create "cirros" \
  --file cirros-0.3.4-x86_64-disk.img \
  --disk-format qcow2 --container-format bare \
  --public
```

##### （4）确认镜像的上传并验证属性

```
openstack image list
+--------------------------------------+--------+--------+
| ID                                   | Name   | Status |
+--------------------------------------+--------+--------+
| f30589ce-a8bd-48d4-9267-eaa15b1efe52 | cirros | active |
+--------------------------------------+--------+--------+
```



### 十四、nova 计算服务 

#### （一）、在控制节点

#### 1、仓库授权

##### （1）登录数据库

```
mysql -u root -p
```

##### （2）创建 nova_api 和 nova 数据库， 并对数据库进行正确的授权

```
CREATE DATABASE nova_api;
CREATE DATABASE nova;

GRANT ALL PRIVILEGES ON nova_api.* TO 'nova'@'localhost' IDENTIFIED BY 'NOVA_DBPASS';
GRANT ALL PRIVILEGES ON nova_api.* TO 'nova'@'%' IDENTIFIED BY 'NOVA_DBPASS';
GRANT ALL PRIVILEGES ON nova.* TO 'nova'@'localhost' IDENTIFIED BY 'NOVA_DBPASS';
GRANT ALL PRIVILEGES ON nova.* TO 'nova'@'%' IDENTIFIED BY 'NOVA_DBPASS';
```

##### （3）退出数据库客户端

```
quit
```

#### 2、创建用户并关联角色

##### （1）创建 nova 用户

```
openstack user create --domain default \
--password NOVA_PASS nova

+-----------+----------------------------------+
| Field     | Value                            |
+-----------+----------------------------------+
| domain_id | e0353a670a9e496da891347c589539e9 |
| enabled   | True                             |
| id        | 8c46e4760902464b889293a74a0c90a8 |
| name      | nova                             |
+-----------+----------------------------------+
```

##### （2）**给 nova 用户添加 admin 角色**

```
openstack role add --project service --user nova admin
```

#### 3、创建服务并注册 api

##### （1）**创建 nova 服务实体**

```
openstack service create --name nova \
--description "OpenStack Compute" compute
+-------------+----------------------------------+
| Field       | Value                            |
+-------------+----------------------------------+
| description | OpenStack Compute                |
| enabled     | True                             |
| id          | 060d59eac51b4594815603d75a00aba2 |
| name        | nova                             |
| type        | compute                          |
+-------------+----------------------------------+
```

##### （2）**创建 Compute 服务 API 端点**

```
openstack endpoint create --region RegionOne \
compute public http://controller:8774/v2.1/%\(tenant_id\)s


openstack endpoint create --region RegionOne \
compute internal http://controller:8774/v2.1/%\(tenant_id\)s


openstack endpoint create --region RegionOne \
compute admin http://controller:8774/v2.1/%\(tenant_id\)s
```

#### 4、安全并配置组件

##### （1）**安装相关软件**

```
yum -y install openstack-nova-api openstack-nova-conductor openstack-nova-console openstack-nova-novncproxy openstack-nova-scheduler
```

##### （2）修改相关配置（编辑 `/etc/nova/nova.conf` 文件并完成下面的操作）

```
# 备份文件
cp /etc/nova/nova.conf{,.bak}

#过滤
egrep -v '^$|#' /etc/nova/nova.conf.bak  > /etc/nova/nova.conf

# 进入文件并修改
vim /etc/nova/nova.conf
```

##### **在 [DEFAULT]部分，只启用计算和元数据API：**

```
[DEFAULT]
...
enabled_apis = osapi_compute,metadata  # 启用了两个API，端口为 8774 和 8775
```

##### **在[api_database]和[database]部分，配置数据库的连接：**

```
[api_database]
...
connection = mysql+pymysql://nova:NOVA_DBPASS@controller/nova_api
[database]
...
connection = mysql+pymysql://nova:NOVA_DBPASS@controller/nova
```

##### **在 “[DEFAULT]” 和 “[oslo_messaging_rabbit]”部分，配置 “RabbitMQ” 消息队列访问：**

```
[DEFAULT]
...
rpc_backend = rabbit     # 指定消息队列 rabbit
```

```
[oslo_messaging_rabbit]    # 地址，用户和用户密码
...
rabbit_host = controller    
rabbit_userid = openstack
rabbit_password = RABBIT_PASS
```

##### **在 “[DEFAULT]” 和 “[keystone_authtoken]” 部分，配置认证服务访问：**

```
[DEFAULT]
...
auth_strategy = keystone
```

```
[keystone_authtoken]
...
auth_uri = http://controller:5000
auth_url = http://controller:35357
memcached_servers = controller:11211
auth_type = password
project_domain_name = default
user_domain_name = default
project_name = service
username = nova
password = NOVA_PASS
```

##### **在 [DEFAULT 部分，配置`my_ip` 来使用控制节点的管理接口的IP 地址。**

```
[DEFAULT]
...
my_ip = 192.168.125.128
```

##### **在 [DEFAULT] 部分，使能 Networking 服务：**

```
[DEFAULT]
...
use_neutron = True
firewall_driver = nova.virt.firewall.NoopFirewallDriver   # 禁用防火墙
```

- 注解
- *-- 默认情况下，计算服务使用内置的防火墙服务。由于网络服务包含了防火墙服务，你必须使用`nova.virt.firewall.NoopFirewallDriver`防火墙服务来禁用掉计算服务内置的防火墙服务*

 ##### **在[vnc]部分，配置VNC代理使用控制节点的管理接口IP地址**

```
[vnc]
...
vncserver_listen = $my_ip
vncserver_proxyclient_address = $my_ip
```

##### **在 [glance] 区域，配置镜像服务 API 的位置：**

```
[glance]
...
api_servers = http://controller:9292
```

##### 在 [oslo_concurrency] 部分，配置锁路径： [锁文件的作用： 防止脚本或任务重复执行] 

```
[oslo_concurrency]
...
lock_path = /var/lib/nova/tmp
```

#### 5、同步数据库

```
su -s /bin/sh -c "nova-manage api_db sync" nova
su -s /bin/sh -c "nova-manage db sync" nova
mysql nova_api -e "show tables;"
mysql nova -e "show tables;" 
```

#### 6、启动服务

```
systemctl enable openstack-nova-api.service openstack-nova-consoleauth.service openstack-nova-scheduler.service openstack-nova-conductor.service openstack-nova-novncproxy.service

systemctl start openstack-nova-api.service openstack-nova-consoleauth.service openstack-nova-scheduler.service openstack-nova-conductor.service openstack-nova-novncproxy.service
```

#### 7、查看日志

```
tail -f /var/log/nova/nova-* | grep ERRO     # 没有输出即为成功
```

#### 8、检测

```
openstack compute service list
+----+------------------+------------+----------+---------+-------+----------------------------+
| Id | Binary           | Host       | Zone     | Status  | State | Updated At                 |
+----+------------------+------------+----------+---------+-------+----------------------------+
|  1 | nova-consoleauth | controller | internal | enabled | up    | 2022-01-18T05:10:56.000000 |
|  2 | nova-scheduler   | controller | internal | enabled | up    | 2022-01-18T05:10:57.000000 |
|  3 | nova-conductor   | controller | internal | enabled | up    | 2022-01-18T05:10:57.000000 |
+----+------------------+------------+----------+---------+-------+----------------------------+
```



#### （二）计算节点

#### 1、安装相关软件

```
yum -y install openstack-nova-compute
```

#### 2、修改相关配置（**编辑 `/etc/nova/nova.conf` 文件并完成下面的操作**）

##### 备份文件

```
cp /etc/nova/nova.conf{,.bak}
egrep -v '^$|#' /etc/nova/nova.conf.bak  > /etc/nova/nova.conf
```

##### **在[DEFAULT]和 [`oslo_messaging_rabbit`]部分，配置RabbitMQ消息队列的连接**

```
[DEFAULT]
...
rpc_backend = rabbit
```

```
[oslo_messaging_rabbit]
...
rabbit_host = controller
rabbit_userid = openstack
rabbit_password = RABBIT_PASS
```

##### **在 “[DEFAULT]” 和 “[keystone_authtoken]” 部分，配置认证服务访问**

```
[DEFAULT]
...
auth_strategy = keystone
```

```
[keystone_authtoken]
...
auth_uri = http://controller:5000
auth_url = http://controller:35357
memcached_servers = controller:11211
auth_type = password
project_domain_name = default
user_domain_name = default
project_name = service
username = nova
password = NOVA_PASS
```

##### **在 [DEFAULT] 部分，配置 my_ip 选项**

```
[DEFAULT]
...
my_ip = 192.168.125.129
```

- *注释*
- *将其中的 MANAGEMENT_INTERFACE_IP_ADDRESS 替换为计算节点上的管理网络接口的IP 地址，例如 :ref:`example architecture <overview-example-architectures>`中所示的第一个节点 192.168.125.129

##### **在 [DEFAULT] 部分，使能 Networking 服务**

```
[DEFAULT]
...
use_neutron = True
firewall_driver = nova.virt.firewall.NoopFirewallDriver
```

- *注解*
- *缺省情况下，Compute 使用内置的防火墙服务。由于 Networking 包含了防火墙服务，所以你必须通过使用 nova.virt.firewall.NoopFirewallDriver 来去除 Compute 内置的防火墙服务*

##### **在[vnc]部分，启用并配置远程控制台访问**

```
[vnc]
...
enabled = True
vncserver_listen = 0.0.0.0
vncserver_proxyclient_address = $my_ip
novncproxy_base_url = http://controller:6080/vnc_auto.html
```

- *服务器组件监听所有的 IP 地址，而代理组件仅仅监听计算节点管理网络接口的 IP 地址。基本的 URL 指示您可以使用 web 浏览器访问位于该计算节点上实例的远程控制台的位置。*
- 注解:
- 如果你运行浏览器的主机无法解析controller 主机名，你可以将 controller替换为你控制节点管理网络的IP地址

##### **在 [glance] 区域，配置镜像服务 API 的位置**

```
[glance]
...
api_servers = http://controller:9292
```

##### **在 [oslo_concurrency] 部分，配置锁路径：** [锁文件的作用： 防止脚本或任务重复执行]

```
[oslo_concurrency]
...
lock_path = /var/lib/nova/tmp
```

#### 3、安全检测

##### （1）确定您的计算节点是否开启虚拟化； 如果输出结果为 1或非零数 就不需要修改， 如果为零就需要修改

```
egrep -c '(vmx|svm)' /proc/cpuinfo
```

##### （2）在 `/etc/nova/nova.conf` 文件的 `[libvirt]` 区域做出如下的编辑

```
[libvirt]
...
virt_type = qemu
cpu_mode = none
```

#### 4、启动

```
systemctl enable libvirtd.service openstack-nova-compute.service;
systemctl start libvirtd.service openstack-nova-compute.service
```

#### 5、测试

- 到 controller 节点

```
查看日志
tail -f /var/log/nova/nova-* | grep ERRO     //没有输出即为成功

openstack compute service list
+----+------------------+------------+----------+---------+-------+----------------------------+
| Id | Binary           | Host       | Zone     | Status  | State | Updated At                 |
+----+------------------+------------+----------+---------+-------+----------------------------+
|  1 | nova-consoleauth | controller | internal | enabled | up    | 2022-01-18T06:21:48.000000 |
|  2 | nova-scheduler   | controller | internal | enabled | up    | 2022-01-18T06:21:49.000000 |
|  3 | nova-conductor   | controller | internal | enabled | up    | 2022-01-18T06:21:49.000000 |
|  7 | nova-compute     | compute1   | nova     | enabled | up    | 2022-01-18T06:21:42.000000 |
+----+------------------+------------+----------+---------+-------+----------------------------+
```



###  十五、网络(neutron)服务

#### （一）控制节点

#### 1、创库授权

##### （1）登录数据库

```
mysql -u root -p
```

##### （2）**创建 neutron 数据库，并对 neutron 数据库授予合适的访问权限**

```
CREATE DATABASE neutron;

GRANT ALL PRIVILEGES ON neutron.* TO 'neutron'@'localhost' IDENTIFIED BY 'NEUTRON_DBPASS';
GRANT ALL PRIVILEGES ON neutron.* TO 'neutron'@'%' IDENTIFIED BY 'NEUTRON_DBPASS';
```

#### 2、创建用户并关联角色

##### （1）**创建neutron用户**

```
openstack user create --domain default --password NEUTRON_PASS neutron
+-----------+----------------------------------+
| Field     | Value                            |
+-----------+----------------------------------+
| domain_id | e0353a670a9e496da891347c589539e9 |
| enabled   | True                             |
| id        | b20a6692f77b4258926881bf831eb683 |
| name      | neutron                          |
+-----------+----------------------------------+
```

##### （2）**添加admin 角色到 neutron 用户：**

```
openstack role add --project service --user neutron admin
```

#### 3、创建服务注册api

##### （1）**创建 neutron 服务实体**

```
openstack service create --name neutron \
--description "OpenStack Networking" network
+-------------+----------------------------------+
| Field       | Value                            |
+-------------+----------------------------------+
| description | OpenStack Networking             |
| enabled     | True                             |
| id          | f71529314dab4a4d8eca427e701d209e |
| name        | neutron                          |
| type        | network                          |
+-------------+----------------------------------+
```

##### （2）**创建网络服务API端点**

```
openstack endpoint create --region RegionOne \
network public http://controller:9696


openstack endpoint create --region RegionOne \
network internal http://controller:9696


openstack endpoint create --region RegionOne \
network admin http://controller:9696
```

#### 4、公共网络

##### （1）安装组件

```
yum -y install openstack-neutron openstack-neutron-ml2 openstack-neutron-linuxbridge ebtables
openstack-neutron-linuxbridge：网桥，用于创建桥接网卡
ebtables：防火墙guize
```

##### （2）配置服务组件（**编辑`/etc/neutron/neutron.conf` 文件并完成如下操作**）

##### 备份文件

```
cp /etc/neutron/neutron.conf{,.bak} 
egrep -v '^$|#' /etc/neutron/neutron.conf.bak  > /etc/neutron/neutron.conf
```

##### **在 [database] 部分，配置数据库访问**

```
[database]
...
connection = mysql+pymysql://neutron:NEUTRON_DBPASS@controller/neutron
```

##### **在`[DEFAULT]`部分，启用ML2插件并禁用其他插件**

```
[DEFAULT]
...
core_plugin = ml2     # 核心插件
service_plugins =     # 服务插件为空 [禁用]
```

##### **在 “[DEFAULT]” 和 “[oslo_messaging_rabbit]”部分，配置 “RabbitMQ” 消息队列的连接**

```
[DEFAULT]
...
rpc_backend = rabbit
```

```
[oslo_messaging_rabbit]
...
rabbit_host = controller
rabbit_userid = openstack
rabbit_password = RABBIT_PASS
```

##### **在 “[DEFAULT]” 和 “[keystone_authtoken]” 部分，配置认证服务访问：**

```
[DEFAULT]
...
auth_strategy = keystone
```

```
[keystone_authtoken]
...
auth_uri = http://controller:5000
auth_url = http://controller:35357
memcached_servers = controller:11211
auth_type = password
project_domain_name = default
user_domain_name = default
project_name = service
username = neutron
password = NEUTRON_PASS
```

##### **在`[DEFAULT]`和`[nova]`部分，配置网络服务来通知计算节点的网络拓扑变化：**

```
[DEFAULT]      # 用于nova 和 neutron 之间互相通信
...
notify_nova_on_port_status_changes = True
notify_nova_on_port_data_changes = True
```

```
[nova]
...
auth_url = http://controller:35357
auth_type = password
project_domain_name = default
user_domain_name = default
region_name = RegionOne
project_name = service
username = nova
password = NOVA_PASS
```

##### **在 [oslo_concurrency] 部分，配置锁路径**

```
[oslo_concurrency]
...
lock_path = /var/lib/neutron/tmp
```

##### （3）配置 Modular Layer 2 (ML2) 插件（**编辑`/etc/neutron/plugins/ml2/ml2_conf.ini`文件并完成以下操作：**）

##### 备份文件

```
cp /etc/neutron/plugins/ml2/ml2_conf.ini{,.bak}
egrep -v '^$|#' /etc/neutron/plugins/ml2/ml2_conf.ini.bak > /etc/neutron/plugins/ml2/ml2_conf.ini
```

##### **在[ml2]部分，启用flat和VLAN网络：**

```
[ml2]
...
type_drivers = flat,vlan       # flat [桥接网络]   
```

##### **在[ml2]部分，禁用私有网络： **

```
[ml2]
...
tenant_network_types =    # 禁用私有网络
```

##### **在[ml2]部分，启用Linuxbridge机制：**

````
[ml2]
...
mechanism_drivers = linuxbridge    # 桥接
````

##### **在[ml2] 部分，启用端口安全扩展驱动：**

````
[ml2]
...
extension_drivers = port_security   [与安全组有关]
````

##### **在[ml2_type_flat]部分，配置公共虚拟网络为flat网络**

```
[ml2_type_flat]
...
flat_networks = provider
```

##### **在 [securitygroup]部分，启用 [\*ipset\*]增加安全组规则的高效性：**

```
[securitygroup]
...
enable_ipset = True
```



#### （3）配置Linuxbridge代理（**编辑`/etc/neutron/plugins/ml2/linuxbridge_agent.ini`文件并且完成以下操作：**）

##### 备份文件

```
cp /etc/neutron/plugins/ml2/linuxbridge_agent.ini{,.bak}
egrep -v '^$|#' /etc/neutron/plugins/ml2/linuxbridge_agent.ini.bak > /etc/neutron/plugins/ml2/linuxbridge_agent.ini
 
vim /etc/neutron/plugins/ml2/linuxbridge_agent.ini
```

##### **在[linux_bridge]部分，将公共虚拟网络和公共物理网络接口对应起来**

```
[linux_bridge]
physical_interface_mappings = provider:ens33
```

- *将 PUBLIC_INTERFACE_NAME 替换为底层的物理公共网络接口： ens33*

##### *将 PUBLIC_INTERFACE_NAME 替换为底层的物理公共网络接口：ens33*

```
[vxlan]
enable_vxlan = False
```

##### **在 `[securitygroup]`部分，启用安全组并配置 Linuxbridge iptables firewall driver:**

````
[securitygroup]
...
enable_security_group = True
firewall_driver = neutron.agent.linux.iptables_firewall.IptablesFirewallDriver
````



#### （4）配置DHCP代理（**编辑 /etc/neutron/dhcp_agent.ini 文件并完成下面的操作：**）

##### **在`[DEFAULT]`部分，配置Linuxbridge驱动接口，DHCP驱动并启用隔离元数据，这样在公共网络上的实例就可以通过网络来访问元数据**

```
[DEFAULT]
...
interface_driver = neutron.agent.linux.interface.BridgeInterfaceDriver
dhcp_driver = neutron.agent.linux.dhcp.Dnsmasq
enable_isolated_metadata = True
```

#### （5）配置元数据代理（**编辑 /etc/neutron/metadata_agent.ini 文件并完成以下操作**）

- 作用
- **访问实例的凭证**

##### **在`[DEFAULT]` 部分，配置元数据主机以及共享密码**

````\
[DEFAULT]
...
nova_metadata_ip = controller
metadata_proxy_shared_secret = METADATA_SECRET
````



#### （6）为nova配置网络服务（**编辑/etc/nova/nova.conf文件并完成以下操作**）

##### **在`[neutron]`部分，配置访问参数，启用元数据代理并设置密码：**

```
[neutron]
...
url = http://controller:9696
auth_url = http://controller:35357
auth_type = password
project_domain_name = default
user_domain_name = default
region_name = RegionOne
project_name = service
username = neutron
password = NEUTRON_PASS
service_metadata_proxy = True
metadata_proxy_shared_secret = METADATA_SECRET
```



#### 5、超链接

- 网络服务初始化脚本需要一个超链接 `/etc/neutron/plugin.ini`指向ML2插件配置文件`/etc/neutron/plugins/ml2/ml2_conf.ini`

```
ln -s /etc/neutron/plugins/ml2/ml2_conf.ini /etc/neutron/plugin.ini
```

#### 6、同步数据库

```
su -s /bin/sh -c "neutron-db-manage --config-file /etc/neutron/neutron.conf \
  --config-file /etc/neutron/plugins/ml2/ml2_conf.ini upgrade head" neutron
```

#### 7、重启服务

```
systemctl restart openstack-nova-api.service
systemctl enable neutron-server.service neutron-linuxbridge-agent.service neutron-dhcp-agent.service neutron-metadata-agent.service
systemctl start neutron-server.service neutron-linuxbridge-agent.service neutron-dhcp-agent.service neutron-metadata-agent.service
```

#### 8、检测

```
查看日志
tail -f /var/log/nova/nova-* | grep ERRO     //没有输出即为成功

neutron agent-list
+--------------------+--------------------+------------+-------------------+-------+----------------+-------
| id                 | agent_type         | host       | availability_zone | alive | admin_state_up | binary                |
+--------------------+--------------------+------------+-------------------+-------+----------------+------
| 06460a49-8b7d-     | Linux bridge agent | controller |                   | :-)   | True           | neutron-linuxbridge-  |
| 4f4f-871a-         |                    |            |                   |       |                | agent                 |
| 1eb84bd04da1       |                    |            |                   |       |                |                       |
| 0cc05ef8-1286-4339 | Metadata agent     | controller |                   | :-)   | True           | neutron-metadata-     |
| -8265-df0e96068589 |                    |            |                   |       |                | agent                 |
| dfcf4acd-13a6-48f6 | DHCP agent         | controller | nova              | :-)   | True           | neutron-dhcp-agent    |
| -ab8a-9d39ab050658 |                    |            |                   |       |                |                       |
+--------------------+--------------------+------------+-------------------+-------+----------------+------
```



#### （二）计算节点

#### 1、安装组件

```
yum -y install openstack-neutron-linuxbridge ebtables ipset
```

#### 2、配置通用组件（**编辑`/etc/neutron/neutron.conf` 文件并完成如下操作**）

##### 备份文件

```
cp /etc/neutron/neutron.conf{,.bak}
egrep -v '^$|#' /etc/neutron/neutron.conf.bak  > /etc/neutron/neutron.conf

vim /etc/neutron/neutron.conf
```

##### **在 “[DEFAULT]” 和 “[oslo_messaging_rabbit]”部分，配置 “RabbitMQ” 消息队列的连接**

```
[DEFAULT]
...
rpc_backend = rabbit
```

```
[oslo_messaging_rabbit]
...
rabbit_host = controller
rabbit_userid = openstack
rabbit_password = RABBIT_PASS
```

##### **在 “[DEFAULT]” 和 “[keystone_authtoken]” 部分，配置认证服务访问**

```
[DEFAULT]
...
auth_strategy = keystone
```

```
[keystone_authtoken]
...
auth_uri = http://controller:5000
auth_url = http://controller:35357
memcached_servers = controller:11211
auth_type = password
project_domain_name = default
user_domain_name = default
project_name = service
username = neutron
password = NEUTRON_PASS
```

##### **在 [oslo_concurrency] 部分，配置锁路径**

```
[oslo_concurrency]
...
lock_path = /var/lib/neutron/tmp
```



#### 3、公共网络（配置Linuxbridge代理）

- **由于该配置与控制节点一样，即复制到计算节点即可**

  ```
  scp -r 10.0.0.11:/etc/neutron/plugins/ml2/linuxbridge_agent.ini /etc/neutron/plugins/ml2/linuxbridge_agent.ini
  ```

  

#### 4、为nova配置网络服务（**编辑`/etc/nova/nova.conf`文件并完成下面的操作**）

- **在`[neutron]` 部分，配置访问参数**

  ```
  [neutron]
  ...
  url = http://controller:9696
  auth_url = http://controller:35357
  auth_type = password
  project_domain_name = default
  user_domain_name = default
  region_name = RegionOne
  project_name = service
  username = neutron
  password = NEUTRON_PASS
  ```

  

#### 5、重启服务

```
systemctl restart openstack-nova-compute.service
systemctl enable neutron-linuxbridge-agent.service
systemctl start neutron-linuxbridge-agent.service
```

#### 6、检测

##### （1）查看日志

```
tail -f /var/log/nova/nova-* | grep ERRO     //没有输出即为成功
```

- 到**控制节点**

  ```
  neutron agent-list
  +--------------------+--------------------+------------+-------------------+-------+----------------+----
  | id                 | agent_type         | host       | availability_zone | alive | admin_state_up | binary                |
  +--------------------+--------------------+------------+-------------------+-------+----------------+----
  | 06460a49-8b7d-     | Linux bridge agent | controller |                   | :-)   | True           | neutron-linuxbridge-  |
  | 4f4f-871a-         |                    |            |                   |       |                | agent                 |
  | 1eb84bd04da1       |                    |            |                   |       |                |                       |
  | 0cc05ef8-1286-4339 | Metadata agent     | controller |                   | :-)   | True           | neutron-metadata-     |
  | -8265-df0e96068589 |                    |            |                   |       |                | agent                 |
  | 96919fa9-fc83-4c95 | Linux bridge agent | compute1   |                   | :-)   | True           | neutron-linuxbridge-  |
  | -be8d-abdb9e507f2f |                    |            |                   |       |                | agent                 |
  | dfcf4acd-13a6-48f6 | DHCP agent         | controller | nova              | :-)   | True           | neutron-dhcp-agent    |
  | -ab8a-9d39ab050658 |                    |            |                   |       |                |                       |
  +--------------------+--------------------+------------+-------------------+-------+----------------+----
  ```

  







CACHES = {
    'default': {
        'BACKEND': 'django.core.cache.backends.locmem.LocMemCache',
    },
}



