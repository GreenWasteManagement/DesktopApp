package com.dashboard.desktopapp.dtos.user.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class GetAllMunicipalitiesResponseDTO {
    private List<MunicipalityData> municipalities;

    @Getter
    @Setter
    @Data
    public static class MunicipalityData {
        private User user;
        private Municipality municipality;
        private Address address;
        private PostalCode postalCode;
    }

    @Getter
    @Setter
    @Data
    public static class User {
        private Long id;
        private String name;
        private String username;
        //private String password;
        private String email;
        private String phoneNumber;
        private String role;
    }

    @Getter
    @Setter
    @Data
    public static class Municipality {
        private Long id;
        private String citizenCardCode;
        private String nif;
    }

    @Getter
    @Setter
    @Data
    public static class Address {
        private Long id;
        private String floorDetails;
        private Integer floorNumber;
        private Integer doorNumber;
        private String street;
    }

    @Getter
    @Setter
    @Data
    public static class PostalCode {
        private Long id;
        private String postalCode;
        private String county;
        private String district;
    }
}
