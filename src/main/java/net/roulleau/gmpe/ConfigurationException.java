package net.roulleau.gmpe;

public class ConfigurationException extends RuntimeException {

    private static final long serialVersionUID = -4364481747354094134L;
    
    public ConfigurationException(String message, Exception e) {
        super(message, e);
    }
    
    public ConfigurationException(String message) {
        super(message);
    }

}
