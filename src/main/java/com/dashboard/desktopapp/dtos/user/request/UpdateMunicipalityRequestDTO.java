package com.dashboard.desktopapp.dtos.user.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateMunicipalityRequestDTO {

    private Municipality municipality;

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
