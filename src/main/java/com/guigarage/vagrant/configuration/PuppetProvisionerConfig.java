package com.guigarage.vagrant.configuration;

public class PuppetProvisionerConfig {

	private String manifestsPath;
	
	private String manifestFile;
	
	private boolean debug;
	
	private String modulesPath;
	
	public PuppetProvisionerConfig(boolean debug, String manifestsPath, String manifestFile, String modulesPath) {
		this.manifestsPath = manifestsPath;
		this.manifestFile = manifestFile;
		this.debug = debug;
		this.modulesPath = modulesPath;
	}
	
	public String getManifestFile() {
		return manifestFile;
	}
	
	public String getManifestsPath() {
		return manifestsPath;
	}
	
	public String getModulesPath() {
		return modulesPath;
	}
	
	public boolean isDebug() {
		return debug;
	}
}
