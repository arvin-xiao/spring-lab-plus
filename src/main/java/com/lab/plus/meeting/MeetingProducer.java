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
        // 会议开始前一天通知
        LocalDateTime oneDayBefore = meeting.getStartTime().minusSeconds(40);
        long oneDayDelay = calculateDelay(oneDayBefore);
        sendDelayedMessage(meeting, oneDayDelay, "会议开始前一天通知");

        // 会议开始前两小时通知
        LocalDateTime twoHoursBefore = meeting.getStartTime().minusSeconds(20);
        long twoHoursDelay = calculateDelay(twoHoursBefore);
        sendDelayedMessage(meeting, twoHoursDelay, "会议开始前两小时提醒");

        // 会议开始时通知
        long startDelay = calculateDelay(meeting.getStartTime());
        sendDelayedMessage(meeting, startDelay, "会议开始通知");
    }

    private void sendDelayedMessage(Meeting meeting, long delay, String messageType) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.QUEUE_NAME, meeting, message -> {
            message.getMessageProperties().setHeader("x-delay", delay);
            message.getMessageProperties().setHeader("message-type", messageType);
            return message;
        });
    }

    public static long calculateDelay(LocalDateTime targetTime) {
        LocalDateTime now = LocalDateTime.now();
        if (targetTime.isBefore(now)) {
            return 0;
        }
        return Duration.between(now, targetTime).toMillis();
    }

}
