package com.dashboard.desktopapp.models;

public class Container {
    private final Integer id;
    private final Float capacity;
    private final String location;

    public Container(Integer id, Float capacity, String location) {
        this.id = id;
        this.capacity = capacity;
        this.location = location;
    }

    public Integer getId() { return id; }
    public Float getCapacity() { return capacity; }
    public String getLocation() { return location; }
}
