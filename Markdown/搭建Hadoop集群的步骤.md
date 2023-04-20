## 搭建Hadoop集群的步骤

### 一、创建虚拟机



### 二、获取到虚拟机IP

#### 1、编辑文件ifcfg-ens33

```
vi /etc/sysconfig/network-scripts/ifcfg-ens33
```

#### 2、重启网络服务

```
service network restart
```

#### 3、查看IP

```
ip addr
```



### 三、安装jdk

#### 1、卸载虚拟机自带的JDK

##### (1）查询是否安装Java软件

```
rpm -qa | grep java
```

##### （2）如果安装的版本低于1.7，卸载该JDK

```
yum -y remove java-1.*
```

#### 2、上传JDK包

#### 3、创建存放JDK的目录（cd /opt/soft）

```
mkdir jdk
```

#### 4、解压jdk压缩文件到jdk目录下

```
tar -zxvf jdk-8u341-linux-x64.tar.gz -C jdk
```



### 四、Hadoop的安装

#### 1、上传Hadoop包

#### 2、创建存放Hadoop的目录（cd /opt/soft）

```
mkdir  hadoop
```

#### 3、解压hadoop压缩文件到hadoop目录下

```
tar -zxvf hadoop-3.2.4.tar.gz -C hadoop
```

### 五、配置环境变量

#### 1、先获取Hadoop和jdk的路径

```
pwd
```

#### 2、复制路径

#### 3、进入到hadoop-eco.sh文件编辑

```
vim /etc/profile.d/hadoop-eco.sh
```

#### 4、在hadoop-eco.sh文件中输入

```
#JAVA_HOME
export JAVA_HOME=/opt/soft/jdk/jdk1.8.0_341
export PATH=$PATH:$JAVA_HOME/bin

#HADOOP_HOME
export  HADOOP_HOME=/opt/soft/hadoop/hadoop-3.2.4
export PATH=$PATH:$HADOOP_HOME/bin
export PATH=$PATH:$HADOOP_HOME/sbin
```

#### 5、保存并推出编辑

```
wq
```

#### 6、让修改后的文件生效

```
source /etc/profile.d/hadoop-eco.sh
```

#### 7、查看是否配置成功

```
java -version
hadoop
```



### 六、设置免密登录

#### 1、去到家目录

```
cd ~
```

#### 2、生成公钥和私钥

```
ssh-keygen  -t  rsa
```

#### 3、添加映射（或者不添加映射 ssh-copy-id -i root@192.168.66.222）

```
vim /etc/hosts

  ip          需改后的主机名
192.168.66.134  hadoop134
192.168.66.135  hadoop135
192.168.66.136  hadoop136
```

#### 4、将公钥拷贝到要免密登录的目标机器上

```
ssh-copy-id  hadoop134
```



### 七、配置文件

#### core-site.xml

``` 
<!-- 指定HDFS中NameNode的地址 -->
<!-- 在java代码中配置hadoop需要调用 -->
<property>
	<name>fs.defaultFS</name>
	<value>hdfs://hadoop128:9000</value>
</property>

<!-- 运行Hadoop时产生的临时文件存储的目录 -->
<property>
	<name>hadoop.tmp.dir</name>
	<value>/opt/data/hadoop</value>
</property>
```



#### hdfs-site.xml

``` 
<!-- 指定namenode的访问地址和端口 -->
<property>
	<name>dfs.namenode.http-address</name>
	<value>hadoop128:50070</value>
</property>

<!-- 2nn web访问端口 -->
<property>
	<name>dfs.namenode.secondary.http-address</name>
	<value>hadoop129:50090</value>
</property>

<!-- 副本个数 -->
<property>
	<name>dfs.replication</name>
	<value>3</value>
</property>

<!-- 设置HDFS的文件权限 -->
<property>
	<name>dfs.permissions</name>
	<value>false</value>
</property>
```



#### yarn-site.xml

``` 
<!-- 配置yarn主节点的位置(指定YARN的ResourceManager的地址) -->
<property>
	<name>yarn.resouremanager.hostname</name>
	<value>hadoop129</value>
</property>

<property>
	<name>yarn.nodemanager.aux-services</name>
	<value>mapreduce_shuffle</value>
</property>

 <!--关闭虚拟内存验证-->
<property>
	<name>yarn.nodemanager.vmem-check-enabled</name>
	<value>false</value>
</property>

<!--关闭物理内存验证-->
<property>
	<name>yarn.nodemanager.pmem-check-enabled</name>
	<value>false</value>
</property>
```



#### mapred-site.xml

``` 
<!-- 指定MR运行的框架，在YARN上 -->
<property>
	<name>mapreduce.framework.name</name>
	<value>yarn</value>
</property>
```



#### workers

````
hadoop134
hadoop135
hadoop136

注意：该文件中添加的内容结尾不允许有空格，文件中不允许有空行。
````



### 八、关闭防火墙

#### 1、暂时关闭防火墙

```
systemctl stop firewalld
```

#### 2、永久关闭防火墙

```
systemctl disable firewalld
```

#### 3、查看防火墙状态

```
systemctl status firewalld

注意：#提示 Active: inactive (dead)  则关闭成功
```



### 九、格式化namenode

#### 1、进到hadoop目录

```
cd  /opt/soft/hadoop/hadoop-3.2.4/
```

#### 2、在格式化

```
hdfs namenode -format
```



### 十、启动集群（/opt/soft/hadoop/hadoop-3.2.4/）

```
sbin/start-dfs.sh
sbin/start-yarn.sh
```

#### 2、如果启动错误

##### (1)、将start-dfs.sh，stop-dfs.sh(在hadoop安装目录的sbin里)两个文件顶部添加以下参数

```
HDFS_DATANODE_USER=root
HADOOP_SECURE_DN_USER=hdfs
HDFS_NAMENODE_USER=root
HDFS_SECONDARYNAMENODE_USER=root
```

##### (2)、将start-yarn.sh，stop-yarn.sh(在hadoop安装目录的sbin里)两个文件顶部添加以下参数

```
YARN_RESOURCEMANAGER_USER=root
HADOOP_SECURE_DN_USER=yarn
YARN_NODEMANAGER_USER=root
```

#### 3、查看进程

```
jps
```

