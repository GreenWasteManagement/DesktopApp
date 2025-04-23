package com.dashboard.desktopapp.dtos.base;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
public class BucketMunicipalityContainerDTO {

    private Long id;

    private Long associationId;

    private Long containerId;

    private Instant depositTimestamp;

    private BigDecimal depositAmount;
}
