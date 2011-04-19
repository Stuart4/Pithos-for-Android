package com.cetus.pithos;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SongList extends ArrayAdapter<Song> {
    
	private ArrayList<Song> songs;
	private Context context;
	
	public SongList(Context context, int textViewResourceId, ArrayList<Song> songs) {
    	super(context, textViewResourceId, songs);
    	this.context = context;
    	this.songs = songs;
    }
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.song_item, null);
		}

		Song song = songs.get(position);
		if (song != null) {
			
			TextView songLabel = (TextView) v.findViewById(R.id.songTitle);
			TextView artistAlbumLabel = (TextView) v.findViewById(R.id.artistAlbum);
			
			if (songLabel != null && artistAlbumLabel != null) {
				songLabel.setText(song.getSongTitle());
				artistAlbumLabel.setText(song.getArtistAlbum());
			}
			
			if (song.played()) {
				songLabel.setTextColor(Color.GRAY);
			}
		}
		return v;
	}
    
    
}
