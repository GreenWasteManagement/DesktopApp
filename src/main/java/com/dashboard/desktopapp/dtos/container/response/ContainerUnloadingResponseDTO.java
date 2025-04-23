package com.dashboard.desktopapp.dtos.container.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Data
public class ContainerUnloadingResponseDTO {
    private BigDecimal unloadedQuantity;
    private Instant unloadingTimestamp;
    private Smas smas;
    private Container container;

    @Getter
    @Setter
    @Data
    public static class Smas {
        private Long id;
        private String position;
        private String employeeCode;
        private String citizenCardCode;
        private Set<Long> containerUnloadingIds;
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
