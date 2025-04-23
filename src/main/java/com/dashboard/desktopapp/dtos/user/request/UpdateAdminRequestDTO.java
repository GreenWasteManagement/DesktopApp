package com.dashboard.desktopapp.dtos.user.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateAdminRequestDTO {

    private Admin admin;

    @Getter
    @Setter
    @Data
    public static class Admin {
        private Long id;
        private String citizenCardCode;
        private Long userId;
    }
}
