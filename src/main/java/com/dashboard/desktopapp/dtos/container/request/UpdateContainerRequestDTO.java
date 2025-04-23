package com.dashboard.desktopapp.dtos.container.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class UpdateContainerRequestDTO {
    private Container container;

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

