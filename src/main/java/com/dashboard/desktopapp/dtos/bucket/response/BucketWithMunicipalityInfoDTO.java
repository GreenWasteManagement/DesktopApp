package com.dashboard.desktopapp.dtos.bucket.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data // ou @Getter e @Setter
public class BucketWithMunicipalityInfoDTO {

    private Long bucketId;
    private BigDecimal capacity;
    private Boolean isAssociated;
    private List<BucketMunicipalityDTO> bucketMunicipalities;

    @Data
    public static class BucketMunicipalityDTO {
        private Long id;
        private Boolean status;
        private MunicipalityDTO municipality;
    }

    @Data
    public static class MunicipalityDTO {
        private Long id;
        private String nif;
        private String citizenCardCode;
        private UserDTO user;
    }

    @Data
    public static class UserDTO {
        private Long id;
        private String name;
        private String email;
    }
}
