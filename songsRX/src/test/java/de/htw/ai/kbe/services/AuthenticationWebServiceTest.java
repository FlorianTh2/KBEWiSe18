//package de.htw.ai.kbe.services;
//import static org.junit.Assert.*;
//
//import javax.inject.Singleton;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import org.glassfish.hk2.api.TypeLiteral;
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.test.JerseyTest;
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import de.htw.ai.kbe.auth.AuthorizationFilter;
//import de.htw.ai.kbe.auth.StandardUser;
//import de.htw.ai.kbe.auth.StandardUserRegistry;
//import de.htw.ai.kbe.db.IDatabase;
//import de.htw.ai.kbe.exceptions.UnhandledExceptionFilter;
//import de.htw.ai.kbe.services.SongsWebServiceTest.ApplicationTest;
//import de.htw.ai.kbe.services.SongsWebServiceTest.DependencyBinderTest;
//import de.htw.ai.kbe.songs.Song;
//import de.htw.ai.kbe.songs.SongDatabase;
//import de.htw.ai.kbe.songs.SongEntry;
//import de.htw.ai.kbe.songs.SongFileDatabase;
//import de.htw.ai.kbe.user.IUserRegistry;
//
//public class AuthenticationWebServiceTest extends JerseyTest{
//
//	private String token = null;
//	Response response = null;
//
//	
//	public static class DependencyBinderTest extends AbstractBinder
//	{
//		@Override
//		protected void configure()
//		{
//			SongFileDatabase.defaultPath = "src/test/java/de/htw/ai/kbe/services/songsOld.json";
//			StandardUserRegistry.defaultPath = "src/test/java/de/htw/ai/kbe/services/user.json";
//			
//			if(SongFileDatabase.defaultPathAvailable())
//				bind(SongFileDatabase.class).to(new TypeLiteral<IDatabase<SongEntry, Song>>(){}).in(Singleton.class);
//			else
//				bind(SongDatabase.class).to(new TypeLiteral<IDatabase<SongEntry, Song>>(){}).in(Singleton.class);
//				
//			bind(StandardUserRegistry.class).to(new TypeLiteral<IUserRegistry<StandardUser>>(){}).in(Singleton.class);
//		}
//	}
//
//	public static class ApplicationTest extends ResourceConfig
//	{
//		public ApplicationTest()
//		{
//			register(new DependencyBinderTest());
//			register(AuthorizationFilter.class);
//	        register(UnhandledExceptionFilter.class);
//			packages("de.htw.ai.kbe.services");
//		}
//	}
//		
//	@Override
//	protected Application configure()
//	{
//		return new ApplicationTest();
//	}
//	
//	@Before
//	public void beforeEach()
//	{
//		response = target("/auth").queryParam("userId", "eschuler").request().get();
//		token = response.readEntity(String.class);
//	}
//	
//	@After
//	public void afterEach() {
//		token = null;
//	}
//	
//	
//	@Test
//	public void checkIfGivenTokenIsThereTest() {
//		assertNotEquals(null, token);
//	}
//	
//	@Test
//	public void checkIfTokenIsValid() {
//		response = target("/songs/1").request(MediaType.APPLICATION_JSON).header("Authorization", token).get();
//		assertEquals(response.getStatus(),200);
//	}
//	
//	@Test
//	public void SuccessForGetSongsWithToken() {
//		response = target("/songs/5").request(MediaType.APPLICATION_JSON).header("Authorization", token).get();
//		assertEquals(response.getStatus(),200);
//	}
//	
//	@Test
//	public void SuccessForPostWithToken() {
//		Song s1 = new Song("Title 01", "Artist 01", "Album 01", Integer.valueOf(1991));
//		response = target("/songs").request().header("Authorization", token).post(Entity.json(s1));
//		assertEquals(response.getStatus(),201);
//	}
//	
//	@Test
//	public void FailForPostWithoutToken() {
//		Song s1 = new Song("Title 01", "Artist 01", "Album 01", Integer.valueOf(1991));
//		response = target("/songs").request().post(Entity.json(s1));
//		assertEquals(response.getStatus(),401);
//	}
//
//	@Test
//	public void SuccessForPutWithToken() {
//		Song s = new Song("Test Title", "Test Artist", "Test Album", Integer.valueOf(2000), 1);
//		Response response = target("/songs/1").request().header("Authorization", token).put(Entity.json(s));
//		assertEquals(204, response.getStatus());
//	}
//	
//	public void FailForPutWithoutToken() {
//		Song s = new Song("Test Title", "Test Artist", "Test Album", Integer.valueOf(2000), 1);
//		Response response = target("/songs/1").request().header("Authorization", token).put(Entity.json(s));
//		assertEquals(401, response.getStatus());
//	}
//	
//	public void SuccessForGetAllWithToken() {
//		response = target("/songs/").request(MediaType.APPLICATION_JSON).header("Authorization", token).get();
//		assertEquals(response.getStatus(),200);
//	}
//	
//	public void FailForGetAllWithoutToken() {
//		response = target("/songs/").request(MediaType.APPLICATION_JSON).get();
//		assertEquals(response.getStatus(),401);
//	}
//	
//	@Test
//	public void checkForUnauthorizedErrorIfNoTokenWasSendTest() {
//		response = target("/songs/1").request(MediaType.APPLICATION_JSON).get();
//		assertEquals(response.getStatus(), 401);
//	}
//	
//	@Test
//	public void failForWrongTokenTest() {
//		response = target("/songs/1").request(MediaType.APPLICATION_JSON).header("Authorization", "noToken").get();
//		assertEquals(response.getStatus(), 401);
//	}
//	
//	@Test
//	public void failTokengenerationForUnexistingUserIdTest() {
//		response = target("/auth").queryParam("userId", "testUserId").request().get();
//		assertEquals(response.getStatus(),403);
//	}
//	
//	@Test
//	public void failTokengenerationForNoGivenUserIdTest() {
//		response = target("/auth").queryParam("userId", "").request().get();
//		assertEquals(response.getStatus(),403);
//	}
//	
//	@Test
//	public void initialUserDatabaseShouldBeLoaded()
//	{
//		// There are only 2 users in inital UserDatabase: eschuler and mmuster
//		// if i can generate a key for these users, these users should have been loaded and
//		// thats the evidence for the exsitence of this users
//		
//		Response response1 = target("/auth").queryParam("userId", "eschuler").request().get();
//		String token1 = response1.readEntity(String.class);
//		
//		Response response2 = target("/auth").queryParam("userId", "mmuster").request().get();
//		String token2 = response2.readEntity(String.class);
//		
//		assertNotEquals(token1, token2);
//		assertNotEquals(token1, null);
//	}
//}
