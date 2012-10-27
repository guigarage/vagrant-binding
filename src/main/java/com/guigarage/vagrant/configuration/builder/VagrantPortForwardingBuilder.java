package com.guigarage.vagrant.configuration.builder;

import com.guigarage.vagrant.configuration.VagrantPortForwarding;

public class VagrantPortForwardingBuilder {

	private int guestport;

	private int hostport;

	private String name;
	
	public VagrantPortForwardingBuilder() {
	}
	
	public VagrantPortForwardingBuilder withHostPort(int hostport) {
		this.hostport = hostport;
		return this;
	}

	public VagrantPortForwardingBuilder withGuestPort(int guestport) {
		this.guestport = guestport;
		return this;
	}
	
	public VagrantPortForwardingBuilder withName(String name) {
		this.name = name;
		return this;
	}
	
	public VagrantPortForwarding create() {
		return new VagrantPortForwarding(name, guestport, hostport);
	}
}
