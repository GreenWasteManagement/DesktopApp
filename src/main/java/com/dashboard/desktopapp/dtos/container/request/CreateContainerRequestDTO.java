package com.dashboard.desktopapp.dtos.container.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class CreateContainerRequestDTO {
    private BigDecimal capacity;
    private BigDecimal currentVolumeLevel;
    private String localization;
}
