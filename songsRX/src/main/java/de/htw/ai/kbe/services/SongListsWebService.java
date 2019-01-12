package de.htw.ai.kbe.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.htw.ai.kbe.auth.StandardUser;
import de.htw.ai.kbe.db.IDatabase;
import de.htw.ai.kbe.songs.SongList;
import de.htw.ai.kbe.songs.SongListEntry;

// 	http://localhost:8080/songsRX/rest/songLists
@Path("/songList")
public class SongListsWebService {

	private IDatabase<SongListEntry, SongList> db;
	
	public SongListsWebService() {
		super();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getSongListsForUser(@QueryParam("userId") String userId) {
		return Response.ok(db.values()).build();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}")
	public Response getSongListsID(@PathParam("id") Integer id) {
		return Response.ok(db.get(id)).build();
	}
	
	@Context UriInfo uriInfo;
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces(MediaType.TEXT_PLAIN)
	public Response postSongListsForUser(SongList songlist) {
		return Response.created(uriInfo.getAbsolutePathBuilder().path(Integer.toString(songlist.getId())).build()).build();
	}
	
	
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteSongListsForUser(@PathParam("id") Integer id) {
		db.delete(id);
		return Response.ok().build();
	}
	
}
