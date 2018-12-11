//         String tmp = Long.toHexString(Double.doubleToLongBits(Math.random()));

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

@Path("/auth")
public class AuthenticationWebService {
	private IDatabase db; 

	@Inject
	public AuthenticationWebService(IDatabase db, UriInfo uriInfo) {
		super();
		this.db = db;
	}

	
	
}
