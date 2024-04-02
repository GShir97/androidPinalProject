package com.example.finalproject.fragments;

public class Dj {
    private String name;
    private String song;
    private String club;
    private String address;

    public Dj() {

    }

    public Dj(String name, String song, String club, String address) {
        this.name = name;
        this.song = song;
        this.club = club;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
