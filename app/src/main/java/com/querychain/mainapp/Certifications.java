package com.querychain.mainapp;


public class Certifications {
    String name;
    byte[] image;
    int id;

    public Certifications() {}

    public Certifications(int keyId) {
        this.id = keyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int keyId) {
        this.id = keyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

