package com.guigarage.vagrant.configuration.builder;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.guigarage.vagrant.configuration.PuppetProvisionerConfig;
import com.guigarage.vagrant.configuration.VagrantPortForwarding;
import com.guigarage.vagrant.configuration.VagrantVmConfig;

public class VagrantVmConfigBuilder {

private List<VagrantPortForwarding> portForwardings;
	
	private PuppetProvisionerConfig puppetProvisionerConfig;
	
	private String name;

	private String host;
	
	private String boxName;
	
	private URL boxUrl;
	
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
	
	public VagrantVmConfigBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public VagrantVmConfigBuilder withHost(String host) {
		this.host = host;
		return this;
	}
	
	public VagrantVmConfigBuilder withBoxName(String boxName) {
		this.boxName = boxName;
		return this;
	}
	
	public VagrantVmConfigBuilder withBoxUrl(URL boxUrl) {
		this.boxUrl = boxUrl;
		return this;
	}
	
	public VagrantVmConfig create() {
		return new VagrantVmConfig(name, host, boxName, boxUrl, portForwardings, puppetProvisionerConfig);
	}
}
