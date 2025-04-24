package com.dashboard.desktopapp.dtos.user.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class GetAllSmasResponseDTO {
    private List<SmasData> smasList;

    @Getter
    @Setter
    @Data
    public static class SmasData {
        private User user;
        private Smas smas;
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
    public static class Smas {
        private Long id;
        private String position;
        private String employeeCode;
        private String citizenCardCode;
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

