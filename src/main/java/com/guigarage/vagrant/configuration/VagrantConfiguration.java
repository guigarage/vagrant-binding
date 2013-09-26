package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Global configuration for a Vagrant environment that uses {@link VagrantFileTemplateConfiguration} for a Vagrant environment
 * @author hendrikebbers
 *
 */
/**
 * @author hendrikebbers
 *
 */
public class VagrantConfiguration {

	private VagrantEnvironmentConfig environmentConfig;
	
	private List<VagrantFileTemplateConfiguration> fileTemplateConfigurations;

	private List<VagrantFolderTemplateConfiguration> folderTemplateConfigurations;
	
	public VagrantConfiguration(VagrantEnvironmentConfig environmentConfig, Iterable<VagrantFileTemplateConfiguration> fileTemplateConfigurations, Iterable<VagrantFolderTemplateConfiguration> folderTemplateConfigurations) {
		this.environmentConfig = environmentConfig;
		this.fileTemplateConfigurations = new ArrayList<VagrantFileTemplateConfiguration>();
		for(VagrantFileTemplateConfiguration fileTemplateConfiguration : fileTemplateConfigurations) {
			this.fileTemplateConfigurations.add(fileTemplateConfiguration);
		}
		this.folderTemplateConfigurations = new ArrayList<VagrantFolderTemplateConfiguration>();
		for(VagrantFolderTemplateConfiguration folderTemplateConfiguration : folderTemplateConfigurations) {
			this.folderTemplateConfigurations.add(folderTemplateConfiguration);
		}
	}
	
	/**
	 * Returns the configuration of the Vagrant environment
	 * @return the configuration of the Vagrant environment
	 */
	public VagrantEnvironmentConfig getEnvironmentConfig() {
		return environmentConfig;
	}
	
	/**
	 * Returns all {@link VagrantFileTemplateConfiguration} used by this configuration.
	 * @return all {@link VagrantFileTemplateConfiguration}
	 */
	public Iterable<VagrantFileTemplateConfiguration> getFileTemplateConfigurations() {
		return fileTemplateConfigurations;
	}
	
	public Iterable<VagrantFolderTemplateConfiguration> getFolderTemplateConfigurations() {
		return folderTemplateConfigurations;
	}
}
