package com.dashboard.desktopapp.dtos.bucket.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Data
public class CreateBucketRequestDTO {
    private Bucket bucket;

    @Getter
    @Setter
    @Data
    public static class Bucket {
        private BigDecimal capacity;
        private Boolean isAssociated;
    }
}
