package com.domo.lms.model;

import com.domo.lms.entity.MemberLog;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberLogDto {
    private String userId;
    private LocalDateTime accessDate;
    private String ip;
    private String agent;

    public static List<MemberLogDto> of(List<MemberLog> list) {
        List<MemberLogDto> result = new ArrayList<>();
        for (MemberLog x : list) {
            result.add(MemberLogDto.builder()
                    .userId(x.getUserId())
                    .ip(x.getIp())
                    .agent(x.getAgent())
                    .accessDate(x.getAccessDate())
                    .build());
        }
        return result;
    }

    public String getAccessDateText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return accessDate != null ? accessDate.format(formatter) : "";
    }
}
