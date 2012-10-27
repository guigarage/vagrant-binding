package com.guigarage.vagrant.configuration;


public class VagrantPortForwarding {

	private int guestport;
	
	private int hostport;
	
	private String name;
	
	public VagrantPortForwarding(String name, int guestport, int hostport) {
		this.guestport = guestport;
		this.hostport = hostport;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getGuestport() {
		return guestport;
	}
	
	public int getHostport() {
		return hostport;
	}
}
