package com.dashboard.desktopapp.dtos.bucket.request;

import lombok.*;

@Getter
@Setter
@Data
public class GetBucketMunicipalitiesByUserIdAndStatusRequestDTO {
    private Long userId;
    private Boolean status;
}
