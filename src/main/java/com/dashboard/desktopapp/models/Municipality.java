package com.dashboard.desktopapp.models;

import java.util.List;

public class Municipality {
        private final Integer id;
        private final String name;
        private final String username;
        private final String email;
        private final String phone;
        private final Double cc;
        private final Double nif;
        private final String userType;
        private final String  address;
        private final List<Bucket> bucketList;

    public Municipality(Integer id, String name, String username, String email, String phone, Double cc, Double nif, String userType, String address, List<Bucket> bucketList) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.cc = cc;
        this.nif = nif;
        this.userType = userType;
        this.address = address;
        this.bucketList = bucketList;
    }


    public Integer getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public Double getCc() {
        return cc;
    }
    public Double getNif() {
        return nif;
    }
    public String getUserType() {
        return userType;
    }
    public String getAddress() {
        return address;
    }
    public List<Bucket> getBucketList() {
        return bucketList;
    }
}
