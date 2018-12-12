package de.htw.ai.kbe.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
 
@Provider
public class UnhandledExceptionFilter extends Throwable implements ExceptionMapper<Throwable>
{
    private static final long serialVersionUID = 1L;
  
    @Override
    public Response toResponse(Throwable exception)
    {
        return Response.status(400).entity("Something went wrong, please check the header and payload of youre request.").build();
    }
}