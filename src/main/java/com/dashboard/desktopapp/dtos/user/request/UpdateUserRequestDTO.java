package com.dashboard.desktopapp.dtos.user.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateUserRequestDTO {

    private User user;

    @Getter
    @Setter
    @Data
    public static class User {
        private Long id;
        private String name;
        private String username;
        private String password;
        private String email;
        private String phoneNumber;
        private String role;
    }

}
