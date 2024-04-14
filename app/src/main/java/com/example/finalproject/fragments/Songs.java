package com.example.finalproject.fragments;

public class Songs {
    private String songName;
    private String songPerformer;

    public Songs() {
    }

    public Songs(String songName, String songPerformer) {
        this.songName = songName;
        this.songPerformer = songPerformer;
    }


    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongPerformer() {
        return songPerformer;
    }

    public void setSongPerformer(String songPerformer) {
        this.songPerformer = songPerformer;
    }
}
