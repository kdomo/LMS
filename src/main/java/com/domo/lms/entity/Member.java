package com.domo.lms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    private String userId;

    private String userName;

    private String phone;

    private String password;

    private LocalDateTime regDt;


    private boolean emailAuthYn;

    private String emailAuthKey;

    private LocalDateTime emailAuthDt;
}
