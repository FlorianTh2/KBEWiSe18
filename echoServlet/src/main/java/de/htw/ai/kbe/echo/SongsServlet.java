// http://localhost:8080/echoServlet/echo

package de.htw.ai.kbe.echo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

public class SongsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static SongDatabase db;
	private static String uriToDB;
	
	
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
	    // Beispiel: Laden eines Konfigurationsparameters aus der web.xml
		this.uriToDB = servletConfig.getInitParameter("uriToDBComponent");
		
		if(isValidDBURI(this.uriToDB))
		{
			db = SongDatabase.loadFromFile(uriToDB);
		} else {
			uriToDB = null;
			db = new SongDatabase();
		}
	}
	
	public boolean isValidDBURI(String uri)
	{
		if(uri == null)
			return false;
		
		File file = new File(uri);
		
		if(!file.exists() || file.isDirectory())
			return false;
		return true;
	}

	@Override
	public void doGet(HttpServletRequest request, 
	        HttpServletResponse response) throws IOException
	{
		if(!isValidHeaderGET(request)) {
		    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		// alle Parameter (keys)
		Enumeration<String> paramNames = request.getParameterNames();

		response.setContentType("application/json");

		StringBuilder builder = new StringBuilder();
		boolean single = false;
		
			
		while (paramNames.hasMoreElements())
		{
			String param = paramNames.nextElement();
			
			switch(param)
			{
				case "all":
					builder = new StringBuilder();
					builder.append(db.toJson());
					single = true;
					break;
				case "songId":
					if(single)
						break;
					
					String[] paramValues = request.getParameterValues(param);
					
					if(paramValues == null)
						break;
					
					int[] paramParsed = new int[paramValues.length];
					
					for(int i = 0; i < paramValues.length; ++i)
						paramParsed[i] = Integer.parseInt(paramValues[i]);
					
					String query = db.queryToJson(paramParsed);
					
					if(query != null)
						builder.append(query);
					else
					    response.sendError(HttpServletResponse.SC_NOT_FOUND);
					break;
			}
			
		}
		
		
		try (PrintWriter out = response.getWriter())
		{
			out.print(builder);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{	
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = request.getReader();
		
		String line;
		while((line = reader.readLine()) != null)
			builder.append(line);
		
		Song song = Song.fromJson(builder.toString());
		
		if(song == null)
		{
			response.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
		} else if(song.valid()) {
			db.add(song);
			response.setHeader("Location", "http://localhost:8080/songsServlet?songId=" + song.getId().toString());		
		} else {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
		
	}
	
	@Override
	public void destroy()
	{
		if(this.uriToDB != null)
			this.db.saveToFile(this.uriToDB);
	}

	protected String getUriToDB () {
		return this.uriToDB;
	}
	
	
	public boolean isValidHeaderGET(HttpServletRequest request)
	{
		String acceptHeader = request.getHeader("Accept");
		
		if(acceptHeader == null)
			return true;
		
		if(acceptHeader.equals("*") || acceptHeader.equals("application/json"))
			return true;
		return false;
	}
}





