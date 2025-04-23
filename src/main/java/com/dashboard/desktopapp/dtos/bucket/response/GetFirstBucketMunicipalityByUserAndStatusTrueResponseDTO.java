package com.dashboard.desktopapp.dtos.bucket.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Data
public class GetFirstBucketMunicipalityByUserAndStatusTrueResponseDTO {
    private BucketMunicipality bucketMunicipality;

    @Getter
    @Setter
    @Data
    public static class BucketMunicipality {
        private Long id;
        private Long userId;
        private Long bucketId;
        private Instant timestampOfAssociation;
        private Boolean status;
    }
}
