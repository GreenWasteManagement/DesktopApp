package com.dashboard.desktopapp.dtos.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class MunicipalityDTO {

    private Long id;

    private Long userId;

    private String citizenCardCode;

    private String nif;

    private Set<Long> bucketMunicipalityIds;
}
