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
	@Option(names = {"-h", "--help"}, usageHelp = true)
	boolean help;
	
	@Option(names = {"-c", "--class"}, arity="1", required = true, description = "Name of the class")
	String className;

	@Option(names = {"-o", "--output"}, arity="1", required = false, description = "Name of output report file")
	String reportName;

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
		if(loaded != null)
			report(loaded);
	}
	
	private Class<?> load()
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
	
	private void report(Class<?> custom)
	{
		Method[] methods = custom.getMethods();
		HashMap<Integer, ArrayList<Method>> map = new HashMap<Integer, ArrayList<Method>>();
		
		for(Method method : methods)
		{
			RunMe runme = method.getAnnotation(RunMe.class);
			
			
			if(runme == null)
			{
				map.get(0).add(method);
			} else {
				map.get(1).add(method);
				
				if(invoke(custom, method))
					map.get(2).add(method);
			}
		}
		
		write(map);
	}
	
	private boolean invoke(Class<?> custom, Method method)
	{
		try
		{
			Object obj = custom.newInstance();
			method.invoke(obj, method);
			return true;
		}catch (InvocationTargetException a) {
			return false;
		} catch (InstantiationException e) {
			return false;
		} catch (IllegalAccessException e) {
			return false;
		}
	}
	
	private void write(HashMap<Integer, ArrayList<Method>> map)
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
		}catch (IOException e) {
			e.printStackTrace();
		}
		

			
		BufferedWriter buffered = null;
		
		try
		{
			buffered = new BufferedWriter(fileWriter);
			
			buffered.write("Methods without RunMe:\n");
			for(Method method : map.get(0))
				buffered.write("\t" + method.getName() + "\n");
			
			buffered.write("Methods with RunMe:\n");
			for(Method method : map.get(1))
				buffered.write("\t" + method.getName() + "\n");
			
			buffered.write("Not invokable Methods:\n");
			for(Method method : map.get(2))
				buffered.write("\t" + method.getName() + "\n");
			
			buffered.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
				if(buffered != null)
					try {
						buffered.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			
		}
	}
}
