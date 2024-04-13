package com.example.finalproject.fragments;

public class Songs {
    private String djName;
    private String songName;
    private String songPerformer;

    public Songs(String djName, String songName, String songPerformer) {
        this.djName = djName;
        this.songName = songName;
        this.songPerformer = songPerformer;
    }

    public String getDjName() {
        return djName;
    }

    public void setDjName(String djName) {
        this.djName = djName;
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
