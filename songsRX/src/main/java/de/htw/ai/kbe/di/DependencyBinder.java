package de.htw.ai.kbe.di;

import java.io.File;

import javax.inject.Singleton;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import de.htw.ai.kbe.auth.StandardUser;
import de.htw.ai.kbe.auth.StandardUserRegistry;
import de.htw.ai.kbe.db.IDatabase;
import de.htw.ai.kbe.songs.Song;
import de.htw.ai.kbe.songs.SongDatabase;
import de.htw.ai.kbe.songs.SongEntry;
import de.htw.ai.kbe.songs.SongFileDatabase;
import de.htw.ai.kbe.songs.SongPostgresDatabase;
import de.htw.ai.kbe.user.IUserRegistry;

public class DependencyBinder extends AbstractBinder {
	@Override
	protected void configure()
	{
		SongFileDatabase.defaultPath = "/home/florian/Desktop/KBE/dataForBeleg2Servlet/songsOld.json";
		StandardUserRegistry.defaultPath = "/home/florian/Desktop/KBE/dataForBeleg2Servlet/user.json";
		
		bind(SongPostgresDatabase.class).to(new TypeLiteral<IDatabase<SongEntry, Song>>(){}).in(Singleton.class);
			
		bind(StandardUserRegistry.class).to(new TypeLiteral<IUserRegistry<StandardUser>>(){}).in(Singleton.class);
	}
}
