package net.roulleau.gmpe.sources;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.Properties;
import java.util.logging.Logger;

public class ParameterSourceClassPath extends ParameterSourceJavaProperties {

    private static final Logger LOGGER = Logger.getLogger(ParameterSourceFile.class.getName());
    
    private boolean lateInit = false;
    
    public ParameterSourceClassPath(String fileName) {
    	init(fileName);
    }
    
    public ParameterSourceClassPath() {
    	lateInit = true;
    }
    
    @Override
    public void lateInit() {
    	if (lateInit) {
    		init(objectToFillclassName + ".properties");
    	}
    }

    private void init(String fileName) {
        
    	properties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is != null) {
                LOGGER.info("Found configuration file in the classpath");
                properties.load(is);
            }
        } catch (IOException | IllegalArgumentException e) {
            throw new InvalidParameterException("Cannot read " + fileName + " from classpath");
        };
    }
    
}
