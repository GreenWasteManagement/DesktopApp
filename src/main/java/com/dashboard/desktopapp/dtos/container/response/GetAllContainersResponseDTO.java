package com.dashboard.desktopapp.dtos.container.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Data
public class GetAllContainersResponseDTO {

    private List<Container> containers;

    @Getter
    @Setter
    @Data
    public static class Container {
        private Long id;
        private BigDecimal capacity;
        private String localization;
        private BigDecimal currentVolumeLevel;

        private List<BucketMunicipalityContainer> bucketMunicipalityContainers;
        private List<ContainerUnloading> containerUnloadings;
    }

    @Getter
    @Setter
    @Data
    public static class BucketMunicipalityContainer {
        private Long id;
        private Long associationId;
        private Long containerId;
        private Instant depositTimestamp;
        private BigDecimal depositAmount;
    }

    @Getter
    @Setter
    @Data
    public static class ContainerUnloading {
        private Long id;
        private Long containerId;
        private Long userId;
        private BigDecimal unloadedQuantity;
        private Instant unloadingTimestamp;
    }
}
