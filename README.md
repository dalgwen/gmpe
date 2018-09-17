# gmpe
Get My Parameter Everywhere - simple library for filling annotated POJO with parameter from different sources (property file, command line, etc.)

# usage
Annotate your field in your POJO with @Parameter :
~~~~
	@Parameter(value = "param1")
	Integer param1;
~~~~
You can use String, Integer, Boolean types, as well as List :
~~~~
	@Parameter("params.list")
	List<String> paramsList;
~~~~

Then "fill" your POJO with values from a source :
~~~~
  Gmpe.fill(myPojo).with(new ParametersSourceCommandLine(args));
~~~~
Other sources include file, file in classpath, or system properties.

Your POJO is now ready to use with your parameters injected.

Special thanks to joptsimple (I included the code from joptsimple for the command line parsing functionality)
