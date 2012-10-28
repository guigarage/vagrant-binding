package com.guigarage.vagrant.configuration;

import java.util.ArrayList;
import java.util.List;

public class VagrantConfiguration {

	private VagrantEnvironmentConfig environmentConfig;
	
	private List<VagrantFileTemplateConfiguration> fileTemplateConfigurations;
	
	public VagrantConfiguration(VagrantEnvironmentConfig environmentConfig, Iterable<VagrantFileTemplateConfiguration> fileTemplateConfigurations) {
		this.environmentConfig = environmentConfig;
		this.fileTemplateConfigurations = new ArrayList<>();
		for(VagrantFileTemplateConfiguration fileTemplateConfiguration : fileTemplateConfigurations) {
			this.fileTemplateConfigurations.add(fileTemplateConfiguration);
		}
	}
	
	public VagrantEnvironmentConfig getEnvironmentConfig() {
		return environmentConfig;
	}
	
	public Iterable<VagrantFileTemplateConfiguration> getFileTemplateConfigurations() {
		return fileTemplateConfigurations;
	}
}
