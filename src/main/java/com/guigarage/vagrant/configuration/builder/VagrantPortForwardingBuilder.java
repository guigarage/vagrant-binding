package com.guigarage.vagrant.configuration.builder;

import com.guigarage.vagrant.configuration.VagrantPortForwarding;

public class VagrantPortForwardingBuilder {

	private int guestport;

	private int hostport;

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
	
	public VagrantPortForwarding create() {
		return new VagrantPortForwarding(guestport, hostport);
	}
}
