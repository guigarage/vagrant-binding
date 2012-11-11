package com.guigarage.vagrant.configuration;


/**
 * This class configures a port forwarding for one Vagrant VM
 * @author hendrikebbers
 *
 */
public class VagrantPortForwarding {

	private int guestport;
	
	private int hostport;
	
	private String name;
	
	/**
	 * Constructor for the port forwarding
	 * @param name the name of the port forwarding. This is optional and used by Vagrant internally
	 * @param guestport the guestport
	 * @param hostport the hostport
	 */
	public VagrantPortForwarding(String name, int guestport, int hostport) {
		this.guestport = guestport;
		this.hostport = hostport;
		this.name = name;
	}
	
	/**
	 * The name of the port forwarding. This is optional and used by Vagrant internally
	 * @return name of the port forwarding
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the guestport.
	 * @return the guestport
	 */
	public int getGuestport() {
		return guestport;
	}
	
	/**
	 * Returns the hostport
	 * @return the hostport
	 */
	public int getHostport() {
		return hostport;
	}
}
