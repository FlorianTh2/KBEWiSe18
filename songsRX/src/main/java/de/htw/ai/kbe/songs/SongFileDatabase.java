package de.htw.ai.kbe.songs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import de.htw.ai.kbe.db.Entry;

public class SongFileDatabase extends SongDatabase
{
	public static String defaultPath;
	
	public SongFileDatabase()
	{
		super();
		if(defaultPath != null)
			loadFromFile(defaultPath);
	}
	
	public static boolean defaultPathAvailable()
	{
		File file = new File(SongFileDatabase.defaultPath);
		return file.exists();
	}
	
	public void loadFromFile(String path)
	{
		File file = new File(path);
		
		if(!file.exists() || file.isDirectory())
			return;
		
		try
		{
			String json = new String(Files.readAllBytes(Paths.get(path)));
			
			if(json.isEmpty())
				return;
			
			Entry<Song> base = new SongEntry();
			List<Entry<Song>> entries = base.entriesFromJson(json);
			
			if(entries == null)
				return;
			
			for(Entry<Song> entry : entries)
				super.insert(entry);
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public void saveToFile(String path)
	{
		if(path != null) 
		{
			try(PrintWriter writer = new PrintWriter(path))
			{
				writer.print(super.toJson());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
