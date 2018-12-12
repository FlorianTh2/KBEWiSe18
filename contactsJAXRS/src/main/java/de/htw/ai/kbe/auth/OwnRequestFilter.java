package de.htw.ai.kbe.auth;

import java.io.IOException;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import de.htw.ai.kbe.db.IUserRegistry;
import de.htw.ai.kbe.user.StandardUser;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class OwnRequestFilter implements ContainerRequestFilter {
    
	private IUserRegistry<StandardUser> registry; 
	
	
	@Inject
	public OwnRequestFilter(IUserRegistry<StandardUser> registry)
	{
		System.out.println("hasdhfjahsdfhasdfgasdfghassdfghfasdgfgasdgdfgafsdgsdfghasdsdfgf");
		this.registry = registry;
	}
	
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null) {
        	requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
        
        String token = authorizationHeader;
        
        if(!registry.authorized(token))
        	requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
    }
}