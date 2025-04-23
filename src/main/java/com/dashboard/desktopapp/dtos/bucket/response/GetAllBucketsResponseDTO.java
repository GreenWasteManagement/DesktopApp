package com.dashboard.desktopapp.dtos.bucket.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Data
public class GetAllBucketsResponseDTO {
    private List<Bucket> buckets;

    @Getter
    @Setter
    @Data
    public static class Bucket {
        private Long id;
        private BigDecimal capacity;
        private Boolean isAssociated;
    }
}