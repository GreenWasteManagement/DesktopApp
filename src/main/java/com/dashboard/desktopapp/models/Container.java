package com.dashboard.desktopapp.models;

public class Container {
    private final Integer id;
    private final Float capacity;
    private final String location;
    private final Float currentVolume;

    public Container(Integer id, Float capacity, String location, Float currentVolume) {
        this.id = id;
        this.capacity = capacity;
        this.location = location;
        this.currentVolume = currentVolume;
    }

    public Integer getId() { return id; }
    public Float getCapacity() { return capacity; }
    public String getLocation() { return location; }
    public Float getCurrentVolume() { return currentVolume; }
}
