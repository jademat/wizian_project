package com.hlb.wizian_project.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseInstDTO {

    private int courNo;
    private String courNm;
    private String courYear;
    private String courDept;
    private String courWeek;
}
