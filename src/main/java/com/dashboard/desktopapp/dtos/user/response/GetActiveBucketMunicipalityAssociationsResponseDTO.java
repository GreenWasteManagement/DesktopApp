package com.dashboard.desktopapp.dtos.user.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@Data
public class GetActiveBucketMunicipalityAssociationsResponseDTO {
    private Long id;
    private Bucket bucket;
    private Municipality municipality;
    private User user;

    @Getter
    @Setter
    @Data
    public static class Municipality {

        private Long id;
        private Long userId;
        private String citizenCardCode;
        private String nif;
    }

    @Getter
    @Setter
    @Data
    public static class Bucket {

        private Long id;
        private BigDecimal capacity;
        private Boolean isAssociated;

    }

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
