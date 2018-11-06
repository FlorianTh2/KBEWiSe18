package runMeRunner;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;

public class AppTest
{
	App app;
	
	@Before
	public void setUp() throws Exception
	{
		app = new App();
	}
	
	@Test
	public void testShouldAcceptAllArguments()
	{
		String[] args = {"-c", "testclass", "-o", "testreport"};
		new CommandLine(app).parse(args);
		
		assert !app.className.isEmpty();
		assert !app.reportName.isEmpty();
	}

	@Test
	public void testShouldAcceptClassNameOnly()
	{
		String[] args = {"-c", "testclass"};
		new CommandLine(app).parse(args);
		
		assert !app.className.isEmpty();
		assert app.reportName == null;
		
		app.run();
		
		assert app.reportName.equals("report.txt");
	}
	
	
	// tests: readFile, erkennen aller @RunMe, 
	@Test
	public void test() {}
	
	@Test(expected = CommandLine.MissingParameterException.class)
	public void testShouldRejectMissingOptions()
	{
		String[] args = {};
		new CommandLine(app).parse(args);
	}
	
	/*
	 * NOTE: Not possible due to the possiblity that the class is named like a option.
	@Test//(expected = CommandLine.MissingParameterException.class)
	public void testShouldRejectMissingOptionValues()
	{
		String[] args = {"-c", "-o"};
		new CommandLine(app).parse(args);
	}
	*/
	
	@Test(expected = CommandLine.MissingParameterException.class)
	public void testShouldRejectMissingClassName()
	{
		String[] args = {"-c"};
		new CommandLine(app).parse(args);
	}
	
	@Test(expected = CommandLine.MissingParameterException.class)
	public void testShouldRejectMissingReportName()
	{
		String[] args = {"-c", "testclass", "-o"};
		new CommandLine(app).parse(args);
	}
	
	@Test(expected = CommandLine.MissingParameterException.class)
	public void testShouldRejectReportNameOnly()
	{
		String[] args = {"-o", "testreport"};
		new CommandLine(app).parse(args);
	}
}
