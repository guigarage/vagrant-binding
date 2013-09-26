package com.guigarage.vagrant;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantVmConfig;
import com.guigarage.vagrant.configuration.builder.VagrantEnvironmentConfigBuilder;
import com.guigarage.vagrant.configuration.builder.VagrantVmConfigBuilder;
import com.guigarage.vagrant.model.VagrantEnvironment;
import com.guigarage.vagrant.util.VagrantUtils;

public class NewBoxUsage {


    private Vagrant vagrant;

    @After
    public void tearDown() throws Exception {
        if (vagrant != null) {
            vagrant.shutdown();
        }

    }

    @Before
    public void setUp() throws Exception {
        vagrant = new Vagrant(Vagrant.LogLevel.DEBUG);
    }


    @Test
	public void initWithNewBox() {
		String boxName = "unittestbox" + System.currentTimeMillis();

		File vagrantTempDir = VagrantTestUtils.createTempDir();
		VagrantVmConfig vmConfig = new VagrantVmConfigBuilder()
				.withBoxName(boxName)
				.withBoxUrl(VagrantUtils.getInstance().getLucid32Url())
				.withName("unitestvm").build();
		VagrantEnvironmentConfig envConfig = new VagrantEnvironmentConfigBuilder()
				.withVagrantVmConfig(vmConfig).build();

		VagrantEnvironment environment = null;

		try {
			environment = vagrant.createEnvironment(vagrantTempDir, envConfig);
			environment.up();
		} catch (Exception exception) {
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		} finally {
			if(environment != null) {
				try {
					environment.removeBox(boxName);
					environment.destroy();
					try {
						FileUtils.forceDelete(vagrantTempDir);
					} catch (Exception e) {
						try {
							FileUtils.forceDeleteOnExit(vagrantTempDir);
						} catch (IOException e1) {
							System.err.println("Can not delete vagrantfolder: "
									+ vagrantTempDir);
						}
					}
				} catch (Exception removeException) {
					removeException.printStackTrace();
					
					String boxPath = null;
					try {
						boxPath = environment.getBoxesPath();
					} catch (Exception e) {
						//...
					}
					try {
						FileUtils.forceDelete(vagrantTempDir);
					} catch (Exception e) {
						try {
							FileUtils.forceDeleteOnExit(vagrantTempDir);
						} catch (IOException e1) {
							System.err.println("Can not delete vagrantfolder: "
									+ vagrantTempDir);
						}
					}
					if(boxPath != null) {
						Assert.fail("Can not remove box " + boxName
								+ "! Please remove it manually. (BoxPath:" + boxPath + ")");
					} else {
						Assert.fail("Can not remove box " + boxName
								+ "! Please remove it manually.");
					}
				}
			}
		}

	}
}
