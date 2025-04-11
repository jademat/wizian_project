package com.hlb.wizian_project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudntAttendListDTO {

    private int stdntNo;
    private String stdntId;
    private String stdntNm;
    private String stdntEmail;
    private String phone;
    private LocalDateTime attendDate;
    private int attendStatus;
}
