package com.dashboard.desktopapp.dtos.base;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class PostalCodeDTO {

    private Long id;

    private String postalCode;

    private String county;

    private String district;

    private Set<Long> addressIds;
}
