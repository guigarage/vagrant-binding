package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

public class VagrantEnvironmentConfig {

	private List<VagrantVmConfig> vmConfigs;
	
	public VagrantEnvironmentConfig(Iterable<VagrantVmConfig> vmConfigs) {
		this.vmConfigs = new ArrayList<>();
		for(VagrantVmConfig vagrantVmConfig : vmConfigs) {
			this.vmConfigs.add(vagrantVmConfig);
		}
	}
	
	public Iterable<VagrantVmConfig> getVmConfigs() {
		return vmConfigs;
	}
}
