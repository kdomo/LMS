package com.domo.lms.model;

import lombok.Data;

@Data
public class TakeCourseParam extends CommonParam {
    private long id;
    private String status;
    private String userId;
}
