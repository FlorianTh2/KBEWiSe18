package de.htw.ai.kbe.songs;

import de.htw.ai.kbe.db.Entry;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class SongEntry extends Entry<Song>
{
	public SongEntry(Song value)
	{
		super(value);
	}

	@Override
	public String toJson()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			return mapper.writeValueAsString(super.getId());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean fromJson(String json)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			Song s = mapper.readValue(json, Song.class);
			super.store(s);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String entriesToJson(List<Entry<Song>> entries)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Entry<Song>> entriesFromJson(String json)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toXml()
	{
		ObjectMapper mapper = new XmlMapper();
		
		try
		{
			return mapper.writeValueAsString(super.getId());
		} catch (JsonProcessingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean fromXml(String xml)
	{
		ObjectMapper mapper = new XmlMapper();
		
		try
		{
			Song s = mapper.readValue(xml, Song.class);
			super.store(s);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String entriesToXml(List<Entry<Song>> entries)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Entry<Song>> entriesFromXml(String xml)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
