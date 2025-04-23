package com.dashboard.desktopapp.dtos.base;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;

    private String name;

    private String username;

    private String password;

    private String email;

    private String phoneNumber;

    private AddressDTO address;

    private AdminDTO admin;

    private MunicipalityDTO municipality;

    private SmasDTO smas;

    private String role;
}
