package com.dashboard.desktopapp.models;

public class Container {
    private final Integer id;
    private final Float capacity;
    private final String containerLocation;
    private final Float currentVolume;

    public Container(Integer id, Float capacity, String containerLocation, Float currentVolume) {
        this.id = id;
        this.capacity = capacity;
        this.containerLocation = containerLocation;
        this.currentVolume = currentVolume;
    }

    public Integer getId() {
        return id;
    }

    public Float getCapacity() {
        return capacity;
    }

    public String getLocation() {
        return containerLocation;
    }

    public Float getCurrentVolume() {
        return currentVolume;
    }
}
