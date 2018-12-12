//         String tmp = Long.toHexString(Double.doubleToLongBits(Math.random()));


//<!doctype html><html lang="en"><head><title>HTTP Status 500 – Internal Server Error</title><style type="text/css">h1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} h2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} h3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} body {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} b {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} p {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;} a {color:black;} a.name {color:black;} .line {height:1px;background-color:#525D76;border:none;}</style></head><body><h1>HTTP Status 500 – Internal Server Error</h1><hr class="line" /><p><b>Type</b> Status Report</p><p><b>Message</b> Internal Server Error</p><p><b>Description</b> The server encountered an unexpected condition that prevented it from fulfilling the request.</p><hr class="line" /><h3>Apache Tomcat/9.0.13</h3></body></html>


package de.htw.ai.kbe.services;

import java.util.Collection;
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
import de.htw.ai.kbe.songs.Song;
import de.htw.ai.kbe.songs.SongEntry;

// URL fuer diesen Service ist: http://localhost:8080/songsRX/rest/songs 
@Path("/songs")
public class SongsWebService {

	private IDatabase<SongEntry, Song> db; 
	
	@Inject
	public SongsWebService(IDatabase<SongEntry, Song> db, UriInfo uriInfo) {
		super();
		this.db = db;
	}
	
	@Secured
	@GET
	@Path("/default")
	@Produces(MediaType.TEXT_PLAIN)
	public String setDefaultSongs() {
		db.add(new Song("title", "album", "artist", new Integer(2001)));
		db.add(new Song("title 2", "album 2", "artist 2", new Integer(2002)));
		return "YAAY your database contains some default values";
	}

	@Secured
	@GET 
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<Song> getAllSongs() {
		System.out.println("check1");
		System.out.println(db.values().toString());
		return db.values();
	}


	//GET http://localhost:8080/contactsJAXRS/rest/contacts/1
	//Returns: 200 and contact with id 1
	//Returns: 404 on provided id not found
	@Secured
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSong(@PathParam("id") Integer id) {
		Song song = db.get(id);
		if (song != null)
			return Response.ok(song).build();
		else
			return Response.status(Response.Status.NOT_FOUND).entity("No song found with id " + id).build();
	}

	
	//  Status Code 201 und URI fuer den neuen Eintrag im http-header 'Location' zurueckschicken, also:
	
	
	@Context UriInfo uriInfo; // Dependency Injection (spaeter)
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	@Secured
	public Response createSong(Song song) {
	     db.add(song);
	     UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
	     uriBuilder.path(Integer.toString(song.getId()));
	     return Response.created(uriBuilder.build()).build();
	}
    
	//     return Response.ok(json, MediaType.APPLICATION_JSON).build();

	
	
	// location header verwenden
	@Secured
	@PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}")
    public Response updateSong(@PathParam("id") Integer id, Song song) {
		if(song != null && song.getId() != -1 && id == song.getId()) {
			if(songIsValid(song)) {
				db.update(id, song);
				return Response.status(204).build();	
			}else {
				return Response.status(400).entity("GIVEN SONG DOES NOT EXISTS IN DATABASE OR IS NOT VALID, AT LEAST ID AND TITLE SHOULD BE GIVEN").build();
			}
		}
		else {
			return Response.status(400).entity("GIVEN ID DOESNT MATCH SONG-ID").build();
		}
    }
	
	@Secured
	@DELETE
	@Path("/{id}")
	public Response deleteSong(@PathParam("id") Integer id) {
		db.delete(id);
		return Response.status(204).build();
	}
	
	private boolean songIsValid(Song song) {
		if(song.title != null && db.get(song.getId()) != null)
			return true;
		return false;
	}
	
}