package com.dashboard.desktopapp.dtos.bucket.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Data
public class GetMunicipalityDepositsResponseDTO {
    private Long depositId;
    private BigDecimal depositAmount;
    private Instant depositTimestamp;

    private Bucket bucket;
    private Container container;
    private Municipality municipality;

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

        private Set<Long> bucketMunicipalityIds;
    }

    @Getter
    @Setter
    @Data
    public static class Container {
        private Long id;

        private BigDecimal capacity;

        private String localization;

        private BigDecimal currentVolumeLevel;
    }


}
