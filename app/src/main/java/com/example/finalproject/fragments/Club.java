package com.example.finalproject.fragments;

public class Club {
    private String name;
    private String DJ;
    private String song;
    private String open;
    private String address;
    private String age;
    private String djId;

    public Club() {
        // Default constructor required for Firebase
    }


    public Club(String name, String DJ, String song, String open, String address, String age, String djId) {
        this.name = name;
        this.DJ = DJ;
        this.song = song;
        this.open = open;
        this.address = address;
        this.age = age;
        this.djId = djId;
    }

    // Getters and setters for the fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDJ() {
        return DJ;
    }

    public void setDJ(String DJ) {
        this.DJ = DJ;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDjId() {
        return djId;
    }

    public void setDjId(String djId) {
        this.djId = djId;
    }
}
