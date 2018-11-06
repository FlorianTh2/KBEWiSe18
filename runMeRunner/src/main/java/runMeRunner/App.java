package runMeRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name="runMeRunner", footer="Version 1.0 2018")
public class App implements Runnable
{
	static class InvokeResult
	{
		public boolean success;
		public String methodName;
		public String exceptionName;
		
		public InvokeResult(boolean success, String methodName, String exceptionName)
		{
			this.success = success;
			this.methodName = methodName;
			this.exceptionName = exceptionName;
		}
		
		public static InvokeResult success(Method method)
		{
			return new InvokeResult(true, method.getName(), null);
		}
		
		public static InvokeResult failure(Method method, Exception exception)
		{
			return new InvokeResult(false, method.getName(), exception.getClass().getName());
		}
		
		public static InvokeResult ignore(Method method)
		{
			return new InvokeResult(false, method.getName(), null);
		}
	}
	
	
	@Option(names = {"-h", "--help"}, usageHelp = true)
	boolean help;
	
	@Option(names = {"-c", "--class"}, arity="1", required = true, description = "Name of the class")
	String className;

	@Option(names = {"-o", "--output"}, arity="1", required = false, description = "Name of output report file")
	String reportName;
	
	private int channels = 3;

	public static void main(String[] args)
	{
		CommandLine.run(new App(), args);
	}
	
	public void run()
	{
		if (reportName == null)
			reportName = "report.txt";

		System.out.println("Input class: " + className);
		System.out.println("Report: " + reportName);
		
		Class<?> loaded = load();
		
		if(loaded == null)
			return;
		
		write(report(loaded));
	}
	
	Class<?> load()
	{
		try
		{
			Class<?> classImported = Class.forName(className);
			return classImported;
		} catch (ClassNotFoundException e) {
			System.out.println("Class cannot be loaded.");
			return null;
		}
	}
	
	InvokeResult invoke(Class<?> custom, Method method)
	{
		try
		{
			Object obj = custom.newInstance();
			method.setAccessible(true);
			method.invoke(obj);
			return InvokeResult.success(method);
		} catch (InvocationTargetException e) {
			return InvokeResult.failure(method, e);
		} catch (InstantiationException e) {
			return InvokeResult.failure(method, e);
		} catch (IllegalAccessException e) {
			return InvokeResult.failure(method, e);
		} catch (IllegalArgumentException e) {
			return InvokeResult.failure(method, e);
		}
	}
	
	HashMap<Integer, ArrayList<InvokeResult>> report(Class<?> custom)
	{
		Method[] methods = custom.getDeclaredMethods();
		HashMap<Integer, ArrayList<InvokeResult>> map = new HashMap<Integer, ArrayList<InvokeResult>>();
		
		for(int i = 0; i < channels; ++i)
			map.put(i, new ArrayList<InvokeResult>());
		
		for(Method method : methods)
		{
			RunMe runme = method.getAnnotation(RunMe.class);
			
			
			if(runme == null)
			{
				map.get(0).add(InvokeResult.ignore(method));
			} else {
				map.get(1).add(InvokeResult.ignore(method));
				
				InvokeResult result = invoke(custom, method);
				if(!result.success)
					map.get(2).add(result);
			}
		}
		
		return map;
	}
	
	private void write(HashMap<Integer, ArrayList<InvokeResult>> map)
	{
		File file = new File(reportName);
		FileWriter fileWriter = null;
		
		try {
			if(file.exists())
			{
					fileWriter = new FileWriter(file, false);
			} else {
					file.createNewFile();

					fileWriter = new FileWriter(file);
				 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		

			
		BufferedWriter buffered = null;
		
		try
		{
			buffered = new BufferedWriter(fileWriter);
			
			buffered.write("Methods without RunMe:\n");
			for(InvokeResult result : map.get(0))
				buffered.write("\t" + result.methodName + "\n");
			
			buffered.write("Methods with RunMe:\n");
			for(InvokeResult result : map.get(1))
				buffered.write("\t" + result.methodName + "\n");
			
			buffered.write("Not invokable Methods:\n");
			for(InvokeResult result : map.get(2))
				buffered.write("\t" + result.methodName + ":" + result.exceptionName + "\n");
			
			buffered.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(buffered != null)
				try {
					buffered.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			
		}
	}
}
