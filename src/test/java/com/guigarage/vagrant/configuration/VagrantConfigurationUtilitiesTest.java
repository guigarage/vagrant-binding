package com.guigarage.vagrant.configuration;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

public class VagrantConfigurationUtilitiesTest {

	private void assertTrimEquals(String expected, String actual) {
		if (expected == null && actual == null) {
			return;
		}
		Assert.assertEquals(expected.trim(), actual.trim());
	}

	@Test
	public void testConfiguration() {
		try {
			VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(null);
			String vagrantFileContent = VagrantConfigurationUtilities
					.createVagrantFileContent(config);
			String expected = "Vagrant::Config.run do |config|" + "\n" + "end";
			assertTrimEquals(expected, vagrantFileContent);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("");
		}
		
		try {
			ArrayList<VagrantVmConfig> vmConfigs = new ArrayList<>();
			vmConfigs.add(new VagrantVmConfig("unitTest", null, null, "lucid32", null, null, null, false));
			VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(vmConfigs);
			String vagrantFileContent = VagrantConfigurationUtilities
					.createVagrantFileContent(config);
			String expected = "Vagrant::Config.run do |config|" + "\n" +
			"config.vm.define :unitTest do |unitTest_config|" + "\n" +
			"unitTest_config.vm.box = \"lucid32\""+ "\n" +
			"end"+ "\n" +
			"end";
			assertTrimEquals(expected, vagrantFileContent);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("");
		}
	}
}
