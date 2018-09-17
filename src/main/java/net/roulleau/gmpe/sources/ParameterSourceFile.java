package net.roulleau.gmpe.sources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.Properties;
import java.util.logging.Logger;

public class ParameterSourceFile extends ParameterSourceJavaProperties {

	private static final Logger LOGGER = Logger.getLogger(ParameterSourceFile.class.getName());

	private boolean lateInit = false;

	public ParameterSourceFile(String filePath) {
		init(filePath);
	}

	public ParameterSourceFile() {
		lateInit = true;
	}

	@Override
	public void lateInit() {
		if (lateInit) {
			if (configurationFileName != null) {
				init(configurationFileName);
			} else {
				init(objectToFillclassName + ".properties");
			}
		}
	}

	private void init(String filePath) {

		Path currentWorkingDirConfFile = Paths.get(filePath).toAbsolutePath();
		if (Files.exists(currentWorkingDirConfFile)) {
			LOGGER.info("Found configuration file in the working directory");
			properties = new Properties();
			try (InputStream input = new FileInputStream(currentWorkingDirConfFile.toFile())) {
				properties.load(input);
			} catch (IOException | IllegalArgumentException e) {
				throw new InvalidParameterException(
						"Cannot read properties in the file " + filePath + " in current directory");
			}
			;
		}
	}

}
