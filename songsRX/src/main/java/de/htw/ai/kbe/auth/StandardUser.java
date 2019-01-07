package de.htw.ai.kbe.auth;

import java.io.IOException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htw.ai.kbe.user.User;

@Entity
@Table(name = "user")
public class StandardUser extends User
{
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	public static List<StandardUser> allFromJson(String json)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			List<StandardUser> list = mapper.readValue(json, new TypeReference<List<StandardUser>>() {});
			
			return list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static StandardUser fromJson(String json)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try
		{
			StandardUser user = mapper.readValue(json, StandardUser.class);
			return user;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public StandardUser()
	{
		super();
		this.firstName = null;
		this.lastName = null;
	}
	
	public StandardUser(String firstName, String lastName)
	{
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public StandardUser(Integer id, String userId, String firstName, String lastName)
	{
		super(id, userId);
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}

}
