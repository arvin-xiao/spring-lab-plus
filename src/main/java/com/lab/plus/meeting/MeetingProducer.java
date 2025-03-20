package com.lab.plus.meeting;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class MeetingProducer {

    private final RabbitTemplate rabbitTemplate;

    public MeetingProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMeetingNotification(Meeting meeting) {
        long delay = calculateDelay(meeting.getStartTime());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, meeting, message -> {
            message.getMessageProperties().setHeader("x-delay", delay);
            return message;
        });
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>开始推送会议通知: " + meeting);
    }

    public static long calculateDelay(LocalDateTime meetingStartTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneDayBefore = meetingStartTime.minusDays(1);
        if (oneDayBefore.isBefore(now)) {
            return 0;
        }
        return Duration.between(now, oneDayBefore).toMillis();
    }

}
