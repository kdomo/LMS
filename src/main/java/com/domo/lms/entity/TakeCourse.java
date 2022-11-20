package com.domo.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TakeCourse {
    public static String STATUS_REQ = "REQ";
    public static String STATUS_COMPLETE = "COMPLETE";
    public static String STATUS_CANCEL = "CANCEL";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long courseId;

    private String userId;

    private long payPrice;
    private String status; //(수강신청, 결제완료, 수강취소)

    private LocalDateTime regDt;

}
