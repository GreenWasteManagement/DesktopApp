package com.dashboard.desktopapp.dtos.base;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BucketDTO {

    private Long id;

    private BigDecimal capacity;

    private Boolean isAssociated;

}
