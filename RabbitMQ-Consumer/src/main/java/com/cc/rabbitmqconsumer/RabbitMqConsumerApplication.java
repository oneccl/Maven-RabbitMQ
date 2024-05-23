package com.cc.rabbitmqconsumer;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RabbitMqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqConsumerApplication.class, args);
    }

    // 为使消息体的体积更小、可读性更高，可以使用JSON方式来做序列化和反序列化，在启动类中配置消息转换器
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

}
