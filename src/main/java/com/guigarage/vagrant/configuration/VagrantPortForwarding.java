package com.guigarage.vagrant.configuration;

public class VagrantPortForwarding {

	private int guestport;
	
	private int hostport;
	
	public VagrantPortForwarding(int guestport, int hostport) {
		this.guestport = guestport;
		this.hostport = hostport;
	}
	
	public int getGuestport() {
		return guestport;
	}
	
	public int getHostport() {
		return hostport;
	}
}
