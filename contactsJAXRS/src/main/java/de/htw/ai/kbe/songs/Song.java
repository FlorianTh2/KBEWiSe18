package de.htw.ai.kbe.songs;

public class Song
{
	public String title;
	public String artist;
	public String album;
	public Integer released;
	public Integer id;
	
	public Song()
	{
		super();
		this.title = null;
		this.artist = null;
		this.album = null;
		this.released = null;
		this.id = null;
	}
	
	public Song(String title, String artist, String album, Integer released, Integer id)
	{
		super();
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.released = released;
		this.id = id;
	}
}
