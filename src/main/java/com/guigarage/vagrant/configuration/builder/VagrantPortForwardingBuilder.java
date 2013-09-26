package com.guigarage.vagrant.configuration.builder;

import com.guigarage.vagrant.configuration.VagrantPortForwarding;
import com.guigarage.vagrant.configuration.builder.util.VagrantBuilderException;

public class VagrantPortForwardingBuilder {

	private int guestport;

	private int hostport;

	private String name;
	
	public VagrantPortForwardingBuilder() {
	}
	
	public static VagrantPortForwardingBuilder create() {
		return new VagrantPortForwardingBuilder();
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
	
	public VagrantPortForwarding build() {
		if(guestport < 0) {
			throw new VagrantBuilderException("no guestport defined");
		}
		if(hostport < 0) {
			throw new VagrantBuilderException("no hostport defined");
		}
		return new VagrantPortForwarding(guestport, hostport);
	}
}
