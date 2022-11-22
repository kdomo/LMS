package com.domo.lms.model;

import com.domo.lms.entity.Banner;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerDto {
    private Long id;
    private String bannerName;

    private String bannerImgUrl;

    private String link;

    private LocalDateTime regDt;

    private boolean openStatus;

    private int sortValue;

    long totalCount;
    long seq;

    public static BannerDto of(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .bannerImgUrl(banner.getBannerImgUrl())
                .link(banner.getLink())
                .regDt(banner.getRegDt())
                .openStatus(banner.isOpenStatus())
                .sortValue(banner.getSortValue())
                .build();
    }

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return regDt != null ? regDt.format(formatter) : "";
    }
}
