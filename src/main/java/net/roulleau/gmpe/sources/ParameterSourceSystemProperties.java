package net.roulleau.gmpe.sources;

import java.util.Optional;

public class ParameterSourceSystemProperties extends ParameterFiller {

	@Override
	public Optional<Object> getValue(String parameterName) {
		return Optional.ofNullable(System.getProperty(parameterName));
	}

}
