package net.roulleau.gmpe;

import java.lang.reflect.Field;

public class ParameterWithField {
    public Parameter annotation;
    public Field field;
    
    public ParameterWithField(Parameter annotation, Field field) {
        this.annotation = annotation;
        this.field = field;
    }
}
