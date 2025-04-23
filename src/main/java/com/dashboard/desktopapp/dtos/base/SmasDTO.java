package com.dashboard.desktopapp.dtos.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SmasDTO {

    private Long id;

    private String position;

    private String employeeCode;

    private String citizenCardCode;

    private Set<Long> containerUnloadingIds;
}
