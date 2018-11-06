package runMeRunner;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import runMeRunner.App.InvokeResult;

public class AppTest
{
	App app;
	
	public static class Invokable
	{
		@RunMe
		public void methodA()
		{
			int a = 1 + 1;
		}
		
		@RunMe
		public void methodB()
		{
			int b = 2 + 2;
		}
		
		@RunMe
		public void methodC()
		{
			int c = 3 + 3;
		}
	}
	
	
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
	
	@Test
	public void testShouldInvokeMethod()
	{
		Class<?> invokable;
		
		try
		{
			invokable = Invokable.class;
			assert app.invoke(invokable, invokable.getMethod("methodA")).success;
		} catch (NoSuchMethodException e) {
			assert false;
		}
	}
	
	@Test
	public void testShouldNotInvokeMethod()
	{
		Class<?> invokable;
		
		try
		{
			invokable = App.class;
			assert !app.invoke(invokable, Invokable.class.getMethod("methodC")).success;
		} catch (NoSuchMethodException e) {
			assert false;
		}
	}
	
	@Test
	public void testShouldCreateReportMap()
	{
		Class<?> invokable;
		
		invokable = Invokable.class;
		HashMap<Integer, ArrayList<InvokeResult>> map = app.report(invokable);
		assert map != null;
		assert map.size() == 3;
		assert map.get(1).size() == 3;
	}
	
	@Test
	public void testShouldLoad() {
		String[] args = {"-c", "runMeRunner.TestAnnotationClass", "-o", "testreport"};
		new CommandLine(app).parse(args);
		assertNotNull(app.load()); 	
	}
}
