package de.htw.ai.kbe.user;

public class User implements IUser {

	private Integer id;
	private String userId;
	private String firstName;
	private String lastName;
	
	public User() {
	}
	
	public User(Builder builder) {
		this.id = builder.id;
		this.userId = builder.userId;
		this.lastName = builder.lastName;
		
		// optional
		this.firstName = builder.firstName;
	}

	public static class Builder{
		private Integer id;
		private String userId;
		private String firstName;
		private String lastName;
		
		public Builder(Integer id, String userId, String lastname) {
			this.id = id;
			this.userId = userId;
			this.lastName = lastname;
		}
		
		public Builder firstName(String val) {
			this.firstName = val;
			return this;
		}
		
		public User build() {
			return new User(this);
		}
	}
	
	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public String getFirstname() {
		return this.firstName;
	}

	@Override
	public String getLastname() {
		return this.lastName;
	}

}
