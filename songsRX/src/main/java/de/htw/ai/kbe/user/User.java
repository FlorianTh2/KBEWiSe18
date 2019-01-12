package de.htw.ai.kbe.user;

import java.security.SecureRandom;

import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "userid")
	private String userId;
	
	public User()
	{
		super();
		this.id = null;
		this.userId = null;
	}
	
	public User(String userId)
	{
		super();
		this.userId = userId;
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

