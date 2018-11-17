// http://localhost:8080/echoServlet/echo

package de.htw.ai.kbe.echo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

public class SongsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String uriToDB = null;

	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
	    // Beispiel: Laden eines Konfigurationsparameters aus der web.xml
		this.uriToDB = servletConfig.getInitParameter("uriToDBComponent");
	}

	@Override
	public void doGet(HttpServletRequest request, 
	        HttpServletResponse response) throws IOException {
		// alle Parameter (keys)
		Enumeration<String> paramNames = request.getParameterNames();

		String responseStr = "";
		String param = "";
		while (paramNames.hasMoreElements()) {
			param = paramNames.nextElement();
			responseStr = responseStr + param + "=" 
			+ request.getParameter(param) + "\n";
		}
		response.setContentType("text/plain");
		try (PrintWriter out = response.getWriter()) {
			responseStr += "\n Your request will be sent to " + uriToDB;
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			out.println(responseStr);
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/plain");
		ServletInputStream inputStream = request.getInputStream();
		byte[] inBytes = IOUtils.toByteArray(inputStream);
		try (PrintWriter out = response.getWriter()) {
			out.println(new String(inBytes));
			
			
			
			
			
			
			
			
		}
	}
	
	protected String getUriToDB () {
		return this.uriToDB;
	}
}




//
//// Reads a list of songs from a JSON-file into List<Song>
//@SuppressWarnings("unchecked")
//static List<OurSong> readJSONToSongs(String filename) throws FileNotFoundException, IOException {
//	ObjectMapper objectMapper = new ObjectMapper();
//	try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
//		return (List<OurSong>) objectMapper.readValue(is, new TypeReference<List<OurSong>>(){});
//	}
//}
//
//// Write a List<Song> to a JSON-file
//static void writeSongsToJSON(List<OurSong> songs, String filename) throws FileNotFoundException, IOException {
//	ObjectMapper objectMapper = new ObjectMapper();
//	try (OutputStream os = new BufferedOutputStream(new FileOutputStream(filename))) {
//		objectMapper.writeValue(os, songs);
//	}
//}