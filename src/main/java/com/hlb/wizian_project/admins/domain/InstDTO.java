package com.hlb.wizian_project.admins.domain;

import java.time.LocalDateTime;

public interface InstDTO {
    int getInstNo();
    String getInstId();
    String getInstNm();
    String getAddr();
    LocalDateTime getInstDate();
}
