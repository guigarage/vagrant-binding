package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

public class VagrantEnvironmentConfig {

	private String name;
	
	private List<VagrantVmConfig> vmConfigs;
	
	private String host;
	
	public VagrantEnvironmentConfig(String name, String host, Iterable<VagrantVmConfig> vmConfigs) {
		this.name = name;
		this.host = host;
		this.vmConfigs = new ArrayList<>();
		for(VagrantVmConfig vagrantVmConfig : vmConfigs) {
			this.vmConfigs.add(vagrantVmConfig);
		}
	}
	
	public String getHost() {
		return host;
	}
	
	public String getName() {
		return name;
	}
	
	public Iterable<VagrantVmConfig> getVmConfigs() {
		return vmConfigs;
	}
}
