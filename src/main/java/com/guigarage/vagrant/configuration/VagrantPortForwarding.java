package com.guigarage.vagrant.configuration;


/**
 * This class configures a port forwarding for one Vagrant VM
 * @author hendrikebbers
 *
 */
public class VagrantPortForwarding {

	private int guestport;
	
	private int hostport;
	
	public VagrantPortForwarding(int guestport, int hostport) {
        this.guestport = guestport;
        this.hostport = hostport;
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
