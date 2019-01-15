package de.htw.ai.kbe.services;
import static org.junit.Assert.*;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.api.TypeLiteral;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

import org.junit.Before;
import org.junit.Test;

import de.htw.ai.kbe.auth.AuthorizationFilter;
import de.htw.ai.kbe.auth.StandardUser;
import de.htw.ai.kbe.auth.StandardUserRegistry;
import de.htw.ai.kbe.db.IDatabase;
import de.htw.ai.kbe.di.DependencyBinder;
import de.htw.ai.kbe.exceptions.UnhandledExceptionFilter;
import de.htw.ai.kbe.songs.Song;
import de.htw.ai.kbe.songs.SongDatabase;
import de.htw.ai.kbe.songs.SongEntry;
import de.htw.ai.kbe.songs.SongFileDatabase;
import de.htw.ai.kbe.user.IUserRegistry;

public class SongsWebServiceTest extends JerseyTest
{
	public static class DependencyBinderTest extends AbstractBinder
	{
		@Override
		protected void configure()
		{
			SongFileDatabase.defaultPath = "src/test/java/de/htw/ai/kbe/services/songsOld.json";
			StandardUserRegistry.defaultPath = "src/test/java/de/htw/ai/kbe/services/user.json";
			
			if(SongFileDatabase.defaultPathAvailable())
				bind(SongFileDatabase.class).to(new TypeLiteral<IDatabase<SongEntry, Song>>(){}).in(Singleton.class);
			else
				bind(SongDatabase.class).to(new TypeLiteral<IDatabase<SongEntry, Song>>(){}).in(Singleton.class);
				
			bind(StandardUserRegistry.class).to(new TypeLiteral<IUserRegistry<StandardUser>>(){}).in(Singleton.class);
		}
	}

	public static class ApplicationTest extends ResourceConfig
	{
		public ApplicationTest()
		{
			register(new DependencyBinderTest());
			register(AuthorizationFilter.class);
	        register(UnhandledExceptionFilter.class);
			packages("de.htw.ai.kbe.services");
		}
	}
	
	private String token;
	
	@Override
	protected Application configure()
	{
		return new ApplicationTest();
	}
	
	@Before
	public void setUpToken() throws Exception
	{ 
		Response response = target("/auth").queryParam("userId", "eschuler").request().get();
		token = response.readEntity(String.class);
	}
	
	@Test
	public void databaseShouldBeLoaded()
	{
		Response response = target("/songs/1").request().header("Authorization", token).get();
		assertEquals(200, response.getStatus());
		response = target("/songs/10").request().header("Authorization", token).get();
		assertEquals(200, response.getStatus());
	}
	
	@Test
	public void updateSongWithJsonShouldBeValid()
	{
		Song s = new Song("Test Title", "Test Artist", "Test Album", Integer.valueOf(2000), 1);
		Response response = target("/songs/1").request().header("Authorization", token).put(Entity.json(s));
		assertEquals(204, response.getStatus());
	}
	
	@Test
	public void updateSongWithXmlShouldBeValid()
	{
		Song s = new Song("Test Title", "Test Artist", "Test Album", Integer.valueOf(2000), 1);
		Response response = target("/songs/1").request().header("Authorization", token).put(Entity.xml(s));
		assertEquals(204, response.getStatus());
	}
	
	@Test
	public void updateSongWithoutPayloadShouldBeInvalid()
	{
		Song s = new Song();
		Response response = target("/songs/1").request().header("Authorization", token).put(Entity.json(s));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void updateSongWithoutExistingIdShouldBeInvalid()
	{
		Song s = new Song("Test Title", "Test Artist", "Test Album", Integer.valueOf(2000), 999);
		Response response = target("/songs/999").request().header("Authorization", token).put(Entity.json(s));
		assertEquals(404, response.getStatus());
	}
	
	@Test
	public void updateSongWithoutValidIdShouldBeInvalid()
	{
		Song s = new Song("Test Title", "Test Artist", "Test Album", Integer.valueOf(2000));
		Response response = target("/songs/test").request().header("Authorization", token).put(Entity.json(s));
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void updateSongWithoutMatchingIdBeInvalid()
	{
		Song s = new Song("Test Title", "Test Artist", "Test Album", Integer.valueOf(2000), 2);
		Response response = target("/songs/1").request().header("Authorization", token).put(Entity.json(s));
		assertEquals(400, response.getStatus());
	}
}
