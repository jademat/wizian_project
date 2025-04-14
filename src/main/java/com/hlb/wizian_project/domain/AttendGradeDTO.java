package com.hlb.wizian_project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendGradeDTO {

    private int stdntNo;
    private String stdntNm;
    private int fullTime;
    private int attendTime;
    private int lateTime;
    private int absTime;
}
