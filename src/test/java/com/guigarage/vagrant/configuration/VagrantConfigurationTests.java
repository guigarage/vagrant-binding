package com.guigarage.vagrant.configuration;

import java.io.File;
import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

import com.guigarage.vagrant.Vagrant;
import com.guigarage.vagrant.VagrantTestUtils;
import com.guigarage.vagrant.model.VagrantEnvironment;
import com.guigarage.vagrant.model.VagrantVm;

public class VagrantConfigurationTests {
	
	@Test
	public void testSimpleConfiguration() {
		try {
			new VagrantEnvironmentConfig(null);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("");
		}

		try {
			ArrayList<VagrantVmConfig> vmConfigs = new ArrayList<VagrantVmConfig>();
			new VagrantEnvironmentConfig(
					vmConfigs);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("");
		}
		
		try {
			ArrayList<VagrantVmConfig> vmConfigs = new ArrayList<VagrantVmConfig>();
			vmConfigs.add(new VagrantVmConfig("unitTestVm", null, null, null, null, null, null, null, null, false, true, null));
			VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(
					vmConfigs);
			Vagrant vagrant = new Vagrant(Vagrant.LogLevel.DEBUG);
			File tempDir = VagrantTestUtils.createTempDir();
			VagrantEnvironment env = vagrant.createEnvironment(tempDir, config);
			VagrantVm vm = env.getAllVms().iterator().next();
			Assert.assertEquals("unitTestVm", vm.getName());
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("");
		}
	}
}
