package de.htw.ai.kbe.db;

import de.htw.ai.kbe.user.IUser;

public interface IDatabaseUser<T extends IUser> {

	public T getUser();
	public void addUser();
	public T exists(T user);
}
