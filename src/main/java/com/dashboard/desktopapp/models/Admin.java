package com.dashboard.desktopapp.models;

public class Admin {
    private Integer id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private Double cc;
    private Double nif;
    private String userType;
    private String address;

    public Admin(Integer id, String name, String username, String email, String phone,
                 Double cc, Double nif, String userType, String address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.cc = cc;
        this.nif = nif;
        this.userType = userType;
        this.address = address;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public Double getCc() { return cc; }
    public Double getNif() { return nif; }
    public String getUserType() { return userType; }
    public String getAddress() { return address; }
}

