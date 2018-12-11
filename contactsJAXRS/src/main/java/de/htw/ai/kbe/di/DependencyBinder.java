package de.htw.ai.kbe.di;

import javax.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import de.htw.ai.kbe.db.IDatabase;
import de.htw.ai.kbe.db.IDatabaseSessionToken;
import de.htw.ai.kbe.db.IDatabaseUser;
import de.htw.ai.kbe.sessionToken.SessionTokenDatabase;
import de.htw.ai.kbe.songs.SongDatabase;
import de.htw.ai.kbe.user.UserDatabase;

public class DependencyBinder extends AbstractBinder {
	@Override
	protected void configure() {
		//in(Singleton.class) legt das ganze als Singleton fest
		//bind(InMemoryAddressBook.class).to(IAddressBook.class).in(Singleton.class);
		bind(SongDatabase.class).to(IDatabase.class).in(Singleton.class);
		bind(UserDatabase.class).to(IDatabaseUser.class).in(Singleton.class);
		bind(SessionTokenDatabase.class).to(IDatabaseSessionToken.class).in(Singleton.class);
	}
}
