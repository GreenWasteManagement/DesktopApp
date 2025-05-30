package com.dashboard.desktopapp.dtos.user.response;

import com.dashboard.desktopapp.dtos.user.request.CreateAdminRequestDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateAdminResponseDTO {
    private CreateAdminRequestDTO.Admin admin;

    @Getter
    @Setter
    @Data
    public static class Admin {
        private String citizenCardCode;
    }
}
