package com.dashboard.desktopapp.dtos.bucket.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateBucketAssociationRequestDTO {
    private Long bucketId;
    private Long municipalityId;
}
