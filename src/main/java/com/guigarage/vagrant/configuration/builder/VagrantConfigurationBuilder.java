package com.guigarage.vagrant.configuration.builder;

import java.util.ArrayList;
import java.util.List;

import com.guigarage.vagrant.configuration.VagrantConfiguration;
import com.guigarage.vagrant.configuration.VagrantEnvironmentConfig;
import com.guigarage.vagrant.configuration.VagrantFileTemplateConfiguration;

public class VagrantConfigurationBuilder {

	private VagrantEnvironmentConfig environmentConfig;

	private List<VagrantFileTemplateConfiguration> fileTemplateConfigurations;

	public VagrantConfigurationBuilder() {
		fileTemplateConfigurations = new ArrayList<>();
	}
	
	public static VagrantConfigurationBuilder create() {
		return new VagrantConfigurationBuilder();
	}
	
	public VagrantConfigurationBuilder withVagrantEnvironmentConfig(VagrantEnvironmentConfig environmentConfig) {
		this.environmentConfig = environmentConfig;
		return this;
	}

	public VagrantConfigurationBuilder withVagrantFileTemplateConfiguration(VagrantFileTemplateConfiguration fileTemplateConfiguration) {
		this.fileTemplateConfigurations.add(fileTemplateConfiguration);
		return this;
	}
	
	public VagrantConfiguration build() {
		return new VagrantConfiguration(environmentConfig,
				fileTemplateConfigurations);
	}
}
