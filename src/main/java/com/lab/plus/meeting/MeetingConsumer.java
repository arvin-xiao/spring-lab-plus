package com.lab.plus.meeting;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MeetingConsumer {
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMeetingNotification(Meeting meeting) {
        // 模拟推送给订阅用户
        System.out.println("+++++++++++++++++++++++>>>>>>>>>>>>>>推送会议通知: " + meeting);
    }
}
