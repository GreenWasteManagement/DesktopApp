package com.dashboard.desktopapp.dtos.bucket.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BucketFullUpdateResponseDTO {
    private Long bucketId;
    private BigDecimal capacity;
    private Boolean isAssociated;
    private List<BucketMunicipalityResponse> bucketMunicipalities;


    @Data
    public static class BucketMunicipalityResponse {
        private Long id;
        private Boolean status;
        private MunicipalityResponse municipality;


    }

    @Data
    public static class MunicipalityResponse {
        private Long id;
        private String nif;
        private String citizenCardCode;
        private UserResponse user;


    }

    @Data
    public static class UserResponse {
        private Long id;
        private String name;
        private String email;


    }
}
