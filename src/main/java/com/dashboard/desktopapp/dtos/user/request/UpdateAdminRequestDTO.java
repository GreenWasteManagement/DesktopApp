package com.dashboard.desktopapp.dtos.user.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateAdminRequestDTO {

    private User user;
    private Address address;
    private PostalCode postalCode;
    private Admin admin;

    @Data
    @Getter
    @Setter
    public static class User {
        private Long id;
        private String name;
        private String username;
        private String email;
        private String phoneNumber;
    }

    @Data
    @Getter
    @Setter
    public static class Address {
        private Long id;
        private String floorDetails;
        private Integer floorNumber;
        private Integer doorNumber;
        private String street;
    }

    @Data
    @Getter
    @Setter
    public static class PostalCode {
        private Long id;
        private String postalCode;
        private String county;
        private String district;
    }

    @Data
    @Getter
    @Setter
    public static class Admin {
        private Long id;
        private String citizenCardCode;
    }

}