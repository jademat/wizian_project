package com.hlb.wizian_project.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyLectDTO {

    private int lectNo;

    private String lectNm;

    private String instNm;

    private String lectDesc;

    private String lectSchd;

    private LocalDateTime lectStart;

    private LocalDateTime lectSubmit;

    private int lectPers;

    private String lectLoc;

    private String lectStatus;

    private int studtLimit;

    private LocalDateTime applyDate;

    private int lectPrice;
    private boolean applied;


}