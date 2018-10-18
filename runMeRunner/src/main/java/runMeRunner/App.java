package runMeRunner;

import picocli.CommandLine;
import picocli.CommandLine.Option;

public class App implements Runnable
{

	@Option(names = { "-c", "--class" }, required = true, description = "Name of the class")
	String className;

	@Option(names = { "-o", "--output" }, required = false, description = "Name of output report file")
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

	}

}
