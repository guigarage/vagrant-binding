package com.guigarage.vagrant.configuration.builder;

import java.util.ArrayList;
import java.util.List;

import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantVmConfig;

public class VagrantEnvironmentConfigBuilder {

	private List<VagrantVmConfig> vmConfigs;

	public VagrantEnvironmentConfigBuilder() {
		vmConfigs = new ArrayList<>();
	}

	public VagrantEnvironmentConfigBuilder withVagrantVmConfig(
			VagrantVmConfig vmConfig) {
		this.vmConfigs.add(vmConfig);
		return this;
	}

	public VagrantEnvironmentConfig create() {
		return new VagrantEnvironmentConfig(vmConfigs);
	}
}
