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
import de.htw.ai.kbe.db.IDatabase;
import de.htw.ai.kbe.songs.Song;

// URL fuer diesen Service ist: http://localhost:8080/songsRX/rest/songs 
@Path("/songs")
public class SongsWebService {

	private IDatabase db; 

	@Inject
	public SongsWebService(IDatabase db) {
		super();
		this.db = db;
		//db.add(new Song("title1","artist1","album1",new Integer(1992)));
		//db.add(new Song("title2","artist2","album2",new Integer(1991)));

	}

	@GET 
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Collection<Song> getAllSongs() {
		return db.values();
	}

	

	//GET http://localhost:8080/contactsJAXRS/rest/contacts/1
	//Returns: 200 and contact with id 1
	//Returns: 404 on provided id not found
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Song getContact(@PathParam("id") Integer id) {
		Song song = (Song)db.get(id);
		if (song != null) {
			System.out.println("getContact: Returning contact for id " + id);
			return song;  //Response.ok(contact).build();
		} else {
			return song; //Response.status(Response.Status.NOT_FOUND).entity("No contact found with id " + id).build();
		}
	}

	
	//  Status Code 201 und URI fuer den neuen Eintrag im http-header 'Location' zurueckschicken, also:
	@Context UriInfo uriInfo; // Dependency Injection (spaeter)
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response createContact(Song song) {
	     db.add(song);
	     //return Response.ok().entity(newId).build();
	     UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
	     //uriBuilder.path(Integer.toString(newId));
	     return Response.created(uriBuilder.build()).build();
	}
    
	
	// location header verwenden
	@PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}")
    public Response updateSong(@PathParam("id") Integer id, Song contact) {
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("PUT not implemented").build();
    }

	@DELETE
	@Path("/{id}")
	public Response deleteSong(@PathParam("id") Integer id) {
		return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity("DELETE not implemented").build();
	}
}