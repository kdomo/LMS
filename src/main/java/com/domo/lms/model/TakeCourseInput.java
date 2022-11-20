package com.domo.lms.model;

import lombok.Data;

@Data
public class TakeCourseInput{
    private String userId;
    private long courseId;
}
