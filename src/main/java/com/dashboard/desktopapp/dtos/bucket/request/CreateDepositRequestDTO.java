package com.dashboard.desktopapp.dtos.bucket.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Data
public class CreateDepositRequestDTO {
    private Municipality municipality;
    private Container container;
    private BigDecimal depositAmount;

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

