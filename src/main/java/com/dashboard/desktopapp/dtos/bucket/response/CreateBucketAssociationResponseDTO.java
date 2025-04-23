package com.dashboard.desktopapp.dtos.bucket.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Data
public class CreateBucketAssociationResponseDTO {
    private Long id;
    private Bucket bucket;
    private Municipality municipality;
    private Instant timestampOfAssociation;
    private Boolean status;

    @Getter
    @Setter
    @Data
    public static class Bucket {
        private Long id;
        private BigDecimal capacity;
        private Boolean isAssociated;
    }

    @Getter
    @Setter
    @Data
    public static class Municipality {
        private Long id;
        private Long userId;
        private String citizenCardCode;
        private String nif;
    }
}

