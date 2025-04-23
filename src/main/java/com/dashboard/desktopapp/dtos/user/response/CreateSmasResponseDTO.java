package com.dashboard.desktopapp.dtos.user.response;

import lombok.*;

@Getter
@Setter
@Data
public class CreateSmasResponseDTO {
    private Smas smas;

    @Getter
    @Setter
    @Data
    public static class Smas {
        private Long id;
        private String position;
        private String employeeCode;
        private String citizenCardCode;
    }
}


