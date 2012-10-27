package com.guigarage.vagrant.configuration.builder;

import java.util.ArrayList;
import java.util.List;

import com.guigarage.vagrant.configuration.PuppetProvisionerConfig;
import com.guigarage.vagrant.configuration.VagrantPortForwarding;
import com.guigarage.vagrant.configuration.VagrantVmConfig;

public class VagrantVmConfigBuilder {

private List<VagrantPortForwarding> portForwardings;
	
	private PuppetProvisionerConfig puppetProvisionerConfig;
	
	public VagrantVmConfigBuilder() {
		portForwardings = new ArrayList<>();
	}
	
	public VagrantVmConfigBuilder withPuppetProvisionerConfig(PuppetProvisionerConfig puppetProvisionerConfig) {
		this.puppetProvisionerConfig = puppetProvisionerConfig;
		return this;
	}
	
	public VagrantVmConfigBuilder withVagrantPortForwarding(VagrantPortForwarding portForwarding) {
		this.portForwardings.add(portForwarding);
		return this;
	}
	
	public VagrantVmConfig create() {
		return new VagrantVmConfig(portForwardings, puppetProvisionerConfig);
	}
}
