package com.dashboard.desktopapp.dtos.user.request;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdatePostalCodeRequestDTO {

    private PostalCode postalCode;

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
