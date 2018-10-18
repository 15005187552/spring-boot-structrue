#### KAFKA 安装
  - http://kafka.apache.org/downloads中 获取下载链接 下载包
  - 解压包，并修改config/server.properties 中 log.dirs 为自己的目录
  - 修改 config/zookeeper.properties 中 dataDir=/tmp/zookeeper 为自己的目录
  - bin/zookeeper-server-start.sh config/zookeeper.properties 启动zookeeper
  - bin/kafka-server-start.sh config/server.properties 启动kafka
#### KAFKA 远程配置
  - conf/server.properties 中 放开listeners=PLAINTEXT://:9092的注释
  - 重启kafka
#### 消费者性质
  - 消费者用消费者组名称标记自己，并且发布到主题的每个记录被传递到每个订阅消费者组中的一个消费者实例。消费者实例可以在不同的进程中或在不同的机器上。
  - 如果所有消费者实例具有相同的消费者组，则记录将有效地在消费者实例上进行负载均衡。
  - 如果所有消费者实例具有不同的消费者组，则每个记录将被广播给所有消费者进程。
