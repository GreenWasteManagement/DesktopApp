package com.dashboard.desktopapp.dtos.user.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateAdminRequestDTO {

    private User user;
    private Admin admin;
    private Address address;
    private PostalCode postalCode;

    @Getter
    @Setter
    @Data
    public static class User {
        private String name;
        private String username;
        private String password;
        private String email;
        private String phoneNumber;
        private String role; // ADMIN, MUNICIPALITY, SMAS
    }

    @Getter
    @Setter
    @Data
    public static class Admin {
        private String citizenCardCode;
    }

    @Getter
    @Setter
    @Data
    public static class Address {
        private String floorDetails;
        private Integer floorNumber;
        private Integer doorNumber;
        private String street;
    }

    @Getter
    @Setter
    @Data
    public static class PostalCode {
        private String postalCode;
        private String county;
        private String district;
    }
}

