package com.guigarage.vagrant.configuration.builder;

import com.guigarage.vagrant.configuration.PuppetProvisionerConfig;

public class PuppetProvisionerConfigBuilder {

	private String manifestPath;
	
	private String manifestFile;
	
	private boolean debug;
	
	private String modulesPath;
	
	
	public PuppetProvisionerConfigBuilder() {
	}
	
	public static PuppetProvisionerConfigBuilder create() {
		return new PuppetProvisionerConfigBuilder();
	}
	
	public PuppetProvisionerConfigBuilder withManifestFile(String manifestFile) {
		this.manifestFile = manifestFile;
		return this;
	}
	
	public PuppetProvisionerConfigBuilder withManifestPath(String manifestPath) {
		this.manifestPath = manifestPath;
		return this;
	}
	
	public PuppetProvisionerConfigBuilder withModulesPath(String modulesPath) {
		this.modulesPath = modulesPath;
		return this;
	}
	
	public PuppetProvisionerConfigBuilder withDebug(boolean debug) {
		this.debug = debug;
		return this;
	}
	
	public PuppetProvisionerConfig build() {
		return new PuppetProvisionerConfig(debug, manifestPath, manifestFile, modulesPath);
	}
}
