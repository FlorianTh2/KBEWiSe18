package de.htw.ai.kbe.echo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SongDatabase
{
	private ConcurrentHashMap<Integer, Song> data;
	
	
	public static SongDatabase fromJson(String json)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			List<Song> list = mapper.readValue(json, new TypeReference<List<Song>>() {});
			SongDatabase db = new SongDatabase(list);
			
			return db;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static SongDatabase loadFromFile(String path)
	{
		File file = new File(path);
		
		if(!file.exists() || file.isDirectory())
			return new SongDatabase();
		
		try
		{
			String json = new String(Files.readAllBytes(Paths.get(path)));
			
			if(json.isEmpty())
				return new SongDatabase();
			else
				return SongDatabase.fromJson(json);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SongDatabase()
	{
		super();
		this.data = new ConcurrentHashMap<>();
	}
	
	public SongDatabase(ConcurrentHashMap<Integer, Song> data)
	{
		super();
		this.data = data;
	}
	
	public SongDatabase(List<Song> list)
	{
		super();
		this.data = new ConcurrentHashMap<>();
		
		for(Song entry : list)
			data.put(entry.getId(), entry);
	}
	
	public void add(Song item)
	{
		if(item == null)
			return;
		
		int id = 0;
		
		while(data.keySet().contains(Integer.valueOf(id)))
			++id;
		
		item.setId(Integer.valueOf(id));
		this.data.put(item.getId(), item);
	}
	
	public void add(Song... items)
	{
		for(Song i : items)
			add(i);
	}
	
	public Song get(int id)
	{
		return this.data.get(Integer.valueOf(id));
	}
	
	public List<Song> get(int... ids)
	{
		List<Song> multi = new ArrayList<>();
				
		for(int i = 0; i < ids.length; ++i)
		{
			Song s = get(ids[i]);
			if(s != null)
				multi.add(s);
		}
		
		return multi;
	}
	
	public Song remove(int id)
	{
		return this.data.remove(Integer.valueOf(id));
	}
	
	public List<Song> remove(int... ids)
	{
		List<Song> multi = new ArrayList<>();
				
		for(int i = 0; i < ids.length; ++i)
		{
			Song s = remove(ids[i]);
			if(s != null)
				multi.add(s);
		}
			
		return multi;
	}
	
	public void delete(Song item)
	{
		this.data.remove(idOf(item));
	}
	
	public void delete(Song... items)
	{
		for(Song i : items)
			delete(i);
	}
	
	public int size()
	{
		return this.data.size();
	}
	
	public int idOf(Song item)
	{
		for(Map.Entry<Integer, Song> entry : this.data.entrySet())
			if(entry.getValue() == item)
				return entry.getKey().intValue();
		
		return -1;
	}
	
	public String queryToJson(int... ids)
	{
		if(ids.length == 1)
		{
			Song s = get(ids[0]);
			if(s != null)
				return s.toJson();
			
			return null;
		}
		
		List<Song> query = get(ids);
		
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			return mapper.writeValueAsString(query);
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	public String toJson()
	{
		ObjectMapper mapper = new ObjectMapper();
		
		List<Song> list = new ArrayList<>();
		
		for(Song entry : data.values())
			list.add(entry);
		
		try
		{
			return mapper.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void saveToFile(String path)
	{
		if(path != null) 
		{
			try(PrintWriter writer = new PrintWriter(path))
			{
				writer.print(toJson());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toString()
	{	
		return "SongDatabase{data=" + this.data.toString() + "(" + this.data.size() + ")" + "}";
	}
}

