package com.dashboard.desktopapp.models;

public class SMAS {
        private final Integer id;
        private final String name;
        private final String username;
        private final String email;
        private final String phone;
        private final Integer cc;
        private final Integer nif;
        private final String userType;
        private final String floorDetails;
        private final Integer floorNumber;
        private final Integer doorNumber;
        private final String street;
        private final String postalCode;
        private final String county;
        private final String district;
        private final String workerId;
        private final String position;

    public SMAS(Integer id, String name, String username, String email, String phone, Integer cc, Integer nif, String userType, String floorDetails, Integer floorNumber, Integer doorNumber, String street, String postalCode, String county, String district, String workerId, String position) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.cc = cc;
        this.nif = nif;
        this.userType = userType;
        this.floorDetails = floorDetails;
        this.floorNumber = floorNumber;
        this.doorNumber = doorNumber;
        this.street = street;
        this.postalCode = postalCode;
        this.county = county;
        this.district = district;
        this.workerId = workerId;
        this.position = position;
    }


    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Integer getCc() { return cc; }
    public Integer getNif() { return nif; }
    public String getUserType() { return userType; }
    public String getFloorDetails() { return floorDetails; }
    public Integer getFloorNumber() { return floorNumber; }
    public Integer getDoorNumber() { return doorNumber; }
    public String getStreet() { return street; }
    public String getPostalCode() { return postalCode; }
    public String getCounty() { return county; }
    public String getDistrict() { return district; }
    public String getWorkerId() { return workerId; }
    public String getPosition() { return position; }
}
