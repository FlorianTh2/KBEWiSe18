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
    	return ResponseException.build(400, ResponseException.SOMETHING_WENT_WRONG);
    }
}