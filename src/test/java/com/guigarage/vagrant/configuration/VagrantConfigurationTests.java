package com.guigarage.vagrant.configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.VagrantEnvironment;
import com.guigarage.vagrant.VagrantVm;

public class VagrantConfigurationTests {

	private File createTempDir() {
		File mainTempDir = FileUtils.getTempDirectory();
		File tempDir = new File(mainTempDir, "vagrant-"
				+ UUID.randomUUID().toString());
		tempDir.mkdirs();
		return tempDir;
	}
	
	@Test
	public void testSimpleConfiguration() {
		try {
			VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(null);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("");
		}

		try {
			ArrayList<VagrantVmConfig> vmConfigs = new ArrayList<>();
			VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(
					vmConfigs);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("");
		}
		
		try {
			ArrayList<VagrantVmConfig> vmConfigs = new ArrayList<>();
			vmConfigs.add(new VagrantVmConfig("unitTestVm", null, null, null, null, null));
			VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(
					vmConfigs);
			Vagrant vagrant = new Vagrant(true);
			File tempDir = createTempDir();
			VagrantEnvironment env = vagrant.createEnvironment(tempDir, config);
			VagrantVm vm = env.getAllVms().iterator().next();
			Assert.assertEquals("unitTestVm", vm.getName());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("");
		}
	}
}
