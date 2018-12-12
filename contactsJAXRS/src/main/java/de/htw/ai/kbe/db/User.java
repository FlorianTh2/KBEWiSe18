package de.htw.ai.kbe.db;

import java.security.SecureRandom;

import java.util.Base64;

public abstract class User
{
	private Integer id;
	private String userId;
	
	public User()
	{
		super();
		this.id = null;
		this.userId = null;
	}
	
	public User(Integer id, String userId)
	{
		super();
		this.id = id;
		this.userId = userId;
	}
	
	public int getId()
	{
		return this.id.intValue();
	}
	
	public void setId(int id)
	{
		this.id = Integer.valueOf(id);
	}
	
	public boolean hasId()
	{
		return this.id != null && this.id >= 0;
	}
	
	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String generateToken()
	{
		SecureRandom random = new SecureRandom();
		byte[] bytes = new byte[64];
		random.nextBytes(bytes);
		
		return Base64.getEncoder().encodeToString(bytes);
	}
}

