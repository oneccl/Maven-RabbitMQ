package com.cc.rabbitmqconsumer.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * Author: CC
 * E-mail: 203717588@qq.com
 * Date: 2023/3/19
 * Time: 17:34
 * Description:
 */

// 2、发布/订阅模型: 角色：生产者、消费者、消息队列、Exchange交换机
// Exchange交换机的3种类型：
/*
Fanout：广播，将消息交给所有绑定到交换机的队列
Direct：定向，把消息交给符合指定routing key的队列
Topic：通配符，把消息交给符合routing pattern（路由模式）的队列
*/
// Fanout
/*
消息发送流程：
1）可以有多个队列
2）每个队列都要绑定到Exchange（交换机）
3）生产者发送的消息，只能发送到交换机，交换机来决定要发给哪个队列，生产者无法决定
4）交换机把消息发送给绑定过的所有队列
5）订阅队列的消费者都能拿到消息
*/
@Configuration
public class FanoutConfig {

    // Fanout类型交换机
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("exchange.fanout");
    }

    // 第1个队列
    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanout.queue1");
    }

    // 绑定队列和交换机
    @Bean
    public Binding bindingQueue1(Queue fanoutQueue1, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }

    // 第2个队列
    @Bean
    public Queue fanoutQueue2(){
        return new Queue("fanout.queue2");
    }

    // 绑定队列和交换机
    @Bean
    public Binding bindingQueue2(Queue fanoutQueue2, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }

}
