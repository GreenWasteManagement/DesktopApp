package com.dashboard.desktopapp.dtos.bucket.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class CreateDepositByIdsRequestDTO {
    private Long municipalityId;
    private Long containerId;
    private BigDecimal depositAmount;
}
