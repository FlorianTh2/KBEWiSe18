package de.htw.ai.kbe.songs;

import de.htw.ai.kbe.db.Entry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class SongEntry extends Entry<Song>
{
	public SongEntry()
	{
		super();
	}
	
	public SongEntry(Song value)
	{
		super(value);
	}
	
	public SongEntry(int id, Song value)
	{
		super(id, value);
	}
	

	@Override
	public String toJson()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			return mapper.writeValueAsString(super.retrieve());
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
			Song value = mapper.readValue(json, Song.class);
			super.store(value);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String entriesToJson(List<Entry<Song>> entries)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		List<Song> list = new ArrayList<>();
		
		for(Entry<Song> entry : entries)
			list.add(entry.retrieve());
		
		try
		{
			return mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Entry<Song>> entriesFromJson(String json)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			List<Song> list = mapper.readValue(json, new TypeReference<List<Song>>() {});
			List<Entry<Song>> entries = new ArrayList<>();
			
			for(Song value : list)
				entries.add(new SongEntry(value.getId(), value));
			
			return entries;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toXml()
	{
		ObjectMapper mapper = new XmlMapper();
		
		try
		{
			return mapper.writeValueAsString(super.retrieve());
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
			Song value = mapper.readValue(xml, Song.class);
			super.store(value);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String entriesToXml(List<Entry<Song>> entries)
	{
		ObjectMapper mapper = new XmlMapper();
		
		List<Song> list = new ArrayList<>();
		
		for(Entry<Song> entry : entries)
			list.add(entry.retrieve());
		
		try
		{
			return mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Entry<Song>> entriesFromXml(String xml)
	{
		ObjectMapper mapper = new XmlMapper();
		
		try
		{
			List<Song> list = mapper.readValue(xml, new TypeReference<List<Song>>() {});
			List<Entry<Song>> entries = new ArrayList<>();
			
			for(Song value : list)
				entries.add(new SongEntry(value.getId(), value));
			
			return entries;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
