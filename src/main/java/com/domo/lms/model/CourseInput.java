package com.domo.lms.model;

import lombok.Data;

@Data
public class CourseInput {

    private long id;
    private long categoryId;
    private String subject;
    private String keyword;
    private String summary;
    private String contents;
    private long price;
    private long salePrice;
    private String saleEndDtText;

    private String idList;
}
