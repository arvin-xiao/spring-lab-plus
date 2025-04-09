package com.lab.plus.message;

import com.lab.plus.message.meeting.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class MessageNotifyApplication implements CommandLineRunner {

    @Autowired
    private MeetingService meetingService;

    public static void main(String[] args) {
        SpringApplication.run(MessageNotifyApplication.class, args);
    }


    @Override
    public void run(String... args) {
        // 创建一个会议，开始时间为明天
        LocalDateTime meetingStartTime = LocalDateTime.now().plusSeconds(60);
        meetingService.createMeeting("1", "重要会议", meetingStartTime);
    }
}
