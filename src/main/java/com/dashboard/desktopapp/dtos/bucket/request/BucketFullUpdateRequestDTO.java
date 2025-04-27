package com.dashboard.desktopapp.dtos.bucket.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BucketFullUpdateRequestDTO {
    @NotNull
    private Long bucketId;

    private BigDecimal capacity;
    private Boolean isAssociated;
    private List<BucketMunicipalityUpdate> bucketMunicipalities;


    @Data
    public static class BucketMunicipalityUpdate {
        @NotNull
        private Long id;
        private Boolean status;
        private MunicipalityDTO municipality;

    }

    @Data
    public static class MunicipalityDTO {
        @NotNull
        private Long id;
        private String nif;
        private String citizenCardCode;
        private UserDTO user;


        @Data
        public static class UserDTO {
            @NotNull
            private Long id;
            private String name;
            private String email;


        }
    }
}
