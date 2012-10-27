package com.guigarage.vagrant.configuration;

public class PuppetProvisionerConfig {

	private String manifestsPath;
	private String manifestFile;
	
	public PuppetProvisionerConfig(String manifestsPath, String manifestFile) {
		this.manifestsPath = manifestsPath;
		this.manifestFile = manifestFile;
	}
	
	public String getManifestFile() {
		return manifestFile;
	}
	
	public String getManifestsPath() {
		return manifestsPath;
	}
}
