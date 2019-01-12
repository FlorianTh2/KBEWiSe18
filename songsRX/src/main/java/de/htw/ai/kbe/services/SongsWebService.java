package de.htw.ai.kbe.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.htw.ai.kbe.*;
import de.htw.ai.kbe.auth.Secured;
import de.htw.ai.kbe.db.IDatabase;
import de.htw.ai.kbe.exceptions.ResponseException;
import de.htw.ai.kbe.songs.Song;
import de.htw.ai.kbe.songs.SongEntry;

//http://localhost:8080/songsRX/rest/songs 
@Path("/songs")
public class SongsWebService
{
	private IDatabase<SongEntry, Song> db; 
	
	@Inject
	public SongsWebService(IDatabase<SongEntry, Song> db, UriInfo uriInfo) {
		super();
		this.db = db;
	}
	

	//@Secured
	@GET 
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<Song> getAllSongs() {
		System.out.println("hallo");
		return db.values();
	}

	//@Secured
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSong(@PathParam("id") Integer id)
	{
		Song song = db.get(id);
		
		if (song == null)
			return ResponseException.build(404, ResponseException.RESOURCE_NOT_FOUND);
		
		return Response.ok(song).build();
	}
	
	@Context UriInfo uriInfo;
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	//@Secured
	public Response createSong(Song song)
	{
		if(!songIsValid(song))
			return ResponseException.build(400, ResponseException.INVALID_PAYLOAD);
		
	     db.add(song); ////////////////// wir brauchen irgendwie die id zurück
	     
	     UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
	     uriBuilder.path(Integer.toString(song.getId()));

	     return Response.created(uriBuilder.build()).build();
	}
    
	//@Secured
	@PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}")
    public Response updateSong(@PathParam("id") Integer id, Song song)
	{
		if(id == null)
			return ResponseException.build(400, ResponseException.INVALID_ID);
		
		if(!songIsValid(song))
			return ResponseException.build(400, ResponseException.INVALID_PAYLOAD);
		
		if(id.intValue() != song.getId())
			return ResponseException.build(400, ResponseException.RESOURCE_ID_NOT_PAYLOAD_ID);
		
		if(!db.exists(id.intValue()))
			return ResponseException.build(404, ResponseException.RESOURCE_NOT_FOUND);
		
		db.update(id, song);
		return Response.status(204).build();
    }
	
	
	private boolean songIsValid(Song song)
	{
		if(song != null && song.getTitle() != null)
			return true;
		return false;
	}
	
}