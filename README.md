# jafka
jafka 开源研究

一、编译步骤对应 myeclipse  jafkatest工程
Maven 下的编译：
mvn clean package assembly:single -Dmaven.test.skip=true
1、Start the server
bash bin/server.sh conf/server.properties
./run.sh start

或者在myelpse 下面运行  com.sohu.kafka   jafka 类
运行参数设置为  conf/server.properties（从配置文件中读入参数）
或者 运行 ./run.sh start （本质和上面的是一样的）
Java com.sohu.jafka  conf/server.properties
2、Start the producer

bin/producer-console.sh --broker-list 0:localhost:9092 --topic demo

Java com.ConsoleProducer  --broker-list 0:localhost:9092 --topic demo


3、start the consumer
 bin/simple-consumer-console.sh --topic demo --server jafka://localhost:9092


Java com. SimipleConsoleConsumer --topic demo --server jafka://localhost:9092


4、总结 以后自己写linux 运行脚本


二、使用 zookeeper 开发
需要修改 conf/server.properties配置文件
Enable zookeeper=true
1、bin/zookeeper-server.sh conf/zookeeper.properties 
2、bin/server.sh conf/server.properties 
3、bin/producer-console.sh --zookeeper localhost:2181 --topic demo
4、bin/consumer-console.sh --zookeeper localhost:2181 --topic demo



三 运行实例：
1、producer、consumer、server都在Linux
Jafka安装在/usr/jafka-1.6/下面，producer、consumer、server都在Linux下面启动
Cd /usr/jafka-1.6/
1、bin/server.sh conf/server.properties   (运行Server)
2、bin/producer-console.sh --broker-list 0:localhost:9092 --topic demo (运行 producer)
3、bin/simple-consumer-console.sh --topic demo --server jafka://localhost:9092(运行consumer)
2、producer、consumer在myeclipse上面，server在Linux下面
Producer运行 com. Testproducer 
Server 在 /usr/jafka-1.6/ 运行 bin/server.sh conf/server.properties   (运行Server)
Consumer 运行 JafkaTest 工程下面 com. SimipleConsoleConsumer 运行参数设置 --topic demo --server jafka://192.168.138.128:9092
3、producer,consumer全在Linux上面，启动zookeeper进行跑
需要修改 conf/server.properties配置文件
Enable zookeeper=true
1)	bin/zookeeper-server.sh conf/zookeeper.properties 
2)	bin/server.sh conf/server.properties 
