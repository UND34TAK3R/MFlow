package com.mdev.mflow.model;

public class Tag {
    //fields
    private final String tagId;
    private String name;
    private String userId;
    private String color;

    //setters
    public void setName(String newName) {
        this.name = newName;
    }
    public void setUserId(String newUserId) {
        this.userId = newUserId;
    }
    public void setColor(String newColor) { this.color = newColor; }

    //getters
    public String getTagId() {
        return tagId;
    }
    public String getName() {
        return name;
    }
    public String getColor() { return color; }
    public String getUserId() {
        return userId;
    }

    //constructor
    public Tag(String tagId, String name, String color, String userId) {
        this.tagId = tagId;
        setName(name);
        setColor(color);
        setUserId(userId);
    }
}
