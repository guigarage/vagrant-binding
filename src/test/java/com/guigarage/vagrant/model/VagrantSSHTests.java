package com.guigarage.vagrant.model;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.VagrantTestUtils;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantVmConfig;
import com.guigarage.vagrant.configuration.builder.VagrantEnvironmentConfigBuilder;
import com.guigarage.vagrant.configuration.builder.VagrantVmConfigBuilder;

public class VagrantSSHTests {


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
	public void testSSHExecute() {
		File vagrantTempDir = VagrantTestUtils.createTempDir();
		VagrantVmConfig vmConfig = new VagrantVmConfigBuilder()
				.withLucid32Box()
				.withName("unitestvm").build();
		VagrantEnvironmentConfig envConfig = new VagrantEnvironmentConfigBuilder()
				.withVagrantVmConfig(vmConfig).build();

		VagrantEnvironment environment = null;

		try {
			environment = vagrant.createEnvironment(vagrantTempDir, envConfig);
			environment.up();
			try {
				VagrantSSHConnection connection = environment.getAllVms().iterator().next().createConnection();
				connection.execute("touch /vagrant/testfile1", true);
				File touchedFile = new File(vagrantTempDir, "testfile1");
				Assert.assertEquals(true, touchedFile.exists());
			} finally {
				environment.destroy();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		} finally {
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
		}
	}
	
	@Test
	public void testSSHUpload() {
		File vagrantTempDir = VagrantTestUtils.createTempDir();
		VagrantVmConfig vmConfig = new VagrantVmConfigBuilder()
				.withLucid32Box()
				.withName("unitestvm").build();
		VagrantEnvironmentConfig envConfig = new VagrantEnvironmentConfigBuilder()
				.withVagrantVmConfig(vmConfig).build();

		VagrantEnvironment environment = null;

		try {
			environment = vagrant.createEnvironment(vagrantTempDir, envConfig);
			environment.up();
			try {
				VagrantSSHConnection connection = environment.getAllVms().iterator().next().createConnection();
				File tempFile = File.createTempFile("vagrant", "test");
				try {
					tempFile.createNewFile();
					FileUtils.write(tempFile, "Vagrant-Unit-Test", false);
					connection.upload(tempFile.getAbsolutePath(), "/vagrant/testfile1");
				} finally {
					try {
						FileUtils.forceDelete(tempFile);
					} catch (Exception e) {
						try {
							FileUtils.forceDeleteOnExit(tempFile);
						} catch (IOException e1) {
							System.err.println("Can not delete tempfile: "
									+ tempFile);
						}
					}
				}
				
				File createdFile = new File(vagrantTempDir, "testfile1");
				Assert.assertEquals(true, createdFile.exists());
				Assert.assertEquals("Vagrant-Unit-Test", FileUtils.readFileToString(createdFile));
			} finally {
				environment.destroy();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			Assert.fail(exception.getMessage());
		} finally {
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
		}
	}
}
