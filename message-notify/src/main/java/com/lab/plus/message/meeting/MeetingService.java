package com.lab.plus.message.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MeetingService {

    @Autowired
    private MeetingProducer meetingProducer;

    public void createMeeting(String id, String title, LocalDateTime startTime) {
        Meeting meeting = new Meeting(id, title, startTime);
        meetingProducer.sendMeetingNotification(meeting);
    }
}
