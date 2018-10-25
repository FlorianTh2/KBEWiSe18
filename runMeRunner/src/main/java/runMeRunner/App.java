package runMeRunner;

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

	}

}
