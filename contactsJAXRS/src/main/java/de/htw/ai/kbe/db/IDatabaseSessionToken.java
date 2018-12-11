package de.htw.ai.kbe.db;

public interface  IDatabaseSessionToken {

	public void add(String token);
	public Boolean exist(String token);
	public Boolean remove(String token);
}
