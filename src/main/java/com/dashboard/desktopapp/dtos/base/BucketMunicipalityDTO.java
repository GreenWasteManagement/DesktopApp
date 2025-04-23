package com.dashboard.desktopapp.dtos.base;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class BucketMunicipalityDTO {

    private Long id;

    private Long userId;

    private Long bucketId;

    private Instant timestampOfAssociation;

    private Boolean status;
}
