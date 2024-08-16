package com.javafest.treeify.model;

public class Plant {

    private String type;
    private String stage;

    // Default constructor
    public Plant() {
    }

    // Parameterized constructor
    public Plant(String type, String stage) {
        this.type = type;
        this.stage = stage;
    }

    // Getters and Setters for all fields

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "type='" + type + '\'' +
                ", stage='" + stage + '\'' +
                '}';
    }
}
