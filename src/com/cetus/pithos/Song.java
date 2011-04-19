package com.cetus.pithos;

public class Song {
    private String artist, album, songTitle;
    
    public Song() {}
    
    public Song(String artist, String album, String songTitle) {
    	this.artist = artist;
    	this.album = album;
    	this.songTitle = songTitle;
    }
    
    public String getArtist() {
    	return this.artist;
    }
    
    public String getAlbum() {
    	return this.album;
    }
	
    public String getSongTitle() {
    	return this.songTitle;
    }
}
