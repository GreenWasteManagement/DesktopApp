package com.dashboard.desktopapp.dtos.user.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Data
public class CreateMunicipalityRequestDTO {

    private User user;
    private Municipality municipality;
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
        private String role;
    }

    @Getter
    @Setter
    @Data
    public static class Municipality {
        private String citizenCardCode;
        private String nif;
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
