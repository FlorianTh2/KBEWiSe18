package de.htw.ai.kbe.echo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Song
{

	private String title;
	private String artist;
	private String album;
	private Integer released;
	private Integer id;

	
	public static Song fromJson(String json)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			Song song = mapper.readValue(json, Song.class);
			return song;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Song()
	{
		super();
		this.title = null;
		this.artist = null;
		this.album = null;
		this.released = null;
		this.id = null;
	}
	
	public Song(String title, String artist, String album, Integer released, Integer id) {
		super();
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.released = released;
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public String getArtist() {
		return artist;
	}


	public String getAlbum() {
		return album;
	}


	public Integer getReleased() {
		return released;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public boolean valid()
	{
		return this.title != null &&
				this.artist != null &&
				this.album != null &&
				this.released != null;
	}

	public String toJson() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}