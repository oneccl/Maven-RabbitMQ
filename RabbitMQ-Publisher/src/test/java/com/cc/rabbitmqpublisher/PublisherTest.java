package com.cc.rabbitmqpublisher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created with IntelliJ IDEA.
 * Author: CC
 * E-mail: 203717588@qq.com
 * Date: 2023/3/19
 * Time: 16:40
 * Description:
 */

/*
RabbitMQ中的一些角色：
1、publisher：生产者
2、consumer：消费者
3、exchange：交换机，负责消息路由
4、queue：队列，存储消息
5、virtualHost：虚拟主机，隔离不同租户的exchange、queue、消息的隔离
*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class PublisherTest {

    @Autowired
    RabbitTemplate rabbitTemplate;

    // 1、角色：生产者、消费者、消息队列
    // A、Basic Queue 简单队列模型
    @Test
    public void basicQueue(){
        // 1）、建立连接（application.yml配置）
        // 2）、创建队列
        String queueName = "basic.queue";
        String message = "Hello Spring AMQP";
        // 3）、发送消息
        rabbitTemplate.convertAndSend(queueName,message);
    }

    // B、Work Queue 任务模型：多个消费者共同消费一个队列中的消息（可能出现消息堆积现象）
    // 结论：消息是平均分配给每个消费者，并没有考虑到不同消费者的处理能力
    // 多个消费者绑定到一个队列，同一条消息只会被一个消费者处理
    // 通过设置prefetch来控制消费者预取的消息数量
    // 能者多劳配置：在消费者服务的application.yml中添加：
    /*
    spring:
      rabbitmq:
        listener:
          simple:
            prefetch: 1  # 每次只能获取一条消息，处理完成才能获取下一个消息
    */

    // 2、发布/订阅模型: 角色：生产者、消费者、消息队列、Exchange交换机
    // A、Fanout 广播，将消息交给所有绑定到交换机的队列
    // 见conf/FanoutConfig.java类
    @Test
    public void fanoutExchange(){
        // 交换机名称
        String exchangeName = "exchange.fanout";
        // 消息
        String message = "hello exchange fanout";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }
    // 结论: 交换机的作用:
    /*
    1)接收publisher发送的消息
    2)将消息按照规则路由到与之绑定的队列
    3)不能缓存消息，路由失败，消息丢失
    4)FanoutExchange会将消息路由到每个绑定的队列
    */
    // B、Direct（暂未写）定向，把消息交给符合指定routing key的队列
    // C、Topic 通配符，把消息交给符合routing pattern（路由模式）的队列
    // 通配符规则：1）item.# 匹配一个或多个 2）item.* 只能匹配一个
    @Test
    public void topicExchange() {
        // 交换机名称
        String exchangeName = "exchange.topic";
        // 消息
        String message = "topic: China.News";
        // 发送消息 [China.News,Japan.News,China.Date,Japan.Date]
        rabbitTemplate.convertAndSend(exchangeName, "China.News", message);
    }
    // 结论：Direct交换机与Topic交换机区别
    // Topic交换机接收的消息RoutingKey必须是多个单词，以 **.**分割
    // Topic交换机与队列绑定时的BindingKey可以指定通配符


    // 消息转换器
    // 发送消息: Spring会序列化（JDK序列化）消息为字节发送给MQ
    // 接收消息: Spring会将字节反序列化为Java对象
    // JDK序列化存在问题：
    /*
    1）数据体积过大
    2）有安全漏洞
    3）可读性差
    */
    // 配置JSON转换器: 见pom.xml文件

}
