package com.dashboard.desktopapp.dtos.bucket.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class CreateBucketResponseDTO {
    private Bucket bucket;

    @Getter
    @Setter
    @Data
    public static class Bucket {
        private Long id;
        private BigDecimal capacity;
        private Boolean isAssociated;
    }
}

