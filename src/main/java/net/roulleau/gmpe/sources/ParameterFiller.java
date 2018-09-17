package net.roulleau.gmpe.sources;

import java.util.List;
import java.util.Optional;

import net.roulleau.gmpe.ParameterWithField;

public abstract class ParameterFiller {

    List<ParameterWithField> parametersAvailable;
    
    String objectToFillclassName;
    
    static String configurationFileName;

	public abstract Optional<Object> getValue(String parameterName);

	public static void setConfigurationFileName(String configurationFileName) {
		ParameterFiller.configurationFileName = configurationFileName;
	}

	public void setParametersAvailable(List<ParameterWithField> parametersAvailable) {
		this.parametersAvailable = parametersAvailable;
	}

    public void setObjectToFillclassName(String objectToFillclassName) {
		this.objectToFillclassName = objectToFillclassName;
	}

	public void lateInit() {
	}

}
