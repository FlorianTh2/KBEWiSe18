package de.htw.ai.kbe.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.htw.ai.kbe.auth.Secured;
import de.htw.ai.kbe.auth.StandardUser;
import de.htw.ai.kbe.db.IDatabase;
import de.htw.ai.kbe.exceptions.ResponseException;
import de.htw.ai.kbe.songs.Song;
import de.htw.ai.kbe.songs.SongEntry;
import de.htw.ai.kbe.songs.SongList;
import de.htw.ai.kbe.songs.SongListEntry;
import de.htw.ai.kbe.songs.Visibility;
import de.htw.ai.kbe.user.IUserRegistry;

// 	http://localhost:8080/songsRX/rest/songLists
@Path("/songLists")
public class SongListsWebService
{
	private IDatabase<SongListEntry, SongList> db;
	private IUserRegistry<StandardUser> registry;
	private IDatabase<SongEntry,Song> songdb;
	
	@Inject
	public SongListsWebService(IDatabase<SongListEntry, SongList> db, IUserRegistry<StandardUser> registry, IDatabase<SongEntry,Song> songdb)
	{
		super();
		this.db = db;
		this.registry = registry;
		this.songdb = songdb;
	}
	
	@Secured
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getSongListsForUser(@QueryParam("userId") String userId, @Context HttpHeaders headers)
	{
		StandardUser user = this.registry.byUserId(userId);
		
		if(user == null)
			return ResponseException.build(404, ResponseException.INVALID_ID);
		
		
		String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		boolean owner = this.registry.authorizedMatches(user, token);
		
		List<SongList> lists = listsOfUser(user, owner);
		
		// since we return a list and xml cannot find root element for list
		return Response.ok(new GenericEntity<List<SongList>>(lists) {}).build();
	}
	
	@Secured
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}")
	public Response getSongListsID(@PathParam("id") Integer id, @Context HttpHeaders headers)
	{
		String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		StandardUser user = this.registry.authorizedWhoIs(token);
		
		SongList songList = this.db.get(id);
		
		if(songList == null)
			return ResponseException.build(404, ResponseException.ID_NOT_FOUND);
		
		Response response = ResponseException.build(403, ResponseException.ACCESS_DENIED);
		
		switch(songList.visiblity())
		{
			case Public:
				response = Response.ok(songList).build();
				break;
			case Private:
				if(songList.getOwner().getId() == user.getId())
					response = Response.ok(songList).build();
				break;
			default:
				break;
		}
		
		return response;
	}
	
	@Context UriInfo uriInfo;
	@Secured
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.TEXT_PLAIN)
	public Response postSongListsForUser(SongList songList, @Context HttpHeaders headers)
	{
		String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		StandardUser user = this.registry.authorizedWhoIs(token);
		
		if(!checkIfSongsExists(songList.getSongs()))
			return ResponseException.build(400, ResponseException.INVALID_PAYLOAD);
		
		
		songList.setOwner(user);
		this.db.add(songList);
		
		return Response.created(uriInfo.getAbsolutePathBuilder().path(Integer.toString(songList.getId())).build()).build();
	}
	
	@Secured
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteSongListsForUser(@PathParam("id") Integer id, @Context HttpHeaders headers)
	{
		String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		StandardUser user = registry.authorizedWhoIs(token);
		SongList songList = this.db.get(id);
		
		if(songList == null)
			return ResponseException.build(404, ResponseException.ID_NOT_FOUND);
		
		if(songList.getOwner().getId() != user.getId())
			return ResponseException.build(403, ResponseException.ACCESS_DENIED);
		
		this.db.delete(songList.getId());
		songList = null;
		
		return Response.ok().build();
	}
	
	private boolean checkIfSongsExists(Set<Song> songs) 
	{
		for(Song s : songs)
		{
			if(!this.songdb.exists(s.getId()))
				return false;
		}
		
		return true;
	}
	
	private List<SongList> listsOfUser(StandardUser user, boolean isOwner)
	{
		List<SongList> lists = this.db.values();
		List<SongList> filtered = new ArrayList<SongList>();
		
		for(SongList sl : lists)
		{
			if(sl.getOwner().getId() != user.getId())
				continue;
			
			switch(sl.visiblity())
			{
				case Public:
					filtered.add(sl);
					break;
				case Private:
					if(isOwner)
						filtered.add(sl);
					break;
				default:
					break;
			}
		}
		
		return filtered;
	}
}