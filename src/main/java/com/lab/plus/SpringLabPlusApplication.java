package com.lab.plus;

import com.lab.plus.meeting.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class SpringLabPlusApplication implements CommandLineRunner {

    @Autowired
    private MeetingService meetingService;

    public static void main(String[] args) {
        SpringApplication.run(SpringLabPlusApplication.class, args);
    }


    @Override
    public void run(String... args) {
        // 创建一个会议，开始时间为明天
        LocalDateTime meetingStartTime = LocalDateTime.now().plusDays(1).plusSeconds(8);
        meetingService.createMeeting("1", "重要会议", meetingStartTime);
    }
}
