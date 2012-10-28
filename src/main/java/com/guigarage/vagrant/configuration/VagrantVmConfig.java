package com.guigarage.vagrant.configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class VagrantVmConfig {

	private List<VagrantPortForwarding> portForwardings;
	
	private PuppetProvisionerConfig puppetProvisionerConfig;
	
	private String name;
	
	private String ip;

	private String boxName;
	
	private URL boxUrl;

	private String hostName;
	
	private boolean guiMode;
	
	public VagrantVmConfig(String name, String ip, String hostName, String boxName, URL boxUrl, Iterable<VagrantPortForwarding> portForwardings, PuppetProvisionerConfig puppetProvisionerConfig, boolean guiMode) {
		this.portForwardings = new ArrayList<>();
		if(portForwardings != null) {
			for(VagrantPortForwarding portForwarding : portForwardings) {
				this.portForwardings.add(portForwarding);
			}
		}
		this.puppetProvisionerConfig = puppetProvisionerConfig;
		this.ip = ip;
		this.name = name;
		this.boxName = boxName;
		this.boxUrl = boxUrl;
		this.hostName = hostName;
		this.guiMode = guiMode;
	}
	
	public String getHostName() {
		return hostName;
	}
	
	public boolean isGuiMode() {
		return guiMode;
	}
	
	public String getBoxName() {
		return boxName;
	}
	
	public URL getBoxUrl() {
		return boxUrl;
	}
	
	public PuppetProvisionerConfig getPuppetProvisionerConfig() {
		return puppetProvisionerConfig;
	}
	
	public Iterable<VagrantPortForwarding> getPortForwardings() {
		return portForwardings;
	}
	
	public String getName() {
		return name;
	}
	
	public String getIp() {
		return ip;
	}
}
