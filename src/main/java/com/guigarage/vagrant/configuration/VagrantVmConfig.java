package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

public class VagrantVmConfig {

	private List<VagrantPortForwarding> portForwardings;
	
	private PuppetProvisionerConfig puppetProvisionerConfig;
	
	public VagrantVmConfig() {
		portForwardings = new ArrayList<>();
	}
	
	
}
