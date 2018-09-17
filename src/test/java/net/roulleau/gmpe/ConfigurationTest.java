package net.roulleau.gmpe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.roulleau.gmpe.sources.ParameterSourceClassPath;
import net.roulleau.gmpe.sources.ParameterSourceFile;
import net.roulleau.gmpe.sources.ParameterSourceSystemProperties;
import net.roulleau.gmpe.sources.ParameterSourceCommandLine;

public class ConfigurationTest {

	@Parameter(value = "param1", mandatory = true)
	Integer param1;
	
	@Parameter("params.list")
	List<String> paramsList;
	
	@Before
	public void reinit() {
		param1=  null;
		paramsList = null;
	}
	
    @Test
    public void testCommandLineList() {
        String[] args = new String[] {"--params.list" , "013245678,087654321", "--param1", "12"};
    	Gmpe.fill(this).with(new ParameterSourceCommandLine(args));
        assertThat(paramsList).containsOnly("013245678","087654321");
    }
	
    @Test
    public void testExternalFile() {
    	String[] args = new String[] {"--" + ParameterSourceCommandLine.CONFIGURATION_FILE_PARAMETER_NAME, "target/test-classes/testconfigurationfile.conf"};
    	Gmpe.fill(this).with(new ParameterSourceCommandLine(args), new ParameterSourceFile());
        assertThat(param1).isEqualTo(8282);
    }
    
    @Test
    public void testExternalFileListParameter()  {
        String[] args = new String[] {"--" + ParameterSourceCommandLine.CONFIGURATION_FILE_PARAMETER_NAME, "target/test-classes/testconfigurationfile.conf"};
    	Gmpe.fill(this).with(new ParameterSourceCommandLine(args), new ParameterSourceFile());
        assertThat(paramsList).containsOnly("012345678","087654321");
    }

    @Test
    public void testDefaultClassPathFile() {
    	Gmpe.fill(this).with(new ParameterSourceClassPath());
        assertThat(param1).isEqualTo(42);
    }
    
    @Test
    public void testClassPathFile() {
    	Gmpe.fill(this).with(new ParameterSourceClassPath("testconfigurationfile.conf"));
        assertThat(param1).isEqualTo(8282);
        assertThat(paramsList).containsOnly("012345678","087654321");
    }
    
    @Test
    public void testSystemProperties() {
    	System.setProperty("param1", "1066");
    	System.setProperty("params.list", "1066,1067");
    	Gmpe.fill(this).with(new ParameterSourceSystemProperties());
        assertThat(param1).isEqualTo(1066);
        assertThat(paramsList).containsOnly("1066","1067");
    }
    
    @Test
    public void testMandatoryParameterMissing() {
        String[] args = new String[] {"--params.list" , "013245678,087654321" };
    	
        try {
    		Gmpe.fill(this).with(new ParameterSourceCommandLine(args));
    		fail("Should have failed because of a mandatory parameter is missing");
    	} catch (ConfigurationException conf) {
    	}
    }
    
    @Test
    public void testInOtherPojoWithPrivateMember() {
    	String[] args = new String[] {"--param1" , "my string" };
		OtherPojo pojoFilled = Gmpe.fill(OtherPojo.class).with(new ParameterSourceCommandLine(args));
		assertThat(pojoFilled.getMyPrivateParam()).isEqualTo("my string");
    }
    
}
