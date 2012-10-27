package com.guigarage.vagrant.configuration.builder;

import com.guigarage.vagrant.configuration.PuppetProvisionerConfig;

public class PuppetProvisionerConfigBuilder {

	private String manifestPath;
	private String manifestFile;
	
	public PuppetProvisionerConfigBuilder() {
	}
	
	public PuppetProvisionerConfigBuilder withManifestFile(String manifestFile) {
		this.manifestFile = manifestFile;
		return this;
	}
	
	public PuppetProvisionerConfigBuilder withManifestPath(String manifestPath) {
		this.manifestPath = manifestPath;
		return this;
	}
	
	public PuppetProvisionerConfig create() {
		return new PuppetProvisionerConfig(manifestPath, manifestFile);
	}
}
