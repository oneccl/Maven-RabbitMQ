package com.cc.rabbitmqconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * Author: CC
 * E-mail: 203717588@qq.com
 * Date: 2023/3/19
 * Time: 17:03
 * Description:
 */
@Component
public class BasicQueueListener {

    // 1、简单模型、任务模型
    @RabbitListener(queues = "basic.queue")
    public void basicQueueReceiver(String msg){
        System.out.println("Consumer接收到消息: "+msg);
    }

    // 2、发布/订阅模型：
    // A、Fanout
    @RabbitListener(queues = "fanout.queue1")
    public void fanoutQueue1Receiver(String msg) {
        System.out.println("消费者1接收到Fanout消息: " + msg);
    }

    @RabbitListener(queues = "fanout.queue2")
    public void fanoutQueue2Receiver(String msg) {
        System.out.println("消费者2接收到Fanout消息: " + msg);
    }

    // C、Topic
    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(name = "topic.queue1"),
            exchange = @Exchange(name = "exchange.topic", type = ExchangeTypes.TOPIC),
            key = "China.#"
    ))
    public void topicQueue1Receiver(String msg){
        System.out.println("消费者接收到topic.queue1的消息: " + msg);
    }

    @RabbitListener(
            bindings = @QueueBinding(value = @Queue(name = "topic.queue2"),
            exchange = @Exchange(name = "exchange.topic", type = ExchangeTypes.TOPIC),
            key = "#.News"
    ))
    public void topicQueue2Receiver(String msg){
        System.out.println("消费者接收到topic.queue2的消息: " + msg);
    }



}
