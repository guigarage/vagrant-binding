package com.guigarage.vagrant.configuration.builder;

import java.util.ArrayList;
import java.util.List;

import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantVmConfig;

public class VagrantEnvironmentConfigBuilder {

	private String name;

	private List<VagrantVmConfig> vmConfigs;

	private String host;

	public VagrantEnvironmentConfigBuilder() {
		vmConfigs = new ArrayList<>();
	}

	public VagrantEnvironmentConfigBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public VagrantEnvironmentConfigBuilder withHost(String host) {
		this.host = host;
		return this;
	}
	
	public VagrantEnvironmentConfigBuilder withVagrantVmConfig(VagrantVmConfig vmConfig) {
		this.vmConfigs.add(vmConfig);
		return this;
	}
	
	public VagrantEnvironmentConfig create() {
		return new VagrantEnvironmentConfig(name, host, vmConfigs);
	}
}
