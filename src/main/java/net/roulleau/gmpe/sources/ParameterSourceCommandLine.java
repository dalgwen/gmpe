package net.roulleau.gmpe.sources;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import net.roulleau.gmpe.ParameterWithField;
import net.roulleau.gmpe.libs.joptsimple.OptionException;
import net.roulleau.gmpe.libs.joptsimple.OptionParser;
import net.roulleau.gmpe.libs.joptsimple.OptionSet;

public class ParameterSourceCommandLine extends ParameterFiller {

    private static final Logger LOGGER = Logger.getLogger(ParameterSourceFile.class.getName());
    
    public static final String CONFIGURATION_FILE_PARAMETER_NAME = "configurationfile";
    
    String[] args;
    
    private OptionSet optionSet;
    
	public OptionSet getOptionSet() {
        return optionSet;
    }
    
    public ParameterSourceCommandLine(String args[]) {
    	this.args = args;
    }

    @Override
	public void setParametersAvailable(List<ParameterWithField> parametersAvailable) {

        OptionParser parser = new OptionParser();
        
        // argument used by gmpe :
        parser.accepts(CONFIGURATION_FILE_PARAMETER_NAME).withOptionalArg();
    	
        // arguments in the pojo :
        LOGGER.info("Trying to parse configuration in program arguments");
        for (ParameterWithField parameterField : parametersAvailable) {
            String parameterName = parameterField.annotation.value();
            if (parameterField.field.getType() == Boolean.class) {
                parser.accepts(parameterName).withOptionalArg();
            }
            if (parameterField.field.getType() == List.class) {
                parser.accepts(parameterName).withRequiredArg().withValuesSeparatedBy(",");
            }
            else {
                parser.accepts(parameterName).withRequiredArg();
            }            
        }        
        optionSet = parser.parse(args);
        
        
	    // getting gmpe configuration from file and storing it for further use :
	    String configurationFilePath = (String) optionSet.valueOf(CONFIGURATION_FILE_PARAMETER_NAME);
	    setConfigurationFileName(configurationFilePath);

	}

	@Override
    public Optional<Object> getValue(String parameterName) {
        
        Object valueRaw;
        try {
            valueRaw = optionSet.valueOf(parameterName);
        }catch (OptionException oe) {
            valueRaw = optionSet.valuesOf(parameterName);
        }
        
        Optional<Object> parameterValue = Optional.ofNullable(valueRaw);
        if (parameterValue.isPresent()) {
            return parameterValue;
        }
        if (optionSet.has(parameterName)) {
            return Optional.of(Boolean.TRUE.toString());
        }
        return Optional.empty();
    }

}
