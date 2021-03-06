/**
 * @file    MusicFiles.java
 *
 * @brief   to get music file details
 *
 * @author  Mayukh P V
 */

package com.example.mediaservice;

public class MusicFiles {
    public boolean getAlbum;
    private String path, title, artist,album, duration;

    public MusicFiles(String path, String title, String artist, String album, String duration) {
        this.path = path;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }
    public MusicFiles() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }


    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {return duration; }
}
