package com.domo.lms.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private Long id;
    private String categoryName;
    private int sortValue;
    private boolean usingYn;
}
