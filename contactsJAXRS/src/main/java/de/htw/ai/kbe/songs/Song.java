package de.htw.ai.kbe.songs;

import javax.xml.bind.annotation.XmlRootElement;

import de.htw.ai.kbe.db.IValue;

@XmlRootElement(name = "song")
public class Song implements IValue
{
	private String title;
	private String artist;
	private String album;
	private Integer released;
	private Integer id;
	
	public Song()
	{
		super();
		this.title = null;
		this.artist = null;
		this.album = null;
		this.released = null;
		this.id = null;
	}
	
	public Song(String title, String artist, String album, Integer released)
	{
		super();
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.released = released;
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

	@Override
	public int getId()
	{
		if(this.id == null)
			return -1;
		return this.id.intValue();
	}

	@Override
	public void setId(int id)
	{
		this.id = Integer.valueOf(id);
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
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

	public Integer getReleased() {
		return released;
	}

	public void setReleased(Integer released) {
		this.released = released;
	}
}
