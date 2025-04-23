package com.dashboard.desktopapp.dtos.user.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateSmasRequestDTO {

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

