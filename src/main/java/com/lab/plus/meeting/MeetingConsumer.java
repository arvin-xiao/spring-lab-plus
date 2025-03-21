package com.lab.plus.meeting;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class MeetingConsumer {
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMeetingNotification(Meeting meeting, org.springframework.messaging.Message<Meeting> springMessage) {
        Map<String, Object> headers = springMessage.getHeaders();
        String messageType = (String) headers.get("message-type");
        switch (Objects.requireNonNull(messageType)) {
            case "会议开始前一天通知":
                System.out.println("推送会议开始前一天通知: " + meeting);
                break;
            case "会议开始前两小时提醒":
                System.out.println("推送会议开始前两小时提醒: " + meeting);
                break;
            case "会议开始通知":
                System.out.println("推送会议开始通知: " + meeting);
                break;
            default:
                System.out.println("未知类型通知: " + meeting);
        }
    }
}
