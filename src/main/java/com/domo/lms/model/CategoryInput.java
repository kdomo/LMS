package com.domo.lms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInput {
    long id;
    String categoryName;
    int sortValue;
    boolean usingYn;

}
