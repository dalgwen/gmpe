package net.roulleau.gmpe;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.roulleau.gmpe.sources.ParameterFiller;

/**
 * License MIT
 * 
 * Get My Parameters Everywhere Simple tool to get parameters from several
 * sources (command line, property file on file system, property file on
 * classpath, system properties) It is for simple needs, and it
 * is simple to use !
 * 
 * @see ConfigurationTest for samples.
 * 
 * @author Gwendal ROULLEAU
 *
 */
public class Gmpe {

	public static <T> GmpeBuilder<T> fill(T pojo) {
		return new GmpeBuilder<T>(pojo);
	}
	
	public static <T> GmpeBuilder<T> fill(Class<T> pojo) {
		return new GmpeBuilder<T>(pojo);
	}

	public static class GmpeBuilder<T> {

		private T pojoToFill;

		public GmpeBuilder(T pojoToFill) {
			super();
			this.pojoToFill = pojoToFill;
		}

		public GmpeBuilder(Class<T> clazz) {
			try {
				pojoToFill = clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new ConfigurationException("Cannot create configuration object of class " + clazz.getName(), e);
			}
		}

		public T with(ParameterFiller... parameterFillers) {

			List<ParameterWithField> parametersWithField = getAllParametersAvailable();

			for (ParameterFiller parameterFiller : parameterFillers) {
				parameterFiller.setParametersAvailable(parametersWithField);
				parameterFiller.setObjectToFillclassName(pojoToFill.getClass().getName());
				parameterFiller.lateInit();
				withOnly(parameterFiller);
			}

			testMandatory();

			return pojoToFill;
		}

		private void testMandatory() {

			for (Field field : pojoToFill.getClass().getDeclaredFields()) {
				Parameter annotation = field.getAnnotation(Parameter.class);

				if (annotation != null) {

					boolean isMandatory = annotation.mandatory();
					String parameterName = annotation.value();

					Object value;
					try {
						value = field.get(pojoToFill);
						if (value == null && isMandatory) {
							throw new ConfigurationException("Parameter " + parameterName + " is mandatory");
						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
					}
				}
			}

		}

		private void withOnly(ParameterFiller parameterFiller) {
	        
	        for (Field field : pojoToFill.getClass().getDeclaredFields()) {
	            Parameter annotation = field.getAnnotation(Parameter.class);
	            
	            if (annotation != null) {
	                String parameterName = annotation.value();
	                Optional<Object> parameterValue = parameterFiller.getValue(parameterName);
	                if (parameterValue.isPresent()) {
	                    Object paramaterValuedConverted = convertValue(parameterValue.get(), field.getType()); 
	                    try {
	                    	field.setAccessible(true);
	                        field.set(pojoToFill, paramaterValuedConverted);
	                    } catch (IllegalArgumentException | IllegalAccessException e) {
	                        throw new ConfigurationException("Cannot access parameter " + parameterName, e);
	                    }
	                }
	            }
	        }
		}

		public List<ParameterWithField> getAllParametersAvailable() {
			return Arrays.stream(pojoToFill.getClass().getDeclaredFields())
					.map(field -> new ParameterWithField(field.getAnnotation(Parameter.class), field))
					.filter(field -> field.annotation != null).collect(Collectors.toList());
		}

		private <Y> Y convertValue(Object parameterValue, Class<Y> type) throws ConfigurationException {

			if (type == String.class) {
				return type.cast(parameterValue.toString());
			} else if (type == Integer.class) {
				return type.cast(Integer.parseInt(parameterValue.toString()));
			} else if (type == Boolean.class) {
				return type.cast(Boolean.parseBoolean(parameterValue.toString()));
			} else if (type == List.class) {
				if (parameterValue instanceof List) {
					return type.cast(parameterValue);
				} else {
					return type.cast(Arrays.asList(parameterValue.toString().split(",")));
				}
			} else {
				throw new ConfigurationException("Runtime error : type " + type.toString() + " not accepted");
			}
		}
	}

}
