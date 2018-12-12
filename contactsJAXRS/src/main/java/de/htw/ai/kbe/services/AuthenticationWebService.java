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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import de.htw.ai.kbe.*;
import de.htw.ai.kbe.auth.StandardUser;
import de.htw.ai.kbe.db.IDatabase;
import de.htw.ai.kbe.user.IUserRegistry;

@Path("/auth")
public class AuthenticationWebService {
	private IUserRegistry<StandardUser> registry; 

	@Inject
	public AuthenticationWebService(IUserRegistry<StandardUser> registry, UriInfo uriInfo) {
		super();
		this.registry = registry;
	}
	
	@GET 
    @Produces(MediaType.TEXT_PLAIN)
	public Response getAuthentication(@QueryParam("userId") String userId)
	{
		StandardUser user = registry.byUserId(userId);
		
		if(user != null)
        	return Response.ok().entity(registry.authorize(user.getId())).build();
		else
			return Response.status(403).entity("USERID DOES NOT EXISTS IN DATABASE" + "\n" + registry.users()).build();	
	}	
}