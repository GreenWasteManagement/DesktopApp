package com.dashboard.desktopapp.dtos.user.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoginResponseDTO {
    private String token;
}
