package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

public class VagrantVmConfig {

	private List<VagrantPortForwarding> portForwardings;
	
	private PuppetProvisionerConfig puppetProvisionerConfig;
	
	public VagrantVmConfig(Iterable<VagrantPortForwarding> portForwardings, PuppetProvisionerConfig puppetProvisionerConfig) {
		this.portForwardings = new ArrayList<>();
		for(VagrantPortForwarding portForwarding : portForwardings) {
			this.portForwardings.add(portForwarding);
		}
		this.puppetProvisionerConfig = puppetProvisionerConfig;
	}
	
	public PuppetProvisionerConfig getPuppetProvisionerConfig() {
		return puppetProvisionerConfig;
	}
	
	public Iterable<VagrantPortForwarding> getPortForwardings() {
		return portForwardings;
	}
	
}
