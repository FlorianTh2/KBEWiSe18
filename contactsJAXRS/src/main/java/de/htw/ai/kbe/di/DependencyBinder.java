package de.htw.ai.kbe.di;

import java.io.File;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import de.htw.ai.kbe.db.IDatabase;
import de.htw.ai.kbe.db.IDatabaseSessionToken;
import de.htw.ai.kbe.db.IUserRegistry;
import de.htw.ai.kbe.sessionToken.SessionTokenDatabase;
import de.htw.ai.kbe.songs.Song;
import de.htw.ai.kbe.songs.SongDatabase;
import de.htw.ai.kbe.songs.SongEntry;
import de.htw.ai.kbe.songs.SongFileDatabase;
import de.htw.ai.kbe.user.StandardUser;
import de.htw.ai.kbe.user.StandardUserRegistry;

public class DependencyBinder extends AbstractBinder {
	@Override
	protected void configure() {
		//in(Singleton.class) legt das ganze als Singleton fest
		//bind(InMemoryAddressBook.class).to(IAddressBook.class).in(Singleton.class);
		//bind(SongDatabase.class).to(IDatabase.class).in(Singleton.class);
		//bind(UserDatabase.class).to(IDatabaseUser.class).in(Singleton.class);
		

		SongFileDatabase.defaultPath = "/home/florian/Desktop/KBE/dataForBeleg2Servlet/songsOld.json";
		StandardUserRegistry.defaultPath = "/home/florian/Desktop/KBE/dataForBeleg2Servlet/user.json";
		
		if(SongFileDatabase.defaultPathAvailable())
			bind(SongFileDatabase.class).to(new TypeLiteral<IDatabase<SongEntry, Song>>(){}).in(Singleton.class);
		else
			bind(SongDatabase.class).to(new TypeLiteral<IDatabase<SongEntry, Song>>(){}).in(Singleton.class);
			
		bind(StandardUserRegistry.class).to(new TypeLiteral<IUserRegistry<StandardUser>>(){}).in(Singleton.class);
		
		
		//bind(SessionTokenDatabase.class).to(IDatabaseSessionToken.class).in(Singleton.class);
	}
}
