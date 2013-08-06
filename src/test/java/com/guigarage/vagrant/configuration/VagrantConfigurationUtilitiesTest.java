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
            String expected = "VAGRANTFILE_API_VERSION = \"2\"\n" +
                              "Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|\n" +
                              "end";
			assertTrimEquals(expected, vagrantFileContent);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		try {
			ArrayList<VagrantVmConfig> vmConfigs = new ArrayList<VagrantVmConfig>();
			vmConfigs.add(new VagrantVmConfig("unitTest", null, null, "lucid32", null, null, null, null, null, false, false, null));
			VagrantEnvironmentConfig config = new VagrantEnvironmentConfig(vmConfigs);
			String vagrantFileContent = VagrantConfigurationUtilities
					.createVagrantFileContent(config);
            String expected = "VAGRANTFILE_API_VERSION = \"2\"\n" +
                    "Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|\n" +
                    "config.vm.define :unitTest do |unitTest_config|" + "\n" +
                    "unitTest_config.vm.box = \"lucid32\""+ "\n" +
                    "end"+ "\n" +
                    "end";
			assertTrimEquals(expected, vagrantFileContent);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
}
