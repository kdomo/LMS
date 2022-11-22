package com.domo.lms.model;

import com.domo.lms.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private Long id;
    private Long categoryId;
    private String imagePath;
    private String keyword;
    private String subject;
    private String summary;
    private String contents;
    private long price;
    private long salePrice;
    private LocalDate saleEndDt;
    private LocalDateTime regDt;
    private LocalDateTime upDt;

    private String fileUrl;

    long totalCount;
    long seq;

    public static CourseDto of(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .categoryId(course.getCategoryId())
                .imagePath(course.getImagePath())
                .keyword(course.getKeyword())
                .subject(course.getSubject())
                .summary(course.getSummary())
                .contents(course.getContents())
                .price(course.getPrice())
                .salePrice(course.getSalePrice())
                .saleEndDt(course.getSaleEndDt())
                .regDt(course.getRegDt())
                .upDt(course.getUpDt())
                .fileUrl(course.getFileUrl())
                .build();

    }

    public static List<CourseDto> of(List<Course> courseList) {
        if (courseList == null) {
            new ArrayList<>();
        }

        List<CourseDto> courseDtoList = new ArrayList<>();
        for (Course x : courseList) {
            courseDtoList.add(CourseDto.of(x));
        }
        return courseDtoList;
    }

}
