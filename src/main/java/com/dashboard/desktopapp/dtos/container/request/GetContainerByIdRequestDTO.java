package com.dashboard.desktopapp.dtos.container.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class GetContainerByIdRequestDTO {
    private Long id;
}
