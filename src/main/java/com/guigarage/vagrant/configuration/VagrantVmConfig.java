package com.guigarage.vagrant.configuration;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A configuration class that can be used to define and create a VM in Vagrant.
 * @author hendrikebbers
 *
 */
public class VagrantVmConfig {

	private List<VagrantPortForwarding> portForwardings;

    private List<VagrantSyncFolder> syncFolders;
	
	private PuppetProvisionerConfig puppetProvisionerConfig;
	
	private String name;
	
	private String ip;

	private String boxName;
	
	private URL boxUrl;

	private String hostName;
	
	private boolean guiMode;

    private boolean privateNetwork;

    private String guest;

    private List<String[]> customizeVm;

    private String bashProvisionScript;

	/**
	 * Constructs a configuration.
     * @param name name of the VM. This can be null
     * @param ip the static ip of the VM. This can be null
     * @param hostName the host name of the VM. This can be null
     * @param boxName the name of the Vagrant box this VM depends on.
     * @param boxUrl the url of the Vagrant box this VM depends on
     * @param guest
     * @param portForwardings the configuration for all port forwardings. This can be null
     * @param syncFolders
     * @param puppetProvisionerConfig the puppet configuration for the VM. This can be null
     * @param guiMode true if the VM should run in gui mode. This means that VirtualBox is not running in headless mode
     * @param privateNetwork
     * @param bashProvisionScript
     */
	public VagrantVmConfig(String name, String ip, String hostName, String boxName, URL boxUrl, String guest,
                           Iterable<VagrantPortForwarding> portForwardings, List<VagrantSyncFolder> syncFolders,
                           PuppetProvisionerConfig puppetProvisionerConfig,
                           boolean guiMode, boolean privateNetwork,
                           List<String[]> customizeVm, String bashProvisionScript) {
		this.portForwardings = new ArrayList<VagrantPortForwarding>();
        this.syncFolders = new ArrayList<VagrantSyncFolder>();
        this.customizeVm = new ArrayList<String[]>();
		if(portForwardings != null) {
			for(VagrantPortForwarding portForwarding : portForwardings) {
				this.portForwardings.add(portForwarding);
			}
		}
        if(syncFolders != null) {
            for(VagrantSyncFolder syncFolder : syncFolders) {
                this.syncFolders.add(syncFolder);
            }
        }
        if (customizeVm != null) {
            this.customizeVm.addAll(customizeVm);
        }
        this.bashProvisionScript = bashProvisionScript;
		this.puppetProvisionerConfig = puppetProvisionerConfig;
		this.ip = ip;
		this.name = name;
		this.boxName = boxName;
		this.boxUrl = boxUrl;
        this.guest = guest;
		this.hostName = hostName;
		this.guiMode = guiMode;
        this.privateNetwork = privateNetwork;
	}
	
	/**
	 * Returns the host name of the VM
	 * @return the host name of the VM
	 */
	public String getHostName() {
		return hostName;
	}
	
	/**
	 * Returns true if gui mode is active for the VM. This means that VirtualBox is not running in headless mode.
	 * @return true if gui mode is active
	 */
	public boolean isGuiMode() {
		return guiMode;
	}
	
	/**
	 * Returns the name of the box Vagrant will use as template for the VM
	 * @return the name of the box
	 */
	public String getBoxName() {
		return boxName;
	}

    public String getGuest() {
        return guest;
    }

    /**
	 * Returns the URL of the box Vagrant will use as template for the VM. If the box with the given name is not installed on your system Vagrant will download it by using this URL.
	 * @return the URL of the box
	 */
	public URL getBoxUrl() {
		return boxUrl;
	}
	
	/**
	 * Returns the puppet configuration for the VM
	 * @return the puppet configuration
	 */
	public PuppetProvisionerConfig getPuppetProvisionerConfig() {
		return puppetProvisionerConfig;
	}


    public String getBashProvisionScript() {
        return bashProvisionScript;
    }

    /**
	 * Returns a iterator for all port forwardings of the VM
	 * @return a iterator for all port forwardings
	 */
	public Iterable<VagrantPortForwarding> getPortForwardings() {
		return portForwardings;
	}

    public List<VagrantSyncFolder> getSyncFolders() {
        return syncFolders;
    }

    public List<String[]>  getCustomizeVm() {
        return customizeVm;
    }

    /**
	 * Returns the name of the VM
	 * @return the name of the VM
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the static ip for the VM
	 * @return the static ip for the VM
	 */
	public String getIp() {
		return ip;
	}

    public boolean isPrivateNetwork() {
        return privateNetwork;
    }
}
