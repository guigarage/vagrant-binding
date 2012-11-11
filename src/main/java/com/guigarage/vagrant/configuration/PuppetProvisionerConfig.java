package com.guigarage.vagrant.configuration;

/**
 * Configuration for a puppet provisioner that is used by Vagrant to configure a
 * VM
 * 
 * @author hendrikebbers
 * 
 */
public class PuppetProvisionerConfig {

	private String manifestsPath;

	private String manifestFile;

	private boolean debug;

	private String modulesPath;

	/**
	 * Creates a new {@link PuppetProvisionerConfig}
	 * @param debug defines if debugging is activated for Puppet
	 * @param manifestsPath path to the Puppet manifest
	 * @param manifestFile name of the Puppet manifest file
	 * @param modulesPath path to all Puppet modules
	 */
	public PuppetProvisionerConfig(boolean debug, String manifestsPath,
			String manifestFile, String modulesPath) {
		this.manifestsPath = manifestsPath;
		this.manifestFile = manifestFile;
		this.debug = debug;
		this.modulesPath = modulesPath;
	}

	/**
	 * Returns the name of the Puppet manifest
	 * 
	 * @return the name of the Puppet manifest
	 */
	public String getManifestFile() {
		return manifestFile;
	}

	/**
	 * Returns the path of the Puppet manifest
	 * 
	 * @return the path of the Puppet manifest
	 */
	public String getManifestsPath() {
		return manifestsPath;
	}

	/**
	 * Returns the path to all Puppet modules
	 * 
	 * @return the path to all Puppet modules
	 */
	public String getModulesPath() {
		return modulesPath;
	}

	/**
	 * Returns true if debugging for Puppet is activated. This will log more
	 * information from puppet on the console when creating a VM
	 * 
	 * @return true if debugging for Puppet is activated
	 */
	public boolean isDebug() {
		return debug;
	}
}
