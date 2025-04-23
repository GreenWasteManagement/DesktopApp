package com.dashboard.desktopapp.dtos.bucket.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Data
public class GetAllBucketMunicipalitiesResponseDTO {
    private List<BucketMunicipality> bucketMunicipalities;

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
