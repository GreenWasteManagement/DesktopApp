package com.dashboard.desktopapp.models;

public class Bucket {
    private final Integer id;
    private final Float capacity;
    private final Boolean associated;
    private final String state;
    private final String municipality;

    public Bucket(Integer id, Float capacity, Boolean associated, String state, String municipality) {
        this.id = id;
        this.capacity = capacity;
        this.associated = associated;
        this.state = state;
        this.municipality = municipality;
    }

    public Integer getId() { return id; }
    public Float getCapacity() { return capacity; }
    public Boolean getAssociated() { return associated; }
    public String getState() { return state; }
    public String getMunicipality() { return municipality; }
}
