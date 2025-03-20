package com.lab.plus.meeting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {

    private String id;
    private String title;
    private LocalDateTime startTime;

}
