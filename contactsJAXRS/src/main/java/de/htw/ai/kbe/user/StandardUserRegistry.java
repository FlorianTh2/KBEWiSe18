package de.htw.ai.kbe.user;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


import de.htw.ai.kbe.db.IUserRegistry;

public class StandardUserRegistry implements IUserRegistry<StandardUser>
{
	public static String defaultPath;
	
	private ConcurrentHashMap<Integer, StandardUser> data;
	private ConcurrentHashMap<String, StandardUser> auth;
	private ConcurrentHashMap<String, Integer> userIds;
	
	public StandardUserRegistry()
	{
		this.data = new ConcurrentHashMap<>();
		this.auth = new ConcurrentHashMap<>();
		this.userIds = new ConcurrentHashMap<>(); 
		load();
	}
	
	@Override
	public void add(StandardUser user)
	{
		if(this.data.contains(user))
			return;
		
		if(!user.hasId() || data.containsKey(user.getId()))
		{
			int id = 0;
			
			while(this.data.keySet().contains(Integer.valueOf(id)))
				++id;
			
			user.setId(id);
		}
		
		this.data.put(Integer.valueOf(user.getId()), user);
		this.userIds.put(user.getUserId(), Integer.valueOf(user.getId()));
	}

	@Override
	public StandardUser get(int id)
	{
		return this.data.get(Integer.valueOf(id));
	}

	@Override
	public StandardUser remove(int id)
	{
		this.userIds.remove(get(id).getUserId());
		return this.data.remove(Integer.valueOf(id));
	}

	@Override
	public void delete(int id)
	{
		remove(id);
	}

	@Override
	public List<StandardUser> users()
	{
		List<StandardUser> list = new ArrayList<StandardUser>();
		
		for(StandardUser user : this.data.values())
			list.add(user);
		
		return list;
	}
	
	@Override
	public String authorize(int id)
	{
		StandardUser user = get(id);
		
		if(user == null)
			return null;
		
		for(String key : this.auth.keySet())
			if(this.auth.get(key).getId() == id)
				return key;
		
		String token = user.generateToken();
		this.auth.put(token, user);
		
		return token;
	}
	
	@Override
	public void unauthorize(String token)
	{
		this.auth.remove(token);
	}
	
	@Override
	public boolean authorized(String token)
	{
		return this.auth.containsKey(token);
	}

	@Override
	public StandardUser byUserId(String userId)
	{
		if(!this.userIds.containsKey(userId))
			return null;
		
		return this.data.get(this.userIds.get(userId));
	}
	
	private void load()
	{
		if(defaultPath == null)
			return;
		
		File file = new File(defaultPath);
		
		if(!file.exists() || file.isDirectory())
			return;
		
		try
		{
			String json = new String(Files.readAllBytes(Paths.get(defaultPath)));
			
			if(json.isEmpty())
				return;
			
			List<StandardUser> users = StandardUser.allFromJson(json);
			
			if(users == null)
				return;
			
			for(StandardUser user : users)
				add(user);
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}