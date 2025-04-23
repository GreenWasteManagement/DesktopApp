package com.dashboard.desktopapp.dtos.user.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateAddressRequestDTO {

    private Address address;

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
}
