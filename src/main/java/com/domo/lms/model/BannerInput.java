package com.domo.lms.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BannerInput {
    private Long id;
    private String bannerName;
    private String fileImgUrl;
    private String link;
    private int sortValue;
    private Boolean openStatus;
}
