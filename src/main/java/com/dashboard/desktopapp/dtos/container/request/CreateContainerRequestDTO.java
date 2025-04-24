package com.dashboard.desktopapp.dtos.container.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class CreateContainerRequestDTO {
    private BigDecimal capacity;
    private BigDecimal currentVolumeLevel;
    private String localization;
}
