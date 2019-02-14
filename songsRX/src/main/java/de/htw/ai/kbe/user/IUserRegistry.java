package de.htw.ai.kbe.user;

import java.util.List;

public interface IUserRegistry<T extends User>
{
	void add(T user);
	T get(int id);
	T remove(int id);
	void delete(int id);
	List<T> users();
	String authorize(int id);
	void unauthorize(String id);
	boolean authorized(String token);
	boolean authorizedMatches(T user, String token);
	T authorizedWhoIs(String token);
	T byUserId(String userId);
}
