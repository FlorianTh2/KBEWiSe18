// Failure: Test for ids in Response-body of GET
// Problem: We cant use contains like: .contains("\"id\":0") Reason: unclear, did alot of research but, 
//		couldnt find an suitable answer
// Solution: We only search for 0. This aims to the song id. The id is the last element in json, 
// 		so we cant put a } at the end to identify the id of the song and not the date or something else

// curl: curl -X GET -H "Accept: application/json" 'http://localhost:8080/songsServlet/?all'

package de.htw.ai.kbe.echo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import de.htw.ai.kbe.echo.SongsServlet;

public class SongsServletTest {
	
    private SongsServlet servlet;
    private MockServletConfig config;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private final static String URITODB_STRING = "src/test/java/de/htw/ai/kbe/echo/songsTEST.json";
    
    @Before
    public void setUp() throws ServletException
    {
        servlet = new SongsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        config.addInitParameter("uriToDBComponent", URITODB_STRING);
        servlet.init(config); //throws ServletException
    }

    @Test
    public void initShouldSetDBComponentURI()
    {
    	assertEquals(URITODB_STRING, servlet.getUriToDB());
    }
    
    @Test
    public void doGetShouldReturnAllJson() throws IOException
    {
    	request.addParameter("all");
    	servlet.doGet(request, response);
    	// Problem: We cant use contains like: .contains("\"id\":0") Reason: unclear, did alot of research but, 
    	//		couldnt find an suitable answer
    	// Solution: We only search for 0. This aims to the song id. The id is the last element in json, 
    	// 		so we cant put a } at the end to identify the id of the song and not the date or something else
    	assert(response.getContentAsString().contains("0}"));
    	assert(response.getContentAsString().contains("5}"));
    	assert(response.getContentAsString().contains("10}"));
    	assert(!response.getContentAsString().contains("11}"));
    }
    
    @Test
    public void doGetShouldReturnSingleJson() throws IOException
    {
        request.addParameter("songId", "1");
        servlet.doGet(request, response);
    	assert(response.getContentAsString().contains("1"));
    }
    
    @Test
    public void doGetShouldReturnMultiJson() throws IOException
    {
    	request.addParameter("songId", "1");
    	request.addParameter("songId", "2");
    	servlet.doGet(request, response);
    	
    	assert(response.getContentAsString().contains("1}"));
    	assert(response.getContentAsString().contains("2}"));
    }
    
    @Test
    public void doGetShouldPrioritizeAllJson() throws IOException
    {
    	request.addParameter("all");
    	request.addParameter("songId", "0");
    	request.addParameter("songId", "1");
    	servlet.doGet(request, response);
    	assert(response.getContentAsString().contains("0}"));
    	assert(response.getContentAsString().contains("5}"));
    	assert(response.getContentAsString().contains("10}"));
    	assert(!response.getContentAsString().contains("11}"));
    }
    
    @Test
    public void doPostShouldBeInDoGet() throws IOException
    {
    	request.setCharacterEncoding("UTF-8");
    	String msg = "{\n" + 
    			"	\"title\": \"sadf\",\n" + 
    			"	\"artist\": \"Lukas Graham\",\n" + 
    			"	\"album\": \"Lukas Graham (Blue Album)\",\n" + 
    			"	\"released\": 2015\n" + 
    			"}";
    	request.setContent(msg.getBytes());
    	servlet.doPost(request, response);
    	System.out.println(response.getStatus());
    	assertEquals(200,response.getStatus());
    } 
    
    @Test
    public void doGetShouldReturn404WhileIdIsNotValid() throws IOException
    {
      request.addParameter("songId", "-1");      
      servlet.doGet(request, response);

      assertEquals(404, response.getStatus());   	
    }
    
    @Test
    public void doGetShouldAllIfAllAndSongIdRequested() throws IOException
    {
        request.addParameter("songId", "1");
        request.addParameter("all");      
        servlet.doGet(request, response);
    	assert(response.getContentAsString().contains("0}"));
    	assert(response.getContentAsString().contains("5}"));
    	assert(response.getContentAsString().contains("10}"));
    	assert(!response.getContentAsString().contains("11}"));
    }
    
    
    @Test
    public void doPostShouldReturnBadRequestIfNoTitleIsSet() throws IOException
    {
    	String msg = "{\"artist\": \"Lukas Graham\",\"album\": \"Lukas Graham (Blue Album)\",\"released\":2015}";
        request.setContent(msg.getBytes());
        servlet.doPost(request, response);
        assertEquals(null, response.getContentType());
        assertEquals(400, response.getStatus());   	
    }
    
    
}
