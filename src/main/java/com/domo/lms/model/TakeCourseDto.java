package com.domo.lms.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class TakeCourseDto {

    private Long id;

    private Long courseId;

    private String userId;

    private long payPrice;
    private String status;

    private LocalDateTime regDt;

    private String userName;
    private String phone;
    private String subject;

    private long totalCount;
    private long seq;

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return regDt != null ? regDt.format(formatter) : "";
    }
}
