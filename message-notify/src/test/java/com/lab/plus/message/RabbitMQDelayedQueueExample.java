package com.lab.plus.message;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQDelayedQueueExample {
    private static final String EXCHANGE_NAME = "normal_exchange";
    private static final String DEAD_LETTER_EXCHANGE_NAME = "dead_letter_exchange";
    private static final String QUEUE_NAME = "normal_queue";
    private static final String DEAD_LETTER_QUEUE_NAME = "dead_letter_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("61.169.134.102");
        factory.setUsername("admin");
        factory.setPassword("admin123");

        // 创建连接
        Connection connection = factory.newConnection();

        // 创建通道
        Channel channel = connection.createChannel();

        // 声明死信交换器
        channel.exchangeDeclare(DEAD_LETTER_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 声明死信队列
        channel.queueDeclare(DEAD_LETTER_QUEUE_NAME, false, false, false, null);
        channel.queueBind(DEAD_LETTER_QUEUE_NAME, DEAD_LETTER_EXCHANGE_NAME, "routing_key");

        // 声明普通交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 为普通队列设置死信交换器和路由键
        Map<String, Object> argsMap = new HashMap<>();
        argsMap.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_NAME);
        argsMap.put("x-dead-letter-routing-key", "routing_key");

        // 声明普通队列，并设置消息的 TTL（这里设置为 5000 毫秒，即 5 秒）
        argsMap.put("x-message-ttl", 10000);
        channel.queueDeclare(QUEUE_NAME, false, false, false, argsMap);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "routing_key");

        // 生产者发送消息
        String message = "This is a delayed message";
        channel.basicPublish(EXCHANGE_NAME, "routing_key", null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        // 消费者从死信队列接收消息
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + msg + "'");
        };
        channel.basicConsume(DEAD_LETTER_QUEUE_NAME, true, deliverCallback, consumerTag -> { });

        // 关闭通道和连接
        // 这里不关闭，因为消费者需要持续监听
        // channel.close();
        // connection.close();
    }
}
