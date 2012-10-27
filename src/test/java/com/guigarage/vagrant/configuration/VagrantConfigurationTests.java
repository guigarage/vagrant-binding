package com.guigarage.vagrant.configuration;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

public class VagrantConfigurationTests {

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
	}
}
